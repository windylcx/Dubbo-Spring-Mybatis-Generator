package com.chunxiao.dev.generator.provider;

import com.chunxiao.dev.generator.api.ApiModuleUtil;
import com.chunxiao.dev.util.FileUtil;
import com.chunxiao.dev.util.SourceCodeUtil;
import com.chunxiao.dev.util.StringUtil;
import com.chunxiao.dev.config.Config;
import com.chunxiao.dev.config.ConfigDefault;
import com.chunxiao.dev.config.provider.ProviderConfig;
import com.chunxiao.dev.generator.maven.MavenDirUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by chunxiaoli on 5/19/17.
 */
public class ProviderUtil {

    private static final Logger logger = LoggerFactory.getLogger(ProviderUtil.class);


    public static void createDir(ProviderConfig config){
        String providerDir = ProviderUtil.getProviderDir(config);
        FileUtil.createDir(providerDir);

        MavenDirUtil.createModuleMavenStructure(providerDir,config.getGroupId(),config.getName());

        for (String sub : Config.PROVIDER_SUB_DIRS) {
            String subDir = ProviderUtil.getProviderSourceCodeDir(config) + File.separator + sub;
            FileUtil.createDir(subDir);
        }
    }

    public static String getProviderDir(ProviderConfig config) {
        return config.getDir() + File.separator + getProviderModuleName(config);
    }


    static String getResourceConfigDir(ProviderConfig config) {
        return (StringUtil.isEmpty(config.getConfigDirName())?
                ConfigDefault.GLOBAL_DIR_CONFIG:config.getConfigDirName());
    }

    static  String getProviderModuleName(ProviderConfig config) {
        return config.getName() + "-" + getProviderDirName(config);
    }

    private static  String getProviderSourceCodeDir(ProviderConfig config) {
        return MavenDirUtil.getSourceCodeBaseDirOfGroup(getProviderDir(config),config.getGroupId(),config.getName())
                + File.separator + getProviderDirName(config);
    }

    public static String getProviderPackage(ProviderConfig config) {
        return config.getGroupId()+"."+config.getName() + "." + getProviderDirName(config);
    }

    static String getProviderOrmDir(ProviderConfig config) {
        return getProviderBaseDir(config) + SourceCodeUtil.convertPackage2Dir(getOrmPackage(config));
    }

    static String getProviderBaseDir(ProviderConfig config) {
        return MavenDirUtil.getMavenSourceCodeDir(getProviderDir(config));
    }

    private static String getOrmPackage(ProviderConfig config) {
        return getProviderPackage(config) + "." + config.getOrmDirName();
    }

    static String getProviderUtilPackage(ProviderConfig config) {
        return getProviderPackage(config) + ".util";
    }

    static String getProviderPojoDir(ProviderConfig config) {
        return getProviderBaseDir(config) + SourceCodeUtil.convertPackage2Dir(getPojoPackage(config));
    }


    static String getDaoPackage(ProviderConfig config) {
        return getProviderPackage(config) + "." + config.getDaoDirName();
    }

    static String getPojoPackage(ProviderConfig config) {
        return getProviderPackage(config) + "." + config.getPojoDirName();
    }

    static String getMapperXmlName(ProviderConfig config, String tableName) {
        String mapperPostName = SourceCodeUtil
                .getFirstUppercase(config.getMapperXmlFilePost());
        return SourceCodeUtil.uppercase(tableName, false) + mapperPostName + ".xml";
    }

    private static String getProviderDirName(ProviderConfig config){
        return (StringUtil.isEmpty(config.getProviderDirName())?
                ConfigDefault.GLOBAL_DIR_PROVIDER:config.getProviderDirName());
    }

    static String getProviderDaoDir(ProviderConfig config) {
        return getProviderBaseDir(config) + SourceCodeUtil.convertPackage2Dir(getDaoPackage(config));
    }

    static String getServiceName(ProviderConfig config) {
        return SourceCodeUtil.covertClassName(config.getName() + SourceCodeUtil.getFirstUppercase(
                config.getServiceFilePost()));
    }

    private String getDtoDir(ProviderConfig config) {
        return SourceCodeUtil.convertPackage2Dir(getDtoPackage(config));
    }

    static String getDtoPackage(ProviderConfig config) {
        return ApiModuleUtil.getApiPackage(config) + "." + config.getDtoDirName();
    }

    public static String getMavenResourceBaseDir(ProviderConfig config){
        return MavenDirUtil.getResourceBaseDir(getProviderDir(config));
    }

    public static String getMavenBaseDir(ProviderConfig config){
        return MavenDirUtil.getMavenBaseDir(getProviderDir(config));
    }

}
