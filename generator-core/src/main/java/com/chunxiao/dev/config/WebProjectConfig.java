package com.chunxiao.dev.config;

import com.chunxiao.dev.config.web.WebConfig;

/**
 * Created by chunxiaoli on 5/27/17.
 */
public class WebProjectConfig {

    private WebConfig config;

    WebProjectConfig(){
        config = ConfigGenerator.getDefaultWebConfig();
    }

    public WebConfig getConfig() {
        return config;
    }
}
