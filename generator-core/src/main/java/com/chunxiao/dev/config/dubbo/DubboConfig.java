package com.chunxiao.dev.config.dubbo;

import java.util.List;

/**
 * Created by chunxiaoli on 10/18/16.
 */
/*
    /*
        <beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
       http://code.alibabatech.com/schema/dubbo
       http://code.alibabatech.com/schema/dubbo/dubbo.xsd">

    <dubbo:application name="${dubbo.application.name}"
                       owner="${dubbo.application.owner}" />

    <dubbo:registry protocol="${dubbo.registry.protocol}"
                    address="${dubbo.registry.address}" />

    <dubbo:protocol name="${dubbo.protocol.name}" port="${dubbo.protocol.port}"
                    threadpool="cached" threads="${dubbo.service.provider.threads:200}" />

    <dubbo:provider retries="0" loadbalance="${dubbo.service.loadbalance}" />

</beans>
 */
public class DubboConfig {
    public static final String ATTR_XMLNS              = "xmlns";
    public static final String ATTR_XMLNS_XSI          = "xmlns:xsi";
    public static final String ATTR_XMLNS_DUBBO        = "xmlns:dubbo";
    public static final String ATTR_XSI_SCHEMALOCATION = "xsi:schemaLocation";
    public static final String ATTR_DUBBO_APPLICATION  = "dubbo:application";

    public static final String XMLNS              = "http://www.springframework.org/schema/beans";
    public static final String XMLNS_XSI          = "http://www.w3.org/2001/XMLSchema-instance";
    public static final String XMLNS_DUBBO        = "http://code.alibabatech.com/schema/dubbo";
    public static final String XSI_SCHEMALOCATION = "http://www.springframework.org/schema/beans    http://www.springframework.org/schema/beans/spring-beans-3.0.xsd      http://code.alibabatech.com/schema/dubbo    http://code.alibabatech.com/schema/dubbo/dubbo.xsd";

    private DubboCommonConfig dubboCommonConfig;

    private List<DubboProviderConfig> providers;
    private List<ClientConfig>        clients;

    private String outputPath;

    public DubboConfig(DubboCommonConfig dubboCommonConfig) {
        this.dubboCommonConfig = dubboCommonConfig;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public DubboCommonConfig getDubboCommonConfig() {
        return dubboCommonConfig;
    }

    public List<DubboProviderConfig> getProviders() {
        return providers;
    }

    public void setProviders(List<DubboProviderConfig> providers) {
        this.providers = providers;
    }

    public List<ClientConfig> getClients() {
        return clients;
    }

    public void setClients(List<ClientConfig> clients) {
        this.clients = clients;
    }
}
