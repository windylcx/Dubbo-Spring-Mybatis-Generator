package com.chunxiao.dev.generator.provider;

import com.chunxiao.dev.config.PropertyConfig;
import com.chunxiao.dev.generator.common.PropertiesGenerator;
import com.chunxiao.dev.util.FileUtil;
import com.chunxiao.dev.config.provider.ProviderConfig;
import com.chunxiao.dev.generator.maven.MavenDirUtil;

import java.io.File;

/**
 * Created by chunxiaoli on 5/23/17.
 */
public class SpringBootAutoUtil {

    //spring auto config
    public static void createSpringBootAutoConfigFiles(ProviderConfig config) {
        createSpringBootAutoConfigFiles(ProviderUtil.getProviderDir(config),
                config.getSpringAutoConfigFullClassPath());
    }

    //spring auto config
    public static void createSpringBootAutoConfigFiles(String parentDir,String fullClassPath) {
        String base = MavenDirUtil.getResourceBaseDir(parentDir);

        FileUtil.createDir(base + File.separator + "META-INF");

        String path = base + File.separator + "META-INF" + File.separator + "spring.factories";
        PropertyConfig config = new PropertyConfig();
        config.setPath(path);

        PropertiesGenerator generator = new PropertiesGenerator(config);

        //todo configurable
        generator.set("org.springframework.boot.autoconfigure.EnableAutoConfiguration",fullClassPath);
        generator.generate();
    }
}
