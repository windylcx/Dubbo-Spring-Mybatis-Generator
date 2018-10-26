package com.chunxiao.dev.generator.web;

import com.chunxiao.dev.config.JavaFileConfig;
import com.chunxiao.dev.config.web.WebConfig;
import com.chunxiao.dev.generator.common.JavaFileGenerator;
import com.chunxiao.dev.pojo.ControllerConfig;
import com.chunxiao.dev.util.SourceCodeUtil;
import com.chunxiao.dev.pojo.CgiInfo;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by chunxiaoli on 5/31/17.
 */
public class ConstantsUtil {
    public static String createCommonFiles(WebConfig config) {
        JavaFileConfig cfg= new JavaFileConfig();
        cfg.setJavaFileDoc("controller cgi constants");
        cfg.setClassName(SourceCodeUtil.getClassName("CgiConstants"));
        cfg.setPackageName(config.getConstantsDir());
        cfg.setOutDir(WebUtil.getWebSourceCodeParentDir(config));
        return generateConstantsConfigFile(cfg,getUrls(config));
    }

    private static List<String> getUrls(WebConfig config) {
        return config.getCgiConfig()!=null?config.getCgiConfig().getControllerConfigList().stream().map(ControllerConfig::getCgiInfoList).
                collect(Collectors.toList()).stream().flatMap(Collection::stream).
                collect(Collectors.toList()).stream().map(CgiInfo::getCgi).collect(Collectors.toList()):new ArrayList<>();
    }

    public static String generateConstantsConfigFile(JavaFileConfig javaFileConfig,List<String> urls) {

        JavaFileGenerator generator=new JavaFileGenerator(javaFileConfig);

        TypeSpec.Builder builder = TypeSpec.classBuilder(javaFileConfig.getClassName())
                .addJavadoc(javaFileConfig.getJavaFileDoc())
                .addModifiers(Modifier.PUBLIC);

        for (String cgi : urls) {
            FieldSpec field = FieldSpec.builder(String.class, cgi.toUpperCase(),
                    Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .initializer("\"$N\"", cgi)
                    .build();
            builder.addField(field);
        }


        return generator.doGenerate(builder);
    }
}
