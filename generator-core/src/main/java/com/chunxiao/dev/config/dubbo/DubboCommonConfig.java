package com.chunxiao.dev.config.dubbo;

/**
 * Created by chunxiaoli on 5/31/17.
 */
public class DubboCommonConfig {

    private ApplicationConfig applicationConfig;
    private ProtocolConfig protocolConfig;
    private RegistryConfig registryConfig;
    private ClientConfig clientConfig;
    private DubboProviderConfig dubboProviderConfig;

    public ApplicationConfig getApplicationConfig() {
        return applicationConfig;
    }

    public void setApplicationConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    public ProtocolConfig getProtocolConfig() {
        return protocolConfig;
    }

    public void setProtocolConfig(ProtocolConfig protocolConfig) {
        this.protocolConfig = protocolConfig;
    }

    public RegistryConfig getRegistryConfig() {
        return registryConfig;
    }

    public void setRegistryConfig(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
    }

    public ClientConfig getClientConfig() {
        return clientConfig;
    }

    public void setClientConfig(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    public DubboProviderConfig getDubboProviderConfig() {
        return dubboProviderConfig;
    }

    public void setDubboProviderConfig(DubboProviderConfig dubboProviderConfig) {
        this.dubboProviderConfig = dubboProviderConfig;
    }

    @Override
    public String toString() {
        return "DubboCommonConfig{" +
                "applicationConfig=" + applicationConfig +
                ", protocolConfig=" + protocolConfig +
                ", registryConfig=" + registryConfig +
                ", clientConfig=" + clientConfig +
                ", dubboProviderConfig=" + dubboProviderConfig +
                '}';
    }
}
