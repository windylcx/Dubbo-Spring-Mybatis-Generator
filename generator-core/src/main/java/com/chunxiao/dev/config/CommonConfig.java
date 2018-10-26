package com.chunxiao.dev.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by chunxiaoli on 5/19/17.
 */
public class CommonConfig {


    @JsonProperty("artifact_id")
    private String artifactId;

    private String version;

    private String owner;

    @JsonProperty("name")
    private String name;

    @JsonProperty("output_dir")
    private String dir;

    private String type;

    @JsonProperty("group_id")
    private String groupId;

    private String rootDir;

    private String constantsDir;

    private String controllerDir;

    @JsonProperty("service_parent_pom")
    private String serviceParentPomTemplatePath;

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getServiceParentPomTemplatePath() {
        return serviceParentPomTemplatePath;
    }

    public void setServiceParentPomTemplatePath(String serviceParentPomTemplatePath) {
        this.serviceParentPomTemplatePath = serviceParentPomTemplatePath;
    }

    public String getRootDir() {
        return rootDir;
    }

    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }

    public String getConstantsDir() {
        return constantsDir;
    }

    public void setConstantsDir(String constantsDir) {
        this.constantsDir = constantsDir;
    }

    public String getControllerDir() {
        return controllerDir;
    }

    public void setControllerDir(String controllerDir) {
        this.controllerDir = controllerDir;
    }
}
