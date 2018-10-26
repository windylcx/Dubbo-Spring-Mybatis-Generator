package com.chunxiao.dev.generator.provider;

import com.chunxiao.dev.config.JavaFileConfig;
import com.chunxiao.dev.config.dubbo.DubboConfig;
import com.chunxiao.dev.generator.dubbo.DubboConfigGenerator;
import com.chunxiao.dev.generator.project.DubboJavaConfigFileGenerator;
import com.chunxiao.dev.util.SourceCodeUtil;
import com.chunxiao.dev.config.ConfigDefault;
import com.chunxiao.dev.config.DubboJavaConfig;
import com.chunxiao.dev.config.dubbo.DubboProviderConfig;
import com.chunxiao.dev.config.provider.ProviderConfig;
import com.chunxiao.dev.generator.maven.MavenDirUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chunxiaoli on 5/27/17.
 */
public class DubboUtil {

    public static void createDubboFile(ProviderConfig config) {
        String base = ProviderUtil.getMavenResourceBaseDir(config);
        DubboConfig dubboConfig = ConfigDefault.getDefaultDubboConfig(config);

        dubboConfig.setOutputPath(base + File.separator + "/dubbo");
        DubboConfigGenerator generator = new DubboConfigGenerator(dubboConfig);

        addDubboClient(dubboConfig,config);
        addDubboProvider(dubboConfig,config);

        generator.generate();
    }

    public static void createDubboJavaConfigFile(ProviderConfig config,Class hookCls) {
        JavaFileConfig cfg = new JavaFileConfig();
        cfg.setJavaFileDoc("Dubbo Spring Config");
        cfg.setClassName(getDubboConfigClassName(config));
        cfg.setPackageName(ProviderUtil.getProviderPackage(config)+ ".config");
        cfg.setOutDir(MavenDirUtil.getMavenSourceCodeDir(ProviderUtil.getProviderDir(config)));
        DubboJavaConfig dubboJavaConfig = new DubboJavaConfig();
        dubboJavaConfig.setDubboConfigResourcesDir("dubbo");
        dubboJavaConfig.setHookCls(hookCls);
        new DubboJavaConfigFileGenerator(cfg,dubboJavaConfig).generateDubboConfigFile();
    }

    private static String getDubboConfigClassName(ProviderConfig config) {
        return SourceCodeUtil.covertClassName(config.getName() + "DubboConfig");
    }

    public static String getDubboConfigFullName(ProviderConfig config) {
        return ProviderUtil.getProviderPackage(config) + "." + getDubboConfigClassName(config);
    }


    private  static void addDubboProvider(DubboConfig dubboConfig,ProviderConfig config){
        List<String> services=ServiceUtil.getServiceFullNameList(config);

        List<DubboProviderConfig> configs =new ArrayList<>();
        for(String service:services){
            DubboProviderConfig provider =  new DubboProviderConfig();
            provider.setInterfaceName(service);
            configs.add(provider);
        }
        dubboConfig.setProviders(configs);
    }

    private static void addDubboClient(DubboConfig dubboConfig,ProviderConfig config){

    }
}
