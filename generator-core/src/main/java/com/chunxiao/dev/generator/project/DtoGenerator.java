package com.chunxiao.dev.generator.project;

import com.chunxiao.dev.codegen.ClassLoaderUtil;
import com.chunxiao.dev.config.DtoConfig;
import com.chunxiao.dev.generator.common.PojoGenerator;
import com.chunxiao.dev.parser.JsonToPojo;
import com.chunxiao.dev.util.SourceCodeUtil;
import com.chunxiao.dev.util.StringUtil;
import com.chunxiao.dev.generator.common.Generator;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;

/**
 * Created by chunxiaoli on 10/20/16.
 */
public class DtoGenerator implements Generator {

    private static final Logger logger = LoggerFactory.getLogger(DtoGenerator.class);
    private DtoConfig dtoConfig;

    public DtoGenerator(DtoConfig dtoConfig){
        this.dtoConfig=dtoConfig;
    }

    public DtoConfig getDtoConfig() {
        return dtoConfig;
    }

    public void setDtoConfig(DtoConfig dtoConfig) {
        this.dtoConfig = dtoConfig;
    }

    @Override
    public void generate() {
        generateDtoFromCgiJson(this.dtoConfig.getJsonConfigPath());
    }

    public void generateDtoFromCgiJson(String jsonFile){

        //InputStream inputStream= getClass().getClassLoader().getResourceAsStream(jsonFile);

        if(StringUtil.isEmpty(jsonFile)){
            logger.error("cgi json file is null or empty!");
            return;
        }
        InputStream inputStream= null;
        try {
            inputStream = new FileInputStream(jsonFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("cgi json file :{} not found!",jsonFile);
        }

        if(inputStream!=null){
            generateDtoFromInputStream(inputStream);
        }


    }

    public void generateDtoFromInputStream(InputStream inputStream){
        Reader reader = new InputStreamReader(inputStream);
        JsonElement jsonElement = new JsonParser().parse(reader);


        JsonObject ele=jsonElement.getAsJsonObject();


        JsonElement list=ele.get("controller_list");

        for (JsonElement item:list.getAsJsonArray()) {
            JsonObject o=item.getAsJsonObject();
            JsonElement el=o.get("cgi_list");

            for (JsonElement obj:el.getAsJsonArray()){
                JsonObject object=obj.getAsJsonObject();

                JsonElement cgi= object.get("cgi");
                JsonElement request= object.get("request");
                JsonElement response= object.get("response");

                String  cgiName= cgi.getAsString();

                jsonElement.getAsJsonObject();
                String requestDtoName= SourceCodeUtil.jsonToUpperCase(cgiName)+this.dtoConfig.getReqDtoNameSuffix();
                String responseDtoName=SourceCodeUtil.jsonToUpperCase(cgiName)+this.dtoConfig.getResDtoNameSuffix();

                String requestDtoFullName=dtoConfig.getRequestDtoPackage()+requestDtoName;
                String responseDtoFullName=dtoConfig.getResponseDtoPackage()+responseDtoName;


                JsonToPojo.fromJsonNode(request,requestDtoFullName,
                        new File(this.dtoConfig.getDir()),true,true,true);

                JsonToPojo.fromJsonNode(response,responseDtoFullName,
                        new File(this.dtoConfig.getDir()),true,true,true);
            }

        }
    }


    public void generateFromPojoClassFile(String fileDir,String sourceFile){
        Class cls= ClassLoaderUtil.loadFromFile(sourceFile);
        generateFromPojoClass(cls,null);

    }

    public void generateFromPojoClass(Class sourceCls,String targetClassFullName){
        Field[] fields=sourceCls.getDeclaredFields();
        new PojoGenerator().generatePojo(targetClassFullName,fields,
                new File(this.dtoConfig.getDir()),true,false);

    }


}
