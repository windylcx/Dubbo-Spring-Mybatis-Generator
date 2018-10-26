package com.chunxiao.dev;

import com.chunxiao.dev.provider.ProviderTest;
import com.chunxiao.dev.config.ConfigDefault;
import com.chunxiao.dev.config.ConfigGenerator;
import com.chunxiao.dev.config.GlobalConfig;
import com.chunxiao.dev.config.MybatisConfig;
import com.chunxiao.dev.config.provider.ProviderConfig;
import com.chunxiao.dev.generator.provider.ProviderUtil;
import com.chunxiao.dev.pojo.TableInfo;
import com.chunxiao.dev.util.ConfigUtil;
import com.chunxiao.dev.util.ObjectConvertUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by chunxiaoli on 6/22/17.
 */
public class BaseTest {
    protected static final Logger         logger = LoggerFactory.getLogger(ProviderTest.class);
    protected static                 ProviderConfig config = new ProviderConfig();
    protected static GlobalConfig globalConfig;

    protected static MybatisConfig mybatisConfig =new MybatisConfig();

    static {
        try {
            final InputStream file =
                    new FileInputStream(
                            "/Users/chunxiaoli/IdeaProjects/Dubbo-Spring-Mybatis-Generator/test.yaml");
            globalConfig = ObjectConvertUtil
                    .merge(ConfigGenerator.getDefaultGlobal(), ConfigUtil.parseConfig(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        config.setDir(".");
        config.setName("test");
        config.setGroupId("com.chunxiao.mall");
        config.setApiDirName("api");
        config.setProviderDirName("provider");
        config.setMapperXmlFilePost("mapper");
        config.setSpringAutoConfigFullClassPath(ConfigDefault.SPRING_AUTO_CONFIG_FULLCLASSPATH);
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableName("t_sku_snapshot");
        config.setMybatisConfig(ConfigGenerator.generateMybatisConfig(globalConfig.getMybatisConfig(), tableInfo,
                ProviderUtil.getProviderPackage(config)));
        config.setTables("t_sku_snapshot");

        config = ObjectConvertUtil.merge(config, ConfigGenerator.getDefault());
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(config,Map.class);
        logger.info("----config----:{}",map);

    }
}
