package com.chunxiao.dev.config.web;

import com.chunxiao.dev.config.api.ApiConfig;
import com.chunxiao.dev.pojo.CgiConfig;

/**
 * Created by chunxiaoli on 5/31/17.
 */
public class WebConfig extends ApiConfig{
    private String jsonFile;

    private CgiConfig cgiConfig;
    private String webPomTemplatePath;
    private String launcherName;
    private String webDirName;

    public CgiConfig getCgiConfig() {
        return cgiConfig;
    }

    public void setCgiConfig(CgiConfig cgiConfig) {
        this.cgiConfig = cgiConfig;
    }

    public String getJsonFile() {
        return jsonFile;
    }

    public void setJsonFile(String jsonFile) {
        this.jsonFile = jsonFile;
    }

    public String getWebPomTemplatePath() {
        return webPomTemplatePath;
    }

    public void setWebPomTemplatePath(String webPomTemplatePath) {
        this.webPomTemplatePath = webPomTemplatePath;
    }

    public String getLauncherName() {
        return launcherName;
    }

    public void setLauncherName(String launcherName) {
        this.launcherName = launcherName;
    }

    public String getWebDirName() {
        return webDirName;
    }

    public void setWebDirName(String webDirName) {
        this.webDirName = webDirName;
    }
}
