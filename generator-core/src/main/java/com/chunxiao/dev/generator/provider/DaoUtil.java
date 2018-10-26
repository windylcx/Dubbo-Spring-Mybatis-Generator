package com.chunxiao.dev.generator.provider;

import com.chunxiao.dev.config.JavaFileConfig;
import com.chunxiao.dev.util.CodeGenUtil;
import com.chunxiao.dev.codegen.ClassLoaderUtil;
import com.chunxiao.dev.config.DaoConfig;
import com.chunxiao.dev.config.MethodInfo;
import com.chunxiao.dev.config.provider.ProviderConfig;
import com.chunxiao.dev.generator.project.DaoFileGenerator;
import com.chunxiao.dev.pojo.TableInfo;
import com.chunxiao.dev.util.SourceCodeUtil;

import java.io.File;
import java.util.List;

/**
 * Created by chunxiaoli on 5/23/17.
 */
public class DaoUtil {

    public static void createDaoFiles(ProviderConfig config, List<TableInfo> tableInfos) {
        for (TableInfo t : tableInfos) {
            createDaoFile(config,t.getDomainName());
        }
    }

    static String getDaoFullName(ProviderConfig config, String domainName) {
        String entity = SourceCodeUtil.getFirstUppercase(domainName);
        String daoInterfaceName =
                entity + SourceCodeUtil.getFirstUppercase(config.getDaoDirName());

        return ProviderUtil.getProviderPackage(config) + "." + config.getDaoDirName() + "."
                + daoInterfaceName;
    }

    //生成dao
    private  static void createDaoFile(ProviderConfig config, String domainName) {
        ClassLoader classLoader = ClassLoaderUtil.loadAllClass(config.getDir());
        createDaoFile(config,domainName,classLoader);
    }

    //生成dao
    private  static void createDaoFile(ProviderConfig config, String domainName,ClassLoader classLoader) {
        List<Class> classList = CodeGenUtil
                .loadClassFromDir(ProviderUtil.getProviderOrmDir(config),classLoader);
        classList.forEach(cls -> {
            List<MethodInfo> methodInfos = CodeGenUtil.getMethods(cls);
            doCreateDaoFile(config,domainName, methodInfos,classLoader);
        });
    }

    private static void doCreateDaoFile(JavaFileConfig javaFileConfig, DaoConfig daoConfig) {
        new DaoFileGenerator(javaFileConfig, daoConfig).generateDaoImpl();
    }

    private static void doCreateDaoFile(ProviderConfig config,String domainName, List<MethodInfo> methodInfos,ClassLoader classLoader) {
        String entity = SourceCodeUtil.getFirstUppercase(domainName);

        String daoPackage = ProviderUtil.getDaoPackage(config);
        String daoImplPackage = daoPackage + ".impl";

        String baseDir = ProviderUtil.getProviderBaseDir(config);

        String daoInterfaceName =
                entity + SourceCodeUtil.getFirstUppercase(config.getDaoDirName());

        JavaFileConfig daoCfg = new JavaFileConfig();

        daoCfg.setJavaFileDoc("dao");
        daoCfg.setClassName(daoInterfaceName);
        daoCfg.setPackageName(daoPackage);
        daoCfg.setOutDir(baseDir);
        daoCfg.setMethodInfos(methodInfos);

        JavaFileConfig daoImplCfg = new JavaFileConfig();
        daoImplCfg.setJavaFileDoc("dao impl");
        daoImplCfg.setClassName(entity + "DaoImpl");
        daoImplCfg.setPackageName(daoImplPackage);
        daoImplCfg.setOutDir(baseDir);
        daoImplCfg.setMethodInfos(methodInfos);

        String mapperSuffix = SourceCodeUtil
                .getFirstUppercase(config.getMapperXmlFilePost());

        String mapperFile = ProviderUtil.getProviderOrmDir(config) + File.separator +
                SourceCodeUtil.getFirstUppercase(entity) + mapperSuffix + ".java";

        DaoConfig daoConfig = new DaoConfig();

        daoConfig.setMapper(ClassLoaderUtil.loadClassByFile(mapperFile, classLoader));

        DaoConfig daoConfigImpl = new DaoConfig();

        daoConfigImpl.setMapper(ClassLoaderUtil.loadClassByFile(mapperFile, classLoader));

        doCreateDaoFile(daoCfg, daoConfig);

        doCreateDaoFile(daoImplCfg, daoConfigImpl);

    }
}
