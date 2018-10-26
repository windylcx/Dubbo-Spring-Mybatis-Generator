package com.chunxiao.dev.generator.provider;

import com.chunxiao.dev.config.ConfigDefault;
import com.chunxiao.dev.util.FileUtil;
import com.chunxiao.dev.util.StringUtil;
import com.chunxiao.dev.config.provider.ProviderConfig;
import com.chunxiao.dev.generator.maven.MavenDirUtil;

import java.io.File;
import java.io.InputStream;

/**
 * Created by chunxiaoli on 5/27/17.
 */
public class LogFileUtil {
    public static void  createLogConfig(ProviderConfig config) {
        String sourceFile = config.getLogConfigFilePath();
        String base= MavenDirUtil.getResourceBaseDir(ProviderUtil.getProviderDir(config));
        if(!StringUtil.isEmpty(sourceFile)&&new File(sourceFile).exists()){
            FileUtil.copy(sourceFile,base+File.separator+new File(sourceFile).getName());
        }else { //create default
            InputStream inputStream = LogFileUtil.class.getClassLoader().getResourceAsStream(ConfigDefault.LOG_CONFIG_FILE);
            FileUtil.save(inputStream,base+File.separator+"logback.xml");
        }
    }
}
