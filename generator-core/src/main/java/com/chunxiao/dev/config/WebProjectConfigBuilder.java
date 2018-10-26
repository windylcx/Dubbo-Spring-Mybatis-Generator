package com.chunxiao.dev.config;

/**
 * Created by chunxiaoli on 5/27/17.
 */
public class WebProjectConfigBuilder {

    private final WebProjectConfig config;


    public WebProjectConfigBuilder() {
        config = new WebProjectConfig();

    }


    public WebProjectConfig build() {
        return config;
    }

    public void setName(String name){
        config.getConfig().setName(name);
    }

    public void setDir(String dir){
        config.getConfig().setDir(dir);
    }

    public void setGroupId(String groupId){
        config.getConfig().setGroupId(groupId);
    }

    public void setJsonFileConfig(String jsonFileConfig){
        config.getConfig().setJsonFile(jsonFileConfig);
    }


}
