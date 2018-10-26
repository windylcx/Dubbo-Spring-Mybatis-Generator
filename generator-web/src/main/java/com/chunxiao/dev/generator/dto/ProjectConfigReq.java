package com.chunxiao.dev.generator.dto;

import java.io.Serializable;

/**
 * Created by chunxiaoli on 7/5/17.
 */
public class ProjectConfigReq implements Serializable{
    private String config;

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    @Override
    public String toString() {
        return "ProjectConfigReq{" +
                "config='" + config + '\'' +
                '}';
    }
}
