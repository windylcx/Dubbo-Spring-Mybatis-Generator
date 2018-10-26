package com.chunxiao.dev.generator.web;

import com.chunxiao.dev.config.PropertyConfig;
import com.chunxiao.dev.config.web.WebConfig;
import com.chunxiao.dev.generator.common.PropertiesGenerator;

import java.io.File;

/**
 * Created by chunxiaoli on 5/31/17.
 */
public class WebApplicationUtil {
    //生成appplication.properties
    public static void createApplicationPropertiesFile(WebConfig config) {
        String base = WebUtil.getWebResourceDir(config);
        String path = base + File.separator + "config" + File.separator + "application.properties";

        PropertyConfig propertyConfig = new PropertyConfig();

        propertyConfig.setPath(path);

        PropertiesGenerator generator = new PropertiesGenerator(propertyConfig);

        generator.set("logging.file",config.getLogginFile());
        generator.set("spring.profiles.active", "test");
        generator.set("spring.dao.exceptiontranslation.enabled", "false");
        generator.set("server.port", "5000");
        generator.generate();

    }
}
