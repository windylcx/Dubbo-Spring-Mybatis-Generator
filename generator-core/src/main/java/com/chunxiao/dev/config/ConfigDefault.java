package com.chunxiao.dev.config;

import com.chunxiao.dev.config.dubbo.DubboCommonConfig;
import com.chunxiao.dev.config.dubbo.DubboConfig;
import com.chunxiao.dev.config.dubbo.ProtocolConfig;
import com.chunxiao.dev.config.dubbo.RegistryConfig;
import com.chunxiao.dev.config.dubbo.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chunxiaoli on 10/12/16.
 */
public class ConfigDefault {

    private static final Logger logger= LoggerFactory.getLogger(ConfigDefault.class);



    public static final String  GLOBAL_GROUP_ID="com.example.www";
    public static final String  GLOBAL_ARTIFACT_ID="test";
    public static final String  GLOBAL_NAME=GLOBAL_ARTIFACT_ID;
    public static final String  GLOBAL_VERSION="1.0.0-SNAPSHOT";

    public static final String  GLOBAL_ROOT_DIR="./TestProject";

    public static final String  GLOBAL_CONSTANTS_DIR="constants";
    public static final String  GLOBAL_CONTROLLER_DIR="controller";

    public static final String  GLOBAL_LOG_FILE="/opt/logs/debug.log";
    public static final String  GLOBAL_DIR_API="api";
    public static final String  GLOBAL_DIR_PROVIDER="provider";
    public static final String  GLOBAL_DIR_WEB="server";
    public static final String  GLOBAL_DIR_DTO="dto";
    public static final String  GLOBAL_DIR_DAO="dao";
    public static final String  GLOBAL_DIR_ORM="orm";
    public static final String  GLOBAL_DIR_POJO="pojo";
    public static final String  GLOBAL_DIR_MODEL="pojo";
    public static final String  GLOBAL_DIR_CONFIG="config";

    public static final String  GLOBAL_MAPPER_XML_FILE_POST="mapper";

    public static final String  GLOBAL_SERVICE_FILE_POST="service";

    public static final String  GLOBAL_DTO_REQ_SUFFIX="ReqDto";
    public static final String  GLOBAL_DTO_RES_SUFFIX="ResDto";


    public static final String  MYBATIS_DBTYPE="MySQL";
    public static final String  MYBATIS_HOST="127.0.0.1";
    public static final String  MYBATIS_PORT="3306";
    public static final String  MYBATIS_USER="root";
    public static final String  MYBATIS_PASSWORD="";
    public static final String  MYBATIS_DATABASE="";
    public static final String  MYBATIS_ENCODING="utf-8";

    public static final String  MYBATIS_MODEL_OUTPUT_DIR="./java";
    public static final String  MYBATIS_MAPPER_XML_OUTPUT_DIR="./resources/mybatis";
    public static final String  MYBATIS_DAO_OUTPUT_DIR="./java";
    public static final String  MYBATIS_MAPPER_OUTPUT_DIR="./java";

    public static final String  MYBATIS_MAPPER_PACKAGE="./orm";
    public static final String  MYBATIS_DAO_PACKAGE="./dao";
    public static final String  MYBATIS_MAPPER_XML_PACKAGE="";
    public static final String  MYBATIS_MODEL_PACKAGE="./pojo";

    public static final String  MYBATIS_AUTO_CONFIG_PREFIX="com.chunxiao.mybatis";

    public static final String  POM_TEMPLATE_PROVIDER="template/template-provider-pom.xml";
    public static final String  POM_TEMPLATE_API="template/template-api-pom.xml";
    public static final String  POM_TEMPLATE_ROOT="template/template-root-pom.xml";

    public static final String  POM_TEMPLATE_WEB="template/template-web-server-pom.xml";
    public static final String  LOG_CONFIG_FILE="template/logback.xml";
    public static final String  LOG_CONFIG_DIR="";

    public static final String  SPRING_AUTO_CONFIG_FULLCLASSPATH="org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration";


    public static final String  DUBBO_PORT="10000";
    public static final String  DUBBO_TIMEOUT="3000";
    public static final String  DUBBO_OWNER="";
    public static final String  DUBBO_PROTOCOL_NAME="dubbo";
    public static final String  DUBBO_REGISTRY_ADDRESS="";


    public static final String DUBBO_XML_PROTOCOL_NAME     ="${dubbo.protocol.name}";
    public static final String DUBBO_XML_PROTOCOL_PORT     ="${dubbo.protocol.port}";
    public static final String DUBBO_XML_REGISTRY_ADDRESS  ="${dubbo.registry.address}";
    public static final String DUBBO_XML_REGISTRY_PROTOCOL ="${dubbo.registry.protocol}";








    public static ProjectConfig getDefault(final String dir,final String project){
        return getDefault(dir,project,
                "com.chunxiao.onecollection",
                "/opt/logs/"+project+"/debug.log");
    }

    public static ProjectConfig getDefault(final String dir,final String project,String groupId,String logFile){
        final ProjectConfig config=new ProjectConfig();
        config.setGroupId(groupId);
        config.setArtifactId(project);
        config.setProjectName(project);
        config.setDirName(project);
        config.setDir(dir);
        config.setLogFile(logFile);
        return config;
    }

    public static PomConfig getDefaultPomConfig(ProjectConfig config,String dir,String module){
        PomConfig pomConfig=new PomConfig();
        pomConfig.setGroupId(config.getGroupId());
        pomConfig.setArtifactId(module);
        pomConfig.setVersion(config.getVersion());
        pomConfig.setProjectName(module);
        pomConfig.setDirName(module);
        pomConfig.setDir(dir);
        return pomConfig;
    }

    public static PomConfig getDefaultPomConfig(CommonConfig config,String dir,String module){
        PomConfig pomConfig=new PomConfig();
        pomConfig.setGroupId(config.getGroupId());
        pomConfig.setArtifactId(module);
        pomConfig.setVersion(config.getVersion());
        pomConfig.setProjectName(module);
        pomConfig.setDirName(module);
        pomConfig.setDir(dir);
        return pomConfig;
    }




    public static DubboConfig getDefaultDubboConfig(CommonConfig config){
        final ApplicationConfig applicationConfig=new ApplicationConfig();
        final ProtocolConfig protocolConfig=new ProtocolConfig();
        final RegistryConfig registryConfig=new RegistryConfig();
        applicationConfig.setName(config.getName());
        applicationConfig.setOwner(config.getOwner());

        protocolConfig.setName(DUBBO_XML_PROTOCOL_NAME);
        protocolConfig.setPort(DUBBO_XML_PROTOCOL_PORT);


        registryConfig.setAddress(DUBBO_XML_REGISTRY_ADDRESS);
        registryConfig.setProtocol(DUBBO_XML_REGISTRY_PROTOCOL);

        final DubboCommonConfig dubboCommonConfig = new DubboCommonConfig();
        dubboCommonConfig.setApplicationConfig(applicationConfig);
        dubboCommonConfig.setProtocolConfig(protocolConfig);
        dubboCommonConfig.setRegistryConfig(registryConfig);


        final DubboConfig dubboConfig=new DubboConfig(dubboCommonConfig);

        return dubboConfig;
    }


}
