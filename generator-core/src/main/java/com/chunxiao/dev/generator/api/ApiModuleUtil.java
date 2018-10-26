package com.chunxiao.dev.generator.api;

import com.chunxiao.dev.config.Config;
import com.chunxiao.dev.config.api.ApiConfig;
import com.chunxiao.dev.generator.maven.MavenDirUtil;
import com.chunxiao.dev.util.FileUtil;
import com.chunxiao.dev.util.SourceCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by chunxiaoli on 5/19/17.
 */
public class ApiModuleUtil {

    private static final Logger logger = LoggerFactory.getLogger(ApiModuleUtil.class);


    @SuppressWarnings("unchecked")
    public static void createApiDir(ApiConfig config) {
        logger.info("createApiDir:{}");
        String apiDir = ApiModuleUtil.getApiDir(config);
        //创建maven结构
        MavenDirUtil.createBaseMavenStructure(apiDir);

        for (String sub : Config.API_SUB_DIRS) {
            String subDir = ApiModuleUtil.getApiSourceCodeDir(config) + File.separator + sub;
            FileUtil.createDir(subDir);
        }
    }

    public static String getApiDir(ApiConfig config) {
        return config.getDir() + File.separator + getApiModuleName(config);
    }

    public static String getApiModuleName(ApiConfig config) {
        return config.getName() + "-" + config.getApiDirName();
    }

    public static String getApiBaseDir(ApiConfig config) {
        return MavenDirUtil.getMavenSourceCodeDir(getApiDir(config));
    }

    public static String getApiSourceCodeDir(ApiConfig config) {
        return MavenDirUtil.getSourceCodeBaseDirOfGroup(getApiDir(config),config.getGroupId(),config.getName()) + File.separator
                + config.getApiDirName();
    }

    public static String getApiPackage(ApiConfig config) {
        return config.getGroupId()+"."+config.getName();
    }

    public static String getApiDtoDir(ApiConfig config) {
        return getApiBaseDir(config) + File.separator + config.getDtoDirName() + File.separator
                + "response";
    }

    public static String getApiDtoPackage(ApiConfig config) {
        return getApiPackage(config) + "." + config.getDtoDirName();
    }

    public static String getApiServicePackage(ApiConfig config) {
        return getApiPackage(config);
    }

    private static String getServiceName(ApiConfig config) {
        return SourceCodeUtil.covertClassName(config.getName() + SourceCodeUtil.getFirstUppercase(
                config.getServiceFilePost()));
    }



    public static String getServiceFullClassName(ApiConfig config) {
        return ApiModuleUtil.getApiPackage(config) + "." + getServiceName(config);
    }

    public static String getServicePath(ApiConfig config) {
        return MavenDirUtil.getMavenSourceCodeDir(ApiModuleUtil.getApiDir(config)) + File.separator
                + SourceCodeUtil.convertPackage2Dir(getServiceFullClassName(config)) + ".java";
    }




}
