package com.chunxiao.dev.generator.junit;

import com.chunxiao.dev.config.JavaFileConfig;
import com.chunxiao.dev.generator.common.JavaFileGenerator;
import com.chunxiao.dev.util.SourceCodeUtil;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.lang.model.element.Modifier;

/**
 * Created by chunxiaoli on 10/19/16.
 */
public class JunitTestJavaFileUtil {

    private static final Logger logger = LoggerFactory.getLogger(JunitTestJavaFileUtil.class);


    public static void generateTestCaseFile(JavaFileConfig config, String baseTestFullName, String serviceFullClassName, Class serviceCls) {

        TypeName serviceType = JavaFileGenerator.resolveType(serviceFullClassName);

        String serviceField = SourceCodeUtil.covertFieldName(JavaFileGenerator.getClassName(serviceFullClassName));

        String testClassName =
                SourceCodeUtil.covertClassName(JavaFileGenerator.getClassName(serviceFullClassName)) + "Test";

        TypeName baseTest = JavaFileGenerator.resolveType(baseTestFullName);

        TypeSpec.Builder builder = TypeSpec.classBuilder(testClassName)
                .superclass(baseTest)
                .addJavadoc(config.getJavaFileDoc())
                .addModifiers(Modifier.PUBLIC);

        //Class serviceCls = CompilerUtil.loadFromFile(serviceJavaPath);

        if (serviceCls == null) {
            logger.error("serviceCls is null ");
            return;
        }

        FieldSpec field = FieldSpec.builder(serviceCls, serviceField, Modifier.PRIVATE)
                .addAnnotation(Resource.class)
                .build();

        FieldSpec logger = JavaFileGenerator.getLogger(JavaFileGenerator.getClassName(baseTestFullName));

        builder.addField(logger);
        //Service 没法正确自动import进来 这里先注释掉
        builder.addField(field);

        //test method

        MethodSpec m = MethodSpec.methodBuilder(serviceField)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("$N.debug(\"$N start:{}\",$N)", logger, serviceField, field)
                .addStatement("$T.assertNotNull($N)", Assert.class, field)
                .addAnnotation(Test.class)
                .build();

        builder.addMethod(m);
        new JavaFileGenerator(config).doGenerate(builder);

    }

    //todo 自动生成测试用例
    private void addServiceTest(TypeSpec.Builder builder,Class serviceCls){
        java.lang.reflect.Method[] methods = serviceCls.getMethods();
        /*for (java.lang.reflect.Method m:methods) {
            Class retType =m.getReturnType();
            MethodSpec mt = MethodSpec.methodBuilder("test"+SourceCodeUtil.getFirstUppercase(m.getName()))
                    .addModifiers(Modifier.PUBLIC)
                    .addStatement("$N.debug(\"$N start:{}\",$N)", logger, serviceField, field)
                    .addStatement("$T.assertNotNull($N)", Assert.class, field)
                    .addAnnotation(Test.class)
                    .build();

            builder.addMethod(mt);
        }*/

        /**/


    }

    //生成provider BaseTest文件
    public static void generateBaseTestCaseFile(JavaFileConfig javaFileConfig,String baseTestFullName, String hookClassFullName) {

        String clsName = JavaFileGenerator.getClassName(baseTestFullName);

        ClassName hookCls = JavaFileGenerator.resolveClassName(hookClassFullName);

        TypeSpec.Builder builder = TypeSpec.classBuilder(clsName)
                .addJavadoc(javaFileConfig.getJavaFileDoc())
                .addAnnotation(AnnotationSpec.builder(RunWith.class)
                        .addMember("value", "$T$N", SpringJUnit4ClassRunner.class,
                                ".class").build())
                .addAnnotation(AnnotationSpec.builder(SpringBootTest.class)
                        .addMember("classes", "$T$N",
                                hookCls, ".class").build())
                .addAnnotation(Transactional.class)
                .addAnnotation(AnnotationSpec.builder(Rollback.class)
                        .addMember("value",
                                "$L", true).build())
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);

        FieldSpec logger = JavaFileGenerator.getLogger(JavaFileGenerator.getClassName(clsName));
        builder.addField(logger);

        new JavaFileGenerator(javaFileConfig).doGenerate(builder);
    }
}
