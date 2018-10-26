package com.chunxiao.dev.provider;

import com.chunxiao.dev.BaseTest;
import com.chunxiao.dev.codegen.ClassLoaderUtil;
import com.chunxiao.dev.generator.api.ApiModuleUtil;
import com.chunxiao.dev.generator.provider.ApplicationPropertiesUtil;
import com.chunxiao.dev.generator.provider.DaoUtil;
import com.chunxiao.dev.generator.provider.DtoUtil;
import com.chunxiao.dev.generator.provider.MybatisUtil;
import com.chunxiao.dev.generator.provider.ProviderHookUtil;
import com.chunxiao.dev.generator.provider.ProviderPomUtil;
import com.chunxiao.dev.generator.provider.ProviderUtil;
import com.chunxiao.dev.generator.provider.ServiceUtil;
import com.chunxiao.dev.generator.provider.SpringBootAutoUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by chunxiaoli on 5/23/17.
 */
public class ProviderTest extends BaseTest{



    @Before
    public void init() {
        ApiModuleUtil.createApiDir(config);
        ProviderUtil.createDir(config);
    }

    @Test
    public void testProviderGenerator() {

    }

    @Test
    public void createPom() {
        ProviderPomUtil.createPom(config);
    }

    @Test
    public void createProviderPom() {
        ProviderPomUtil.createProviderPom(config,ProviderUtil.getProviderDir(config));
    }

    @Test
    public void testApplicationConfigUtil() {
        ApplicationPropertiesUtil.createApplicationPropertiesFile(config);
    }

    @Test
    public void testProviderHookUtil() {
        ProviderHookUtil.createProviderHookFile(config);
    }

    @Test
    public void testSpringBootAutoUtil() {
        SpringBootAutoUtil.createSpringBootAutoConfigFiles(ProviderUtil.getProviderDir(config),
                config.getSpringAutoConfigFullClassPath());
    }

    @Test
    public void testMybatisUtil() {
        MybatisUtil.createMybatisPropertiesFile(config);
    }


    @Test
    public void createOrm() throws Exception {
        MybatisUtil.createOrm(config, config.getName(), ProviderUtil.getProviderDir(config));
    }

    @Test
    public void createDaoFiles() throws Exception {
        //MybatisUtil.createOrm(config, config.getName(), ProviderUtil.getProviderDir(config));
        DaoUtil.createDaoFiles(config,MybatisUtil.getTableList(config));
    }

    @Test
    public void createDtoFromPojo() throws Exception {
        MybatisUtil.createOrm(config, config.getName(), ProviderUtil.getProviderDir(config));
        ClassLoader classLoader = ClassLoaderUtil.loadAllClass(config.getDir());
        DtoUtil.createDtoFromPojo(config,classLoader);
    }

    @Test
    public void createUtilFiles() throws Exception {
        ClassLoader classLoader = ClassLoaderUtil.loadAllClass(config.getDir());
        DtoUtil.createUtilFiles(config,classLoader,MybatisUtil.getTableList(config));
    }

    @Test
    public void createService() throws Exception {
        MybatisUtil.createOrm(config, config.getName(), ProviderUtil.getProviderDir(config));
        ClassLoader classLoader = ClassLoaderUtil.loadAllClass(config.getDir());
        DaoUtil.createDaoFiles(config,MybatisUtil.getTableList(config));
        DtoUtil.createDtoFromPojo(config,classLoader);
        classLoader = ClassLoaderUtil.loadAllClass(config.getDir());
        DtoUtil.createUtilFiles(config,classLoader,MybatisUtil.getTableList(config));
        classLoader = ClassLoaderUtil.loadAllClass(config.getDir());
        ServiceUtil.createServices(config,classLoader);
    }
}
