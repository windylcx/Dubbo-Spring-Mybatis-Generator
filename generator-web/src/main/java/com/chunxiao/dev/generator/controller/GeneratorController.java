package com.chunxiao.dev.generator.controller;

import com.chunxiao.dev.generator.config.CgiConstants;
import com.chunxiao.dev.generator.config.Config;
import com.chunxiao.dev.config.ConfigGenerator;
import com.chunxiao.dev.config.MybatisConfig;
import com.chunxiao.dev.config.RpcProjectConfigBuilder;
import com.chunxiao.dev.config.provider.ProviderConfig;
import com.chunxiao.dev.generator.rpc.RpcProjectGenerator;
import com.chunxiao.dev.generator.util.DBBean;
import com.chunxiao.dev.generator.util.SQLUtil;
import com.chunxiao.dev.util.DBConnTestUtil;
import com.google.common.io.BaseEncoding;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zeroturnaround.zip.ZipUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.chunxiao.dev.generator.config.ErrorMsg.SQL_EMPATY;

/**
 * Created by chunxiaoli on 7/5/17.
 */
@RestController
public class GeneratorController {

    //private final static   RpcProjectConfigBuilder builder= new RpcProjectConfigBuilder();

    private static RpcProjectGenerator generator;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DBBean dbBean;

    @Value("${db.host}")
    private String DB_HOST;

    @Value("${db.port}")
    private String DB_PORT;

    @Value("${db.database}")
    private String DB_DATABASE;

    @Value("${db.username}")
    private String DB_USERNAME;

    @Value("${db.password}")
    private String DB_PASSWORD;

    private final ExecutorService pool = Executors.newSingleThreadExecutor();

    private final Map<String,HashMap<String,byte[]>> files = new HashMap<>();


   /* static {
        builder.setDir(OUT_DIR);
    }
    */
    @RequestMapping(value = CgiConstants.GENERATE,method = RequestMethod.POST)
    public String generate(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse,
                           @RequestBody ProviderConfig req,
                           @RequestParam("client_id") String clientId) {



        final String sql=req.getTablesSql();

        if(StringUtils.isEmpty(sql)||(files.get(clientId)!=null&&files.get(clientId).get("sqlFile")==null)){
            return SQL_EMPATY;
        }

        
        
        logger.info("generate tables {}",req.getTables());
        logger.info("generate req {}",req);
        logger.info("generate sql {}",sql);

        RpcProjectConfigBuilder builder= new RpcProjectConfigBuilder();
        builder.setDir(Config.OUT_DIR);
        
        if(files.get(clientId)!=null){
            byte[] bytes =files.get(clientId).get("sqlFile");
            if(bytes!=null){
                try {
                    List<String> tables= SQLUtil.parseTables(new String(bytes,"UTF-8"));
                    String tbs=tables.stream().collect(Collectors.joining(","));
                    req.setTables(tbs);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        builder.updateConfig(req);
        builder.setDBConfig(DB_HOST,DB_PORT,DB_USERNAME,DB_PASSWORD,DB_DATABASE);

        

        buildTables(clientId,req);


        generator= new RpcProjectGenerator(builder);

        String dir= generator.generate();
        //String dir="";
        String id= UUID.randomUUID().toString();
        File target =new File(getFileNameById(id));
        ZipUtil.pack(new File(dir),target);

        clear(req.getTables());

        return id;
    }

    @RequestMapping(value = CgiConstants.DOWNLOAD,method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(HttpServletRequest httpServletRequest,
                                           HttpServletResponse httpServletResponse,
                                           @RequestParam("id") String id,@RequestParam("name") String name) {
        byte[]data= null;
        try {
            data = Files.readAllBytes(new File(getFileNameById(id)).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment",name+".zip");

        remove(id);

        return new ResponseEntity<byte[]>(data, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = CgiConstants.UPLOAD,method = RequestMethod.POST)
    public String upload(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse, MultipartFile file) {


        String clientId= httpServletRequest.getParameter("client_id");
        String key= httpServletRequest.getParameter("key");

        logger.info("upload {},{},{}",file.getName(),file.getOriginalFilename(),clientId);

        String filePah= Config.UPLOAD_DIR+File.separator+clientId+File.separator+key;
        try{
            if(files.get(clientId)!=null){
                files.get(clientId).put(key,file.getBytes());
            }else {
                HashMap<String,byte[]> config=new HashMap<>();
                config.put(key,file.getBytes());
                files.put(clientId,config);
            }
        }catch (Exception e){
            e.printStackTrace();
            return "Fail";
        }

        File uploadFile = new File(filePah);

        try {
            FileUtils.copyToFile(file.getInputStream(),uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "OK";
    }

    @RequestMapping(value = CgiConstants.DB_TEST,method = RequestMethod.POST)
    public String testDBConnection(HttpServletRequest httpServletRequest,
                                   HttpServletResponse httpServletResponse,
                                   @RequestBody MybatisConfig mybatisConfig) {
        mybatisConfig.setDbType("MySQL");
        boolean ret= DBConnTestUtil.testDBConn(mybatisConfig);
        return ret?"OK":"FAIL";

    }

    @RequestMapping(value = CgiConstants.LOAD_CONFIG,method = RequestMethod.GET)
    @ResponseBody
    public ProviderConfig loadConfig(HttpServletRequest httpServletRequest,
                                   HttpServletResponse httpServletResponse) {
        ProviderConfig providerConfig = ConfigGenerator.getDefault();
        return providerConfig;
    }

    @RequestMapping(value = CgiConstants.EXECUTE,method = RequestMethod.GET)
    @ResponseBody
    public void executeSql(HttpServletRequest httpServletRequest,
                                     HttpServletResponse httpServletResponse,String[] sqls) {
        for (String item:sqls) {
            dbBean.execute(item);
        }

    }

    private void buildTables(String clientId,ProviderConfig config){
        if(StringUtils.isEmpty(config.getTablesSql())){
            try {
                String decodeSql= new String(BaseEncoding.base64().decode(config.getTablesSql()),"UTF-8");
                String[] commands=decodeSql.split(";");

                for (String item:commands) {
                    dbBean.execute(item);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else {
            if(files.get(clientId)!=null){
                byte[] bytes = files.get(clientId).get("sqlFile");
                try {
                    String sql= new String(bytes,"UTF-8");
                    String[] commands=SQLUtil.filter(sql).split(";");
                    for (String item:commands) {
                        dbBean.execute(item);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getFileNameById(String id){
        return Config.DOWNLOAD_DIR+id+".zip";
    }

    private void clear(final String tables){
        pool.submit(() -> {
            for (String t:tables.split(",")) {
                final String sql="DROP TABLE IF EXISTS `"+t+"`";
                dbBean.execute(sql);
            }
        });
    }

    private void remove(String clientId){
        pool.submit(() -> {
            try {
                FileUtils.forceDelete(new File(Config.UPLOAD_DIR+File.separator+clientId));
                FileUtils.forceDelete(new File(Config.OUT_DIR+File.separator+clientId));
                FileUtils.forceDelete(new File(getFileNameById(clientId)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
