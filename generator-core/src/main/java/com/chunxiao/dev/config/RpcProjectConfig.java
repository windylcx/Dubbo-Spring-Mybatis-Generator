package com.chunxiao.dev.config;

import com.chunxiao.dev.config.provider.ProviderConfig;

/**
 * Created by chunxiaoli on 5/27/17.
 */
public class RpcProjectConfig {

    private ProviderConfig config;

    RpcProjectConfig(){
        config = ConfigGenerator.getDefault();
    }

    public ProviderConfig getConfig() {
        return config;
    }

    public void updateProviderConfig(ProviderConfig providerConfig) {
        config=providerConfig;
    }
}
