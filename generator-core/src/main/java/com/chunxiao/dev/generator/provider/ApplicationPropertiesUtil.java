package com.chunxiao.dev.generator.provider;

import com.chunxiao.dev.config.PropertyConfig;
import com.chunxiao.dev.generator.common.PropertiesGenerator;
import com.chunxiao.dev.util.FileUtil;
import com.chunxiao.dev.util.StringUtil;
import com.chunxiao.dev.config.provider.ProviderConfig;
import com.chunxiao.dev.generator.maven.MavenDirUtil;

import java.io.File;
import java.io.InputStream;

/**
 * Created by chunxiaoli on 5/23/17.
 */
public class ApplicationPropertiesUtil {

    private final static String KEY_DUBBO_OWNER="dubbo.application.owner";
    private final static String KEY_DUBBO_NAME="dubbo.application.name";
    private final static String KEY_DUBBO_PORT="dubbo.protocol.port";
    private final static String KEY_DATA_SOURCE_URL="spring.datasource.url";
    private final static String KEY_DATA_SOURCE_USERNAME="spring.datasource.username";
    private final static String KEY_DATA_SOURCE_PASSWORD="spring.datasource.password";


    //生成appplication.properties
    public static void createApplicationPropertiesFile(ProviderConfig config) {

        String templatePath = config.getApplicationPropertiesTemplatePath();
        String base = MavenDirUtil.getResourceBaseDir(ProviderUtil.getProviderDir(config));
        String path = base + File.separator + ProviderUtil.getResourceConfigDir(config) +
                File.separator + "application.properties";
        if (!StringUtil.isEmpty(templatePath)) {
            FileUtil.copy(templatePath, path);
        } else {
            InputStream inputStream = ApplicationPropertiesUtil.class.getClassLoader().
                    getResourceAsStream("template/application.properties");

            FileUtil.save(inputStream, path);

            PropertyConfig propertyConfig = new PropertyConfig();
            propertyConfig.setPath(path);
            PropertiesGenerator generator = new PropertiesGenerator(propertyConfig);

            updateDubboConfig(config,generator);
            updateDataSourceConfig(config,generator);

            generator.generate();

        }

    }

    private void updateProperties(ProviderConfig config,String path){

    }

    private static void updateDubboConfig(ProviderConfig config,PropertiesGenerator generator){

        //generator.set();
        generator.set(KEY_DUBBO_NAME,config.getDubboConfig().getApplicationConfig().getName());
        //generator.set(KEY_DUBBO_OWNER,config.getDubboConfig().getApplicationConfig().getOwner());
        generator.set(KEY_DUBBO_PORT,config.getDubboConfig().getProtocolConfig().getPort());
        generator.generate();
    }

    private static void updateDataSourceConfig(ProviderConfig config,PropertiesGenerator generator){
        String url="jdbc:mysql://"
                + config.getMybatisConfig().getHost()+":"
                + config.getMybatisConfig().getPort()
                + "/"
                + config.getMybatisConfig().getDatabase()
                + "?useSSL=false&useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true";

        generator.set(KEY_DATA_SOURCE_URL,url);
        generator.set(KEY_DATA_SOURCE_USERNAME,config.getMybatisConfig().getUsername());
        generator.set(KEY_DATA_SOURCE_PASSWORD,config.getMybatisConfig().getPassword());

    }


}
