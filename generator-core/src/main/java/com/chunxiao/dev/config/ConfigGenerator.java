package com.chunxiao.dev.config;

import com.chunxiao.dev.config.dubbo.ClientConfig;
import com.chunxiao.dev.config.dubbo.DubboCommonConfig;
import com.chunxiao.dev.config.dubbo.ProtocolConfig;
import com.chunxiao.dev.config.web.WebConfig;
import com.chunxiao.dev.util.ObjectConvertUtil;
import com.chunxiao.dev.util.StringUtil;
import com.chunxiao.dev.config.dubbo.ApplicationConfig;
import com.chunxiao.dev.config.dubbo.DubboProviderConfig;
import com.chunxiao.dev.config.dubbo.RegistryConfig;
import com.chunxiao.dev.config.provider.ProviderConfig;
import com.chunxiao.dev.pojo.TableInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static com.chunxiao.dev.config.ConfigDefault.*;

/**
 * Created by chunxiaoli on 12/27/16.
 */
public class ConfigGenerator {

    private static final Logger logger = LoggerFactory.getLogger(ConfigGenerator.class);

    private   static GlobalConfig defaultConfig = new GlobalConfig();



    private   static ProviderConfig providerConfig = new ProviderConfig();

    private   static WebConfig webConfig = new WebConfig();


    static {

        //dubbo config
        ApplicationConfig applicationConfig = new ApplicationConfig();
        ProtocolConfig protocolConfig = new ProtocolConfig();
        RegistryConfig registryConfig = new RegistryConfig();
        DubboProviderConfig dubboProviderConfig = new DubboProviderConfig();
        ClientConfig clientConfig = new ClientConfig();

        protocolConfig.setPort(DUBBO_PORT);
        protocolConfig.setOwner(DUBBO_OWNER);
        applicationConfig.setOwner(DUBBO_OWNER);




        DubboCommonConfig dubboCommonConfig = new DubboCommonConfig();
        dubboCommonConfig.setRegistryConfig(registryConfig);
        dubboCommonConfig.setProtocolConfig(protocolConfig);
        dubboCommonConfig.setApplicationConfig(applicationConfig);
        dubboCommonConfig.setClientConfig(clientConfig);
        dubboCommonConfig.setDubboProviderConfig(dubboProviderConfig);


        //mybatis config
        MybatisConfig myBatisConfig=new MybatisConfig();
        myBatisConfig.setDbType(ConfigDefault.MYBATIS_DBTYPE);
        myBatisConfig.setHost(ConfigDefault.MYBATIS_HOST);
        myBatisConfig.setPort(ConfigDefault.MYBATIS_PORT);
        myBatisConfig.setUsername(ConfigDefault.MYBATIS_USER);
        myBatisConfig.setPassword(ConfigDefault.MYBATIS_PASSWORD);
        myBatisConfig.setDatabase(ConfigDefault.MYBATIS_DATABASE);
        myBatisConfig.setEncoding(ConfigDefault.MYBATIS_ENCODING);

        myBatisConfig.setDaoPackage(ConfigDefault.MYBATIS_DAO_PACKAGE);
        myBatisConfig.setModelPackage(ConfigDefault.MYBATIS_MODEL_PACKAGE);
        myBatisConfig.setMapperPackage(ConfigDefault.MYBATIS_MAPPER_PACKAGE);
        myBatisConfig.setMapperXMLPackage(ConfigDefault.MYBATIS_MAPPER_XML_PACKAGE);

        myBatisConfig.setDaoOutputDir(ConfigDefault.MYBATIS_DAO_OUTPUT_DIR);
        myBatisConfig.setModelOutputDir(ConfigDefault.MYBATIS_MODEL_OUTPUT_DIR);
        myBatisConfig.setMapperOutputDir(ConfigDefault.MYBATIS_MAPPER_OUTPUT_DIR);
        myBatisConfig.setMapperXMLOutputDir(ConfigDefault.MYBATIS_MAPPER_XML_OUTPUT_DIR);




        //template


        //global
        defaultConfig.setGroupId(ConfigDefault.GLOBAL_GROUP_ID);
        defaultConfig.setArtifactId(ConfigDefault.GLOBAL_ARTIFACT_ID);
        defaultConfig.setVersion(ConfigDefault.GLOBAL_VERSION);

        defaultConfig.setDir(ConfigDefault.GLOBAL_ROOT_DIR);
        defaultConfig.setConstantsDir(ConfigDefault.GLOBAL_CONSTANTS_DIR);
        defaultConfig.setControllerDir(ConfigDefault.GLOBAL_CONTROLLER_DIR);
        defaultConfig.setLogFile(ConfigDefault.GLOBAL_LOG_FILE);

        defaultConfig.setApiDirName(ConfigDefault.GLOBAL_DIR_API);
        defaultConfig.setProviderDirName(ConfigDefault.GLOBAL_DIR_PROVIDER);

        defaultConfig.setDtoDirName(ConfigDefault.GLOBAL_DIR_DTO);
        defaultConfig.setDaoDirName(ConfigDefault.GLOBAL_DIR_DAO);
        defaultConfig.setOrmDirName(ConfigDefault.GLOBAL_DIR_ORM);
        defaultConfig.setPojoDirName(ConfigDefault.GLOBAL_DIR_POJO);
        defaultConfig.setConfigDirName(ConfigDefault.GLOBAL_DIR_CONFIG);

        defaultConfig.setMapperXmlFilePost(ConfigDefault.GLOBAL_MAPPER_XML_FILE_POST);
        defaultConfig.setServiceFilePost(ConfigDefault.GLOBAL_SERVICE_FILE_POST);

        defaultConfig.setReqDtoNameSuffix(ConfigDefault.GLOBAL_DTO_REQ_SUFFIX);
        defaultConfig.setResDtoNameSuffix(ConfigDefault.GLOBAL_DTO_RES_SUFFIX);

        defaultConfig.setSpringAutoConfigFullClassPath(ConfigDefault.SPRING_AUTO_CONFIG_FULLCLASSPATH);


        //web
        webConfig.setName("test");
        webConfig.setGroupId(ConfigDefault.GLOBAL_GROUP_ID);
        webConfig.setArtifactId(ConfigDefault.GLOBAL_ARTIFACT_ID);
        webConfig.setVersion(ConfigDefault.GLOBAL_VERSION);

        webConfig.setDir(ConfigDefault.GLOBAL_ROOT_DIR);
        webConfig.setConstantsDir(ConfigDefault.GLOBAL_CONSTANTS_DIR);
        webConfig.setControllerDir(ConfigDefault.GLOBAL_CONTROLLER_DIR);
        webConfig.setLogginFile(ConfigDefault.GLOBAL_LOG_FILE);

        webConfig.setApiDirName(ConfigDefault.GLOBAL_DIR_API);

        webConfig.setDtoDirName(ConfigDefault.GLOBAL_DIR_DTO);

        webConfig.setServiceFilePost(ConfigDefault.GLOBAL_SERVICE_FILE_POST);

        webConfig.setReqDtoNameSuffix(ConfigDefault.GLOBAL_DTO_REQ_SUFFIX);
        webConfig.setResDtoNameSuffix(ConfigDefault.GLOBAL_DTO_RES_SUFFIX);


        //provider
        providerConfig.setName(ConfigDefault.GLOBAL_NAME);
        providerConfig.setGroupId(ConfigDefault.GLOBAL_GROUP_ID);
        providerConfig.setArtifactId(ConfigDefault.GLOBAL_ARTIFACT_ID);
        providerConfig.setVersion(ConfigDefault.GLOBAL_VERSION);

        providerConfig.setDir(ConfigDefault.GLOBAL_ROOT_DIR);
        providerConfig.setConstantsDir(ConfigDefault.GLOBAL_CONSTANTS_DIR);
        providerConfig.setControllerDir(ConfigDefault.GLOBAL_CONTROLLER_DIR);
        providerConfig.setLogFile(ConfigDefault.GLOBAL_LOG_FILE);

        providerConfig.setApiDirName(ConfigDefault.GLOBAL_DIR_API);
        providerConfig.setProviderDirName(ConfigDefault.GLOBAL_DIR_PROVIDER);

        providerConfig.setDtoDirName(ConfigDefault.GLOBAL_DIR_DTO);
        providerConfig.setDaoDirName(ConfigDefault.GLOBAL_DIR_DAO);
        providerConfig.setOrmDirName(ConfigDefault.GLOBAL_DIR_ORM);
        providerConfig.setPojoDirName(ConfigDefault.GLOBAL_DIR_POJO);
        providerConfig.setConfigDirName(ConfigDefault.GLOBAL_DIR_CONFIG);

        providerConfig.setMapperXmlFilePost(ConfigDefault.GLOBAL_MAPPER_XML_FILE_POST);
        providerConfig.setServiceFilePost(ConfigDefault.GLOBAL_SERVICE_FILE_POST);

        providerConfig.setReqDtoNameSuffix(ConfigDefault.GLOBAL_DTO_REQ_SUFFIX);
        providerConfig.setResDtoNameSuffix(ConfigDefault.GLOBAL_DTO_RES_SUFFIX);

        providerConfig.setSpringAutoConfigFullClassPath(ConfigDefault.SPRING_AUTO_CONFIG_FULLCLASSPATH);

        providerConfig.setExportServiceSeperately(1);


        providerConfig.setMybatisConfig(myBatisConfig);

        providerConfig.setMybatisAutoConfigPrefix(ConfigDefault.MYBATIS_AUTO_CONFIG_PREFIX);

        providerConfig.setDubboConfig(dubboCommonConfig);


        defaultConfig.setMybatisConfig(myBatisConfig);


        logger.info("global config:{}", providerConfig);
    }


    public static ProjectConfig generateProjectConfig(GlobalConfig globalConfig){
        String dir= StringUtil.isEmpty(globalConfig.getDir())? "" :
                globalConfig.getDir() + File.separator + globalConfig.getName() + "-rpc";
        final ProjectConfig config=new ProjectConfig();
        config.setLogFile(globalConfig.getLogFile());
        config.setGroupId(globalConfig.getGroupId());

        String artifactId=globalConfig.getArtifactId();
        artifactId=StringUtil.isEmpty(artifactId)?globalConfig.getName():artifactId;

        config.setArtifactId(artifactId);
        config.setVersion(globalConfig.getVersion());
        config.setProjectName(globalConfig.getName());
        config.setDirName(globalConfig.getDir());
        config.setDir(dir);
        return config;
    }

    public static ProjectConfig generateWebProjectConfig(GlobalConfig globalConfig){
        String dir=globalConfig.getDir();
        final ProjectConfig config=new ProjectConfig();
        config.setLogFile(globalConfig.getLogFile());
        config.setGroupId(globalConfig.getGroupId());
        config.setArtifactId(globalConfig.getName());
        config.setVersion(globalConfig.getVersion());
        config.setProjectName(globalConfig.getName());
        config.setDirName(globalConfig.getDir());
        config.setDir(dir);
        return config;
    }

    public static String getWebRootDir(GlobalConfig globalConfig){
        String parentDir=globalConfig.getDir();
        return StringUtil.isEmpty(parentDir)?globalConfig.getName()
                :(parentDir+File.separator+globalConfig.getName());
    }


    public static MybatisConfig generateMybatisConfig( MybatisConfig cfg, TableInfo table,String packageRoot){
        cfg.setDaoPackage(packageRoot+".dao");
        cfg.setMapperPackage(packageRoot+".orm");
        cfg.setModelPackage(packageRoot+".pojo");
        cfg.setMapperXMLPackage("");
        cfg.setTable(table.getTableName());
        cfg.setDomainObjectName(table.getDomainName());
        return cfg;
    }


    public static GlobalConfig cloneConfig(GlobalConfig from){
        GlobalConfig cfg = new GlobalConfig();
        ObjectConvertUtil.merge(from,cfg);
        return cfg;
    }

    public static GlobalConfig getDefaultGlobal() {
        return defaultConfig;
    }

    public static ProviderConfig getDefault() {
        return providerConfig;
    }

    public static WebConfig getDefaultWebConfig() {
        return webConfig;
    }
}
