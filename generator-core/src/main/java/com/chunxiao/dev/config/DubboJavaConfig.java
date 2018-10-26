package com.chunxiao.dev.config;

/**
 * Created by chunxiaoli on 6/30/17.
 */
public class DubboJavaConfig {
    private String dubboConfigResourcesDir;
    private Class hookCls;

    public String getDubboConfigResourcesDir() {
        return dubboConfigResourcesDir;
    }

    public void setDubboConfigResourcesDir(String dubboConfigResourcesDir) {
        this.dubboConfigResourcesDir = dubboConfigResourcesDir;
    }

    public Class getHookCls() {
        return hookCls;
    }

    public void setHookCls(Class hookCls) {
        this.hookCls = hookCls;
    }
}
