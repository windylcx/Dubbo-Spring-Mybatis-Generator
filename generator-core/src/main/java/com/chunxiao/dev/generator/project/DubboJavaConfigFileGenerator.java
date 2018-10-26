package com.chunxiao.dev.generator.project;

import com.chunxiao.dev.config.JavaFileConfig;
import com.chunxiao.dev.generator.common.JavaFileGenerator;
import com.chunxiao.dev.config.DubboJavaConfig;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.TypeSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import javax.lang.model.element.Modifier;

/**
 * Created by chunxiaoli on 1/5/17.
 */
public class DubboJavaConfigFileGenerator {

    private static final Logger logger = LoggerFactory
            .getLogger(DubboJavaConfigFileGenerator.class);

    private JavaFileGenerator generator;

    private JavaFileConfig javaFileConfig;

    private DubboJavaConfig dubboJavaConfig;

    public DubboJavaConfigFileGenerator(JavaFileConfig javaFileConfig, DubboJavaConfig dubboJavaConfig) {
        this.javaFileConfig = javaFileConfig;
        this.dubboJavaConfig = dubboJavaConfig;
        this.generator = new JavaFileGenerator(javaFileConfig);
    }

    /*
        @Configuration
        @Import({ GoodsorderProviderHook.class})
        @ImportResource({ "classpath:dubbo/*.xml" })
        public class GoodsOrderDubboConfig {
        }
     */
    public void generateDubboConfigFile() {
        TypeSpec.Builder builder = TypeSpec.classBuilder(javaFileConfig.getClassName())
                .addJavadoc(javaFileConfig.getJavaFileDoc())
                .addModifiers(Modifier.PUBLIC);

        builder.addAnnotation(Configuration.class);

        AnnotationSpec importAnno = AnnotationSpec.builder(Import.class).
                addMember("value", "{$T.class}",
                dubboJavaConfig.getHookCls()).build();

        AnnotationSpec importResourceAnno = AnnotationSpec.builder(ImportResource.class)
                .addMember("value", "{\"classpath:$N\"}",
                        dubboJavaConfig.getDubboConfigResourcesDir()+"/*.xml").build();

        builder.addAnnotation(importAnno);
        builder.addAnnotation(importResourceAnno);

        generator.doGenerate(builder);
    }
}
