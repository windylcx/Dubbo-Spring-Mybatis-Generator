package com.chunxiao.dev.generator.provider;

import com.chunxiao.dev.config.JavaFileConfig;
import com.chunxiao.dev.config.ServiceConfig;
import com.chunxiao.dev.generator.api.ApiModuleUtil;
import com.chunxiao.dev.util.CodeGenUtil;
import com.chunxiao.dev.util.SourceCodeUtil;
import com.chunxiao.dev.codegen.ClassLoaderUtil;
import com.chunxiao.dev.config.provider.ProviderConfig;
import com.chunxiao.dev.generator.maven.MavenDirUtil;
import com.chunxiao.dev.generator.project.ServiceFileGenerator;
import com.chunxiao.dev.pojo.TableInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by chunxiaoli on 5/25/17.
 */
public class ServiceUtil {


    public static void createServices(ProviderConfig config, ClassLoader classLoader) {
        if(config.getExportServiceSeperately()==1){
            List<String> services=getServiceList(config);
            for(String service:services){
                List<ServiceConfig.ServiceDaoInfo> list = getDaoInfoList(config, classLoader);
                list=list.stream().filter(item-> item.getDao().getSimpleName().replace("Dao","").toLowerCase()
                        .matches(service.toLowerCase().replace(config.getServiceFilePost(),""))).collect(Collectors.toList());
                createService(config,classLoader,service,list);
            }
        }else {
            createService(config,classLoader,ProviderUtil.getServiceName(config),getDaoInfoList(config, classLoader));
        }

    }


    //生成api service
    public static void createService(ProviderConfig config, ClassLoader classLoader,
                                     String serviceName,List<ServiceConfig.ServiceDaoInfo> daoInfos) {


        JavaFileConfig cfg = new JavaFileConfig();
        cfg.setJavaFileDoc(config.getName() + " RPC interface");
        cfg.setClassName(serviceName);
        cfg.setPackageName(ApiModuleUtil.getApiPackage(config));
        cfg.setOutDir(MavenDirUtil.getMavenSourceCodeDir(ApiModuleUtil.getApiDir(config)));

        //

        ServiceConfig serviceConfig = new ServiceConfig();

        serviceConfig.setDtoPackageName(ProviderUtil.getDtoPackage(config));
        //serviceConfig.setDaoList(daoCls);
        serviceConfig.setDaoInfos(daoInfos);

        final String utilPackage = ProviderUtil.getProviderUtilPackage(config);

        new ServiceFileGenerator(cfg, serviceConfig)
                .generateServiceInterfaceFile(classLoader, utilPackage);

        JavaFileConfig implCfg = new JavaFileConfig();
        implCfg.setJavaFileDoc(config.getName() + " RPC interface impl");
        implCfg.setClassName(getServiceImplName(serviceName));
        implCfg.setPackageName(ProviderUtil.getProviderPackage(config) + ".impl");
        implCfg.setOutDir(MavenDirUtil.getMavenSourceCodeDir(ProviderUtil.getProviderDir(config)));

        String serviceFullName = ApiModuleUtil.getApiServicePackage(config) + "." + serviceName;

        new ServiceFileGenerator(implCfg, serviceConfig)
                .generateServiceImplFile(serviceFullName, classLoader, utilPackage);
    }

    private static String getServiceImplName(String serviceInterfaceName){
        return  serviceInterfaceName+"Impl";
    }


    public static List<String> getServiceList(ProviderConfig config){
        List<String> services = new ArrayList<>();
        if(config.getExportServiceSeperately()==1){
            for (TableInfo item : MybatisUtil.getTableList(config)) {
                services.add(getServiceName(config,item.getDomainName()));
            }
        }else {
            services.add(ProviderUtil.getServiceName(config));
        }
        return services;
    }


    public static List<ServiceConfig.ServiceDaoInfo> getDaoInfoList(ProviderConfig config, ClassLoader classLoader) {
        List<ServiceConfig.ServiceDaoInfo> serviceDaoInfos = new ArrayList<>();
        List<Class> daoList = CodeGenUtil
                .loadClassFromDir(ProviderUtil.getProviderDaoDir(config), classLoader);
        daoList.forEach(item -> {
            String name = item.getSimpleName();
            if (name.endsWith("Dao")) {
                name = name.replace("Dao", "");
                Class pojo = ClassLoaderUtil
                        .loadClassByFile(ProviderUtil.getProviderPojoDir(config) + File.separator + name + ".java",
                                classLoader);
                ServiceConfig.ServiceDaoInfo info = new ServiceConfig.ServiceDaoInfo();
                info.setDao(item);
                info.setPojo(pojo);
                serviceDaoInfos.add(info);
            }
        });

        return serviceDaoInfos;
    }

    private static String getServiceName(ProviderConfig config, String domainName) {
        return domainName+ SourceCodeUtil.getFirstUppercase(config.getServiceFilePost());
    }

    public static String  getServiceFullName(ProviderConfig config,String serviceName){
        return ApiModuleUtil.getApiServicePackage(config) + "." + serviceName;
    }

    public static List<String> getServiceFullNameList(ProviderConfig config){
        List<String> services = new ArrayList<>();
        if(config.getExportServiceSeperately()==1){
            for (TableInfo item : MybatisUtil.getTableList(config)) {
                services.add(getServiceFullName(config,getServiceName(config,item.getDomainName())));
            }
        }else {
            services.add(getServiceFullName(config,ProviderUtil.getServiceName(config)));
        }
        return services;
    }
}
