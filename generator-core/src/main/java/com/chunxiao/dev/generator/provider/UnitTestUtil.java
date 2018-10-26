package com.chunxiao.dev.generator.provider;

import com.chunxiao.dev.codegen.ClassLoaderUtil;
import com.chunxiao.dev.config.JavaFileConfig;
import com.chunxiao.dev.generator.api.ApiModuleUtil;
import com.chunxiao.dev.util.FileUtil;
import com.chunxiao.dev.util.SourceCodeUtil;
import com.chunxiao.dev.config.provider.ProviderConfig;
import com.chunxiao.dev.generator.junit.JunitTestJavaFileUtil;
import com.chunxiao.dev.generator.maven.MavenDirUtil;

import java.io.File;
import java.util.List;

/**
 * Created by chunxiaoli on 5/23/17.
 */
public class UnitTestUtil {


    public static void createTest(ProviderConfig config) {
        ClassLoader classLoader= ClassLoaderUtil.loadAllClass(config.getDir());
        createTest(config,ProviderHookUtil.getProviderHookFullName(config),classLoader);
    }

    //生成测试文件
    public static void createTest(ProviderConfig config,String providerHookFullName,ClassLoader classLoader) {
        String rootDir =ProviderUtil.getProviderDir(config);
        String sourceCodeDir = MavenDirUtil.getMavenTestSourceCodeDir(rootDir);
        FileUtil.createDir(MavenDirUtil.getMavenTestBaseDir(rootDir));
        FileUtil.createDir(MavenDirUtil.getMavenTestResourceDir(rootDir));
        FileUtil.createDir(sourceCodeDir);

        String packageName = ProviderUtil.getProviderPackage(config);

        String baseTestClass = "BaseTest";

        JavaFileConfig serviceDaoCfg = new JavaFileConfig();
        serviceDaoCfg.setJavaFileDoc("test case");
        serviceDaoCfg.setClassName(baseTestClass);
        serviceDaoCfg.setPackageName(packageName);

        serviceDaoCfg.setOutDir(sourceCodeDir);

        JunitTestJavaFileUtil.generateBaseTestCaseFile(serviceDaoCfg,packageName + "." + baseTestClass,providerHookFullName);



        //String serviceFullClassName = ApiModuleUtil.getServiceFullClassName(config);

        List<String> services = ServiceUtil.getServiceFullNameList(config);


        for(String serviceFullClassName:services){
            String servicePath = MavenDirUtil.getMavenSourceCodeDir(ApiModuleUtil.getApiDir(config)) + File.separator
                    + SourceCodeUtil.convertPackage2Dir(serviceFullClassName) + ".java";

            Class serviceCls = ClassLoaderUtil.loadClassByFile(servicePath,classLoader);

            JunitTestJavaFileUtil.generateTestCaseFile(serviceDaoCfg,packageName + "." + baseTestClass,
                            serviceFullClassName, serviceCls);
        }



    }
}
