package com.chunxiao.dev.generator.provider;

import com.chunxiao.dev.codegen.ClassLoaderUtil;
import com.chunxiao.dev.config.JavaFileConfig;
import com.chunxiao.dev.config.PropertyConfig;
import com.chunxiao.dev.generator.common.JavaFileGenerator;
import com.chunxiao.dev.generator.common.PropertiesGenerator;
import com.chunxiao.dev.util.SourceCodeUtil;
import com.chunxiao.dev.config.provider.ProviderConfig;
import com.chunxiao.dev.generator.maven.MavenDirUtil;
import com.chunxiao.dev.pojo.TableInfo;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.TypeSpec;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by chunxiaoli on 5/23/17.
 */
public class MybatisUtil {

    private final static Logger logger = LoggerFactory.getLogger(MybatisUtil.class);

    //生成 config/module-mybatis-config.properties文件
    public static void createMybatisPropertiesFile(ProviderConfig config) {
        List<String> tables = getTableNames(getTableList(config));
        String base = ProviderUtil.getMavenResourceBaseDir(config);
        String path = base + File.separator + getMybatisPropertiesConfigPath(config.getName());
        PropertyConfig propertyConfig = new PropertyConfig();
        propertyConfig.setPath(path);

        PropertiesGenerator generator = new PropertiesGenerator(propertyConfig);


        generator.set(config.getMybatisAutoConfigPrefix()+".typeAlias." + config.getName(), ProviderUtil.getProviderPackage(config) + "." +
                config.getPojoDirName());


        if(tables!=null){
            for (String table : tables) {
                String domain = SourceCodeUtil
                        .uppercase(table.startsWith("t_") ? table.substring(2, table.length()) : table, true);
                generator.set(config.getMybatisAutoConfigPrefix()+".mapper." + domain,
                        "mybatis/" + ProviderUtil.getMapperXmlName(config,domain));
            }
        }else {
            logger.error("table list is null or empty,please check your mybatis config");
        }


        generator.generate();
    }

    private static String getMybatisPropertiesConfigPath(String module) {
        return module + "-mybatis-config.properties";
    }

    private static void createMybatisJavaConfig(ProviderConfig config, String moduleName,
                                                String classNamePrefix, String dir) {

        String propertySourceValue = "classpath:" + getMybatisPropertiesConfigPath(moduleName);

        String mapperScanValue = config.getGroupId() + "." + moduleName + "." +
                config.getProviderDirName() + "."
                + config.getOrmDirName();


        generateMybatisConfigFile(config,propertySourceValue, mapperScanValue,classNamePrefix,dir);
    }

    public static void createOrm(ProviderConfig config,String moduleName, String providerDir) {


        createMybatisJavaConfig(config,moduleName, moduleName, providerDir);
        //todo
        if (getTableList(config) != null) {

            MBGUtil.createMybatisFiles(config,getTableList(config));

            MybatisUtil.createMybatisPropertiesFile(config);
        } else {
            logger.warn("table list is null....");
        }
        //update class
        ClassLoaderUtil.loadAllClass(config.getDir());

    }

    public static void updateOrm(ProviderConfig config,String moduleName, String providerDir) {


        if (getTableList(config) != null) {

            MBGUtil.createMybatisFiles(config,getTableList(config));

            MybatisUtil.createMybatisPropertiesFile(config);
        } else {
            logger.warn("table list is null....");
        }
        //update class
        ClassLoaderUtil.loadAllClass(config.getDir());

    }






    public static List<TableInfo> getTableList(ProviderConfig config){
        List<TableInfo> ret= new ArrayList<>();
        if(config.getTableInfoList()!=null&&!config.getTableInfoList().isEmpty()){
            ret= config.getTableInfoList();
        }else if(config.getTables()!=null){
            String [] tables=config.getTables().split(",");
            for(String t:tables){
                TableInfo tableInfo = new TableInfo();
                tableInfo.setTableName(t);
                ret.add(tableInfo);
            }
        }
        return formatTableInfo(ret);
    }

    private static List<TableInfo> formatTableInfo(final List<TableInfo> tableInfos){
        for (TableInfo t : tableInfos) {
            t.setDomainName(MBGUtil.getDomainName(t));
        }
        return tableInfos;
    }

    public static List<String> getTableNames(List<TableInfo> tableInfos){
        if(tableInfos==null||tableInfos.isEmpty()){
            return null;
        }
        return tableInfos.stream().map(TableInfo::getTableName).collect(Collectors.toList());
    }


    public static void generateMybatisConfigFile(ProviderConfig config,String propertySource,
                                                 String mapperScan,String classNamePrefix, String dir) {

        JavaFileConfig cfg = new JavaFileConfig();
        cfg.setJavaFileDoc("mybatis config");
        cfg.setClassName(SourceCodeUtil.convertFieldUppercase(classNamePrefix));
        cfg.setPackageName(ProviderUtil.getProviderPackage(config) + ".config");
        cfg.setOutDir(MavenDirUtil.getMavenSourceCodeDir(dir));

        String name = SourceCodeUtil.getFirstUppercase(cfg.getClassName()) + "MybatisConfig";

        AnnotationSpec propertySourceAnnotation = AnnotationSpec.builder(PropertySource.class).
                addMember("value", "$S", propertySource).build();

        AnnotationSpec mapperScanAnnotation = AnnotationSpec.builder(MapperScan.class).
                addMember("value", "$S", mapperScan).build();

        TypeSpec.Builder builder = TypeSpec.classBuilder(name)
                .addJavadoc(cfg.getJavaFileDoc())
                .addModifiers(Modifier.PUBLIC);

        builder.addAnnotation(Configuration.class);
        builder.addAnnotation(propertySourceAnnotation);
        builder.addAnnotation(mapperScanAnnotation);
        JavaFileGenerator generator = new JavaFileGenerator(cfg);
        generator.doGenerate(builder);
    }
}
