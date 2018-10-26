package com.chunxiao.dev.mybatis;

import org.apache.ibatis.session.ExecutorType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joey on 16-1-9.
 */
@Configuration
@ConfigurationProperties(prefix = MybatisProperties.MYBATIS_PREFIX)
public class MybatisProperties {

    public MybatisProperties() {
        System.out.println("MybatisProperties constructor");
    }

    public static final String MYBATIS_PREFIX = "com.chunxiao.mybatis";

    private Map<String,Resource> mapper = new HashMap<String, Resource>();

    private Map<String, String> typeAlias = new HashMap<String, String>();

    private Map<String, String> scanMapper = new HashMap<String, String>();

    /**
     * Config file path.
     */
    private String config;

    /**
     * Location of mybatis mapper files.
     */
    private Resource[] mapperLocations;

    /**
     * Package to scan domain objects.
     */
    private String typeAliasesPackage;

    /**
     * Package to scan handlers.
     */
    private String typeHandlersPackage;

    /**
     * Check the config file exists.
     */
    private boolean checkConfigLocation = false;

    /**
     * Execution mode.
     */
    private ExecutorType executorType = ExecutorType.SIMPLE;

    public String getConfig() {
        return this.config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public Resource[] getMapperLocations() {
        return this.mapperLocations;
    }

    public void setMapperLocations(Resource[] mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public String getTypeHandlersPackage() {
        return this.typeHandlersPackage;
    }

    public void setTypeHandlersPackage(String typeHandlersPackage) {
        this.typeHandlersPackage = typeHandlersPackage;
    }

    public String getTypeAliasesPackage() {
        return this.typeAliasesPackage;
    }

    public void setTypeAliasesPackage(String typeAliasesPackage) {
        this.typeAliasesPackage = typeAliasesPackage;
    }

    public boolean isCheckConfigLocation() {
        return this.checkConfigLocation;
    }

    public void setCheckConfigLocation(boolean checkConfigLocation) {
        this.checkConfigLocation = checkConfigLocation;
    }

    public ExecutorType getExecutorType() {
        return this.executorType;
    }

    public void setExecutorType(ExecutorType executorType) {
        this.executorType = executorType;
    }

    public static String getMYBATIS_PREFIX() {
        return MYBATIS_PREFIX;
    }

    public Map<String, Resource> getMapper() {
        return mapper;
    }

    public void setMapper(Map<String, Resource> mapper) {
        this.mapper = mapper;
    }

    public Map<String, String> getTypeAlias() {
        return typeAlias;
    }

    public void setTypeAlias(Map<String, String> typeAlias) {
        this.typeAlias = typeAlias;
    }

    public Map<String, String> getScanMapper() {
        return scanMapper;
    }

    public void setScanMapper(Map<String, String> scanMapper) {
        this.scanMapper = scanMapper;
    }
}


