package com.chunxiao.dev.generator.rpc;

import com.chunxiao.dev.codegen.ClassLoaderUtil;
import com.chunxiao.dev.config.RpcProjectConfig;
import com.chunxiao.dev.config.RpcProjectConfigBuilder;
import com.chunxiao.dev.generator.api.ApiModuleUtil;
import com.chunxiao.dev.generator.common.LauncherUtil;
import com.chunxiao.dev.generator.provider.*;
import com.chunxiao.dev.config.provider.ProviderConfig;

/**
 * Created by chunxiaoli on 5/27/17.
 */
public class RpcProjectGenerator {

    private RpcProjectConfigBuilder builder;

    private RpcProjectConfig rpcProjectConfig;

    private ProviderConfig providerConfig;


    public RpcProjectGenerator(RpcProjectConfigBuilder builder){
        this.builder=builder;
        this.rpcProjectConfig =builder.build();
        this.providerConfig=this.rpcProjectConfig.getConfig();
    }

    public RpcProjectConfig getRpcProjectConfig(){
        return this.rpcProjectConfig;
    }

    public String generate(){
        createDir();
        createPom();
        createProperties();
        createAutoConfig();
        createLogFile();

        createHookFile();

        createDubboJavaConfigFile();

        createLauncherFile();

        createService();

        createDubbo();

        createTest();

        return providerConfig.getDir();

    }
    //新增表
    public void updateOrm(){
        final  ProviderConfig config = providerConfig;

        MybatisUtil.updateOrm(config, config.getName(), ProviderUtil.getProviderDir(config));
        ClassLoader classLoader = ClassLoaderUtil.loadAllClass(config.getDir());
        DaoUtil.createDaoFiles(config,MybatisUtil.getTableList(config));
        DtoUtil.createDtoFromPojo(config,classLoader);
        classLoader = ClassLoaderUtil.loadAllClass(config.getDir());
        DtoUtil.createUtilFiles(config,classLoader,MybatisUtil.getTableList(config));
        classLoader = ClassLoaderUtil.loadAllClass(config.getDir());
        ServiceUtil.createServices(config,classLoader);
    }

    private void createDir(){
        ApiModuleUtil.createApiDir(rpcProjectConfig.getConfig());
        ProviderUtil.createDir(rpcProjectConfig.getConfig());
    }

    private void createPom(){
        ProviderPomUtil.createPom(rpcProjectConfig.getConfig());
    }

    private void createAutoConfig(){
        SpringBootAutoUtil.createSpringBootAutoConfigFiles(providerConfig);
    }

    private void createProperties(){
        ApplicationPropertiesUtil.createApplicationPropertiesFile(providerConfig);
    }

    private void createLogFile(){
        LogFileUtil.createLogConfig(providerConfig);
    }

    private void createHookFile(){
        ProviderHookUtil.createProviderHookFile(providerConfig);
    }

    private void createDubboJavaConfigFile(){
        ClassLoader classLoader = ClassLoaderUtil.loadAllClass(providerConfig.getDir());
        String clsName = ProviderHookUtil.getProviderHookFullName(providerConfig);
        Class hookCls = ClassLoaderUtil.load(clsName, classLoader);

        if(hookCls!=null){
            DubboUtil.createDubboJavaConfigFile(providerConfig,hookCls);
        }

    }

    private void createLauncherFile(){
        LauncherUtil.createLauncher(providerConfig);
    }


    private void createService(){
        final  ProviderConfig config = providerConfig;

        MybatisUtil.createOrm(config, config.getName(), ProviderUtil.getProviderDir(config));
        ClassLoader classLoader = ClassLoaderUtil.loadAllClass(config.getDir());
        DaoUtil.createDaoFiles(config,MybatisUtil.getTableList(config));
        DtoUtil.createDtoFromPojo(config,classLoader);
        classLoader = ClassLoaderUtil.loadAllClass(config.getDir());
        DtoUtil.createUtilFiles(config,classLoader,MybatisUtil.getTableList(config));
        classLoader = ClassLoaderUtil.loadAllClass(config.getDir());
        ServiceUtil.createServices(config,classLoader);
    }

    private void createDubbo(){
        DubboUtil.createDubboFile(providerConfig);
    }

    private void createTest(){
        UnitTestUtil.createTest(providerConfig);
    }
}
