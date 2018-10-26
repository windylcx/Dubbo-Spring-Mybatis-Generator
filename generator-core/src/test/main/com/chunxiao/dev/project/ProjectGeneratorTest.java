package com.chunxiao.dev.project;

import com.chunxiao.dev.config.ConfigGenerator;
import com.chunxiao.dev.config.GlobalConfig;
import com.chunxiao.dev.config.ProjectConfig;
import com.chunxiao.dev.util.ObjectConvertUtil;
import org.junit.Test;

/**
 * Created by chunxiaoli on 5/9/17.
 */
public class ProjectGeneratorTest {

    final static   ProjectConfig projectConfig = new ProjectConfig();

    static {
        projectConfig.setDir("");
        projectConfig.setDir("abc");
    }

    @Test
    public void testProjectGenerator(){
    }

    @Test
    public void testCreateApi(){
        GlobalConfig config = new GlobalConfig();
        config.setDir("./");
        config.setName("tt");
        config.setPojoDirName("a");
        config.setGroupId("com.chunxiao.hh");

        GlobalConfig merge = ObjectConvertUtil.merge(config, ConfigGenerator.getDefaultGlobal());

        //new DubboRPCProjectGenerator(merge).createApiDir();
        //new DubboRPCProjectGenerator(merge).createProviderDir();
    }




}
