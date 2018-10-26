package com.chunxiao.dev.generator.web;

import com.chunxiao.dev.config.DtoConfig;
import com.chunxiao.dev.config.web.WebConfig;
import com.chunxiao.dev.generator.maven.MavenDirUtil;
import com.chunxiao.dev.generator.project.DtoGenerator;

/**
 * Created by chunxiaoli on 5/31/17.
 */
public class DtoUtil {
    public static void createDtoFromJson(WebConfig config) {

        DtoGenerator dtoGenerator = new DtoGenerator(getDtoConfig(config));
        dtoGenerator.generate();
    }

    public static DtoConfig getDtoConfig(WebConfig config){
        DtoConfig dtoConfig = new DtoConfig();
        dtoConfig.setReqDtoNameSuffix(config.getReqDtoNameSuffix());
        dtoConfig.setResDtoNameSuffix(config.getResDtoNameSuffix());
        dtoConfig.setDir(MavenDirUtil.getMavenSourceCodeDir(config.getDir()));
        String packageName = MavenDirUtil.getBasePackage(config.getGroupId(),config.getName());

        dtoConfig.setPackageName(packageName);
        dtoConfig.setJsonConfigPath(config.getJsonFile());
        return dtoConfig;
    }
}
