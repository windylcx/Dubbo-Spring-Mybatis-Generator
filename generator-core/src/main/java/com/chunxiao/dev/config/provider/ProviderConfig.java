package com.chunxiao.dev.config.provider;

import com.chunxiao.dev.config.MybatisConfig;
import com.chunxiao.dev.config.dubbo.DubboCommonConfig;
import com.chunxiao.dev.pojo.TableInfo;
import com.chunxiao.dev.config.api.ApiConfig;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by chunxiaoli on 5/19/17.
 */
public class ProviderConfig extends ApiConfig{

    @JsonProperty("provider_dir_name")
    private String providerDirName;

    @JsonProperty("dto_dir_name")
    private String dtoDirName;

    @JsonProperty("dao_dir_name")
    private String daoDirName;

    @JsonProperty("orm_dir_name")
    private String ormDirName;

    @JsonProperty("pojo_dir_name")
    private String pojoDirName;

    @JsonProperty("log_file")
    private String logFile;

    @JsonProperty("mybatis")
    private MybatisConfig mybatisConfig;

    //spring boot mybatis autoconfig
    private String MybatisAutoConfigPrefix;


    private String mapperXmlFilePost;

    private String serviceFilePost;

    private String serviceProviderPomTemplatePath;

    @JsonProperty("table_info")
    private List<TableInfo> tableInfoList;

    @JsonProperty("tables")
    private String tables;


    @JsonProperty("log_config_file")
    private String logConfigFilePath;

    @JsonProperty("application_properties_template_path")
    private String applicationPropertiesTemplatePath;

    @JsonProperty("config_dir_name")
    private String configDirName;

    private DubboCommonConfig dubboConfig;


    //spring 自动配置类路径
    private String springAutoConfigFullClassPath;


    private int exportServiceSeperately;

    @JsonProperty("tables_sql")
    private String tablesSql;

    public String getTablesSql() {
        return tablesSql;
    }

    public void setTablesSql(String tablesSql) {
        this.tablesSql = tablesSql;
    }

    public String getProviderDirName() {
        return providerDirName;
    }

    public void setProviderDirName(String providerDirName) {
        this.providerDirName = providerDirName;
    }

    public String getDtoDirName() {
        return dtoDirName;
    }

    public void setDtoDirName(String dtoDirName) {
        this.dtoDirName = dtoDirName;
    }

    public String getDaoDirName() {
        return daoDirName;
    }

    public void setDaoDirName(String daoDirName) {
        this.daoDirName = daoDirName;
    }

    public String getOrmDirName() {
        return ormDirName;
    }

    public void setOrmDirName(String ormDirName) {
        this.ormDirName = ormDirName;
    }

    public String getPojoDirName() {
        return pojoDirName;
    }

    public void setPojoDirName(String pojoDirName) {
        this.pojoDirName = pojoDirName;
    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }

    public String getServiceProviderPomTemplatePath() {
        return serviceProviderPomTemplatePath;
    }

    public MybatisConfig getMybatisConfig() {
        return mybatisConfig;
    }

    public void setMybatisConfig(MybatisConfig mybatisConfig) {
        this.mybatisConfig = mybatisConfig;
    }

    public void setServiceProviderPomTemplatePath(String serviceProviderPomTemplatePath) {
        this.serviceProviderPomTemplatePath = serviceProviderPomTemplatePath;
    }

    public String getMapperXmlFilePost() {
        return mapperXmlFilePost;
    }

    public void setMapperXmlFilePost(String mapperXmlFilePost) {
        this.mapperXmlFilePost = mapperXmlFilePost;
    }

    public String getServiceFilePost() {
        return serviceFilePost;
    }

    public void setServiceFilePost(String serviceFilePost) {
        this.serviceFilePost = serviceFilePost;
    }

    public String getLogConfigFilePath() {
        return logConfigFilePath;
    }

    public void setLogConfigFilePath(String logConfigFilePath) {
        this.logConfigFilePath = logConfigFilePath;
    }

    public String getApplicationPropertiesTemplatePath() {
        return applicationPropertiesTemplatePath;
    }

    public void setApplicationPropertiesTemplatePath(String applicationPropertiesTemplatePath) {
        this.applicationPropertiesTemplatePath = applicationPropertiesTemplatePath;
    }

    public String getSpringAutoConfigFullClassPath() {
        return springAutoConfigFullClassPath;
    }

    public void setSpringAutoConfigFullClassPath(String springAutoConfigFullClassPath) {
        this.springAutoConfigFullClassPath = springAutoConfigFullClassPath;
    }

    public String getConfigDirName() {
        return configDirName;
    }

    public void setConfigDirName(String configDirName) {
        this.configDirName = configDirName;
    }



    public String getTables() {
        return tables;
    }

    public void setTables(String tables) {
        this.tables = tables;
    }


    public List<TableInfo> getTableInfoList() {
        return tableInfoList;
    }

    public void setTableInfoList(List<TableInfo> tableInfoList) {
        this.tableInfoList = tableInfoList;
    }

    public int getExportServiceSeperately() {
        return exportServiceSeperately;
    }

    public void setExportServiceSeperately(int exportServiceSeperately) {
        this.exportServiceSeperately = exportServiceSeperately;
    }

    public String getMybatisAutoConfigPrefix() {
        return MybatisAutoConfigPrefix;
    }

    public void setMybatisAutoConfigPrefix(String mybatisAutoConfigPrefix) {
        MybatisAutoConfigPrefix = mybatisAutoConfigPrefix;
    }

    public DubboCommonConfig getDubboConfig() {
        return dubboConfig;
    }

    public void setDubboConfig(DubboCommonConfig dubboConfig) {
        this.dubboConfig = dubboConfig;
    }

    @Override
    public String toString() {
        return "ProviderConfig{" +
                "providerDirName='" + providerDirName + '\'' +
                ", dtoDirName='" + dtoDirName + '\'' +
                ", daoDirName='" + daoDirName + '\'' +
                ", ormDirName='" + ormDirName + '\'' +
                ", pojoDirName='" + pojoDirName + '\'' +
                ", logFile='" + logFile + '\'' +
                ", mybatisConfig=" + mybatisConfig +
                ", MybatisAutoConfigPrefix='" + MybatisAutoConfigPrefix + '\'' +
                ", mapperXmlFilePost='" + mapperXmlFilePost + '\'' +
                ", serviceFilePost='" + serviceFilePost + '\'' +
                ", serviceProviderPomTemplatePath='" + serviceProviderPomTemplatePath + '\'' +
                ", tableInfoList=" + tableInfoList +
                ", tables='" + tables + '\'' +
                ", logConfigFilePath='" + logConfigFilePath + '\'' +
                ", applicationPropertiesTemplatePath='" + applicationPropertiesTemplatePath + '\'' +
                ", configDirName='" + configDirName + '\'' +
                ", dubboConfig=" + dubboConfig +
                ", springAutoConfigFullClassPath='" + springAutoConfigFullClassPath + '\'' +
                ", exportServiceSeperately=" + exportServiceSeperately +
                '}';
    }
}
