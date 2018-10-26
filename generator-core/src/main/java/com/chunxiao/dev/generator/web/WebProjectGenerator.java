package com.chunxiao.dev.generator.web;

import com.chunxiao.dev.config.WebProjectConfig;
import com.chunxiao.dev.config.web.WebConfig;
import com.chunxiao.dev.generator.common.PomUtil;
import com.chunxiao.dev.pojo.CgiConfig;
import com.chunxiao.dev.util.FileUtil;
import com.chunxiao.dev.util.SourceCodeUtil;
import com.chunxiao.dev.util.StringUtil;
import com.chunxiao.dev.config.ConfigDefault;
import com.chunxiao.dev.config.WebProjectConfigBuilder;
import com.chunxiao.dev.generator.common.LauncherUtil;
import com.chunxiao.dev.generator.maven.MavenDirUtil;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by chunxiaoli on 5/27/17.
 */
public class WebProjectGenerator {

    private WebProjectConfigBuilder builder;

    private WebProjectConfig webProjectConfig;

    private WebConfig config;

    private static final Logger logger = LoggerFactory.getLogger(WebProjectGenerator.class);

    public WebProjectGenerator(WebProjectConfigBuilder builder) {
        this.builder = builder;
        this.webProjectConfig = builder.build();
        this.config = this.webProjectConfig.getConfig();

    }

    public void generate() {


        createDir();
        createPom();
        createProperties();
        createLogFile();
        createHookFile();
        createLauncherFile();

        createDtoFromJson();
        String file = createCommons();
        createControllers(file);

    }

    private String createCommons() {
        return ConstantsUtil.createCommonFiles(this.config);
    }

    private void createLauncherFile() {
        LauncherUtil.generateSpringLauncherFile(config.getGroupId() + "." + config.getName(),
                SourceCodeUtil.convertFieldUppercase(this.config.getLauncherName()),
                MavenDirUtil.getMavenSourceCodeDir(WebUtil.getWebDir(this.config)));
    }

    private void createHookFile() {
    }

    private void createLogFile() {
        String base = MavenDirUtil.getResourceBaseDir(WebUtil.getWebRootDir(this.config));
        String sourceFile = this.config.getLogginFile();
        if (!StringUtil.isEmpty(sourceFile) && new File(sourceFile).exists()) {
            FileUtil.copy(sourceFile, base + File.separator + new File(sourceFile).getName());
        } else { //create default
            InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream(ConfigDefault.LOG_CONFIG_FILE);
            FileUtil.save(inputStream, base + File.separator + "logback.xml");
        }
    }

    private void createProperties() {
        WebApplicationUtil.createApplicationPropertiesFile(this.config);
    }

    private void createPom() {
        String template = this.config.getWebPomTemplatePath();
        template = StringUtil.isEmpty(template) ? ConfigDefault.POM_TEMPLATE_WEB : template;
        PomUtil.getPomGenerator(this.config, null, null, template,
                WebUtil.getWebRootDir(config), this.config.getName(), "jar").generate();
    }

    private void createDir() {
        WebUtil.createDir(this.config);
    }

    private CgiConfig parseCgiConfig(String jsonFile) {
        final ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        try {
            return mapper.readValue(new FileInputStream(jsonFile), CgiConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //根据json协议 生成 dto
    public void createDtoFromJson() {
        if(this.config.getJsonFile()!=null){
            DtoUtil.createDtoFromJson(this.config);
        }else {
            logger.warn("json file not set....");
        }

    }

    public void createControllers(String cgiConfigs) {
        ControllerUtil.createController(this.config, cgiConfigs);
    }

}
