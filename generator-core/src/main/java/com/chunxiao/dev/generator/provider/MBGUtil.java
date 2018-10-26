package com.chunxiao.dev.generator.provider;

import com.chunxiao.dev.config.MybatisConfig;
import com.chunxiao.dev.generator.project.MyBatisGenerator;
import com.chunxiao.dev.pojo.TableInfo;
import com.chunxiao.dev.util.SourceCodeUtil;
import com.chunxiao.dev.util.StringUtil;
import com.chunxiao.dev.config.provider.ProviderConfig;

import java.util.List;

/**
 * Created by chunxiaoli on 5/23/17.
 */
public class MBGUtil {

    static void createMybatisFiles(ProviderConfig config, List<TableInfo> tableList) {
        for (TableInfo tableItem : tableList) {
            doCreateMybatisFile(config,tableItem);
        }
    }

    private static void doCreateMybatisFile(ProviderConfig config, TableInfo tableItem) {

        String mavenBase = ProviderUtil.getMavenBaseDir(config);

        MybatisConfig cfg = generateMybatisConfig(config,tableItem);

        cfg.setProjectDir(mavenBase);

        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(cfg);

        myBatisGenerator.generate();
    }

    public static MybatisConfig generateMybatisConfig(ProviderConfig config, TableInfo table){
        String packageRoot = ProviderUtil.getProviderPackage(config);
        return generateMybatisConfig(config,table,packageRoot);
    }

    private static MybatisConfig generateMybatisConfig(ProviderConfig config, TableInfo table,
                                                       String packageRoot){
        MybatisConfig cfg= config.getMybatisConfig();

        cfg.setDaoPackage(packageRoot+".dao");
        cfg.setMapperPackage(packageRoot+".orm");
        cfg.setModelPackage(packageRoot+".pojo");
        cfg.setMapperXMLPackage("");

        cfg.setTable(table.getTableName());
        cfg.setDomainObjectName(table.getDomainName());
        return cfg;
    }

    static String getDomainName(TableInfo t) {
        String entityName=t.getTableName().startsWith("t_") ? t.getTableName().substring(2,
                t.getTableName().length()) : t.getTableName();
        return StringUtil.isEmpty(t.getDomainName()) ?
                SourceCodeUtil.uppercase(entityName,false) : t.getDomainName();
    }
}
