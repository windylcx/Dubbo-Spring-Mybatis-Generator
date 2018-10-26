package com.chunxiao.dev.docs;

import com.chunxiao.dev.config.DtoConfig;
import com.chunxiao.dev.util.CustomProtocolUtil;
import com.chunxiao.dev.generator.project.DtoGenerator;
import com.chunxiao.dev.pojo.ProtocolModel;
import com.chunxiao.dev.util.PostmanBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chunxiaoli on 6/15/17.
 */
public class ProtocolUtil {
    public static void generateDto(String docsFile){
        try {
            ProtocolModel protocolModel = CustomProtocolUtil.parseProtocol(new FileInputStream(docsFile));

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            CustomProtocolUtil.convert(protocolModel,out);

            InputStream inputStream = new ByteArrayInputStream(out.toByteArray());

            DtoConfig dtoConfig = new DtoConfig();
            dtoConfig.setDir("target");
            dtoConfig.setPackageName("com.chunxiao.mall." + protocolModel.module + ".api");
            dtoConfig.setReqDtoNameSuffix("Req");
            dtoConfig.setResDtoNameSuffix("Resp");
            new DtoGenerator(dtoConfig).generateDtoFromInputStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void geneaterPostmanCollection(String name,String inputDir,String out){
        PostmanBuilder builder = PostmanBuilder.builder(name);
        File dir=new File(inputDir);
        if(dir.listFiles()!=null){
            for (File file : dir.listFiles()) {
                if (file.isDirectory())
                    continue;
                String dst = "target/" + file.getName() + ".json";
                try (InputStream fin = new FileInputStream(file); FileOutputStream fo = new FileOutputStream(dst)) {

                    ProtocolModel protocolModel = CustomProtocolUtil.parseProtocol(fin);
                    CustomProtocolUtil.convert(protocolModel, fo);

                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("session_key", "{{session_key}}");

                    ObjectMapper objectMapper = new ObjectMapper();

                    for (ProtocolModel.CGI cur : protocolModel.cgiList) {

                        String folder = null;
                        String prefex;
                        if (file.getName().contains("app")) {
                            folder = "app";
                            prefex = "/chunxiaomall_app/v1";
                        } else {
                            folder = "管理后台";
                            prefex = "/chunxiaomall_admin/v1";
                        }

                        if (!cur.url.contains("chunxiaomall_")) {
                            cur.url = prefex + cur.url;
                        }
                        builder.addRequest(folder,
                                cur.name,
                                "http://{{host}}:{{port}}" + cur.url,
                                "POST",
                                headers,
                                objectMapper.writeValueAsString(cur.request),
                                true);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try {
                builder.out(new FileOutputStream(out));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
}
