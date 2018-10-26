package com.chunxiao.dev.generator.common;

import com.chunxiao.dev.config.JavaFileConfig;
import com.chunxiao.dev.generator.provider.ProviderUtil;
import com.chunxiao.dev.util.SourceCodeUtil;
import com.chunxiao.dev.config.provider.ProviderConfig;
import com.chunxiao.dev.generator.maven.MavenDirUtil;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.lang.model.element.Modifier;
import java.util.Date;

/**
 * Created by chunxiaoli on 5/27/17.
 */
public class LauncherUtil {
    public static void createLauncher(ProviderConfig config){


        generateSpringLauncherFile(getPackageName(config),getLauncherName(config),
                MavenDirUtil.getMavenSourceCodeDir(ProviderUtil.getProviderDir(config)));
    }

    private static String getLauncherName(ProviderConfig config){
        return config.getName()+ SourceCodeUtil.getFirstUppercase(config.getProviderDirName());
    }


    private static  String getPackageName(ProviderConfig config){
        return config.getGroupId()+"."+config.getName() + "." + config.getProviderDirName();
    }

    public static void generateSpringLauncherFile(String packageName,String launcherName,String outDir) {

        JavaFileConfig cfg= new JavaFileConfig();
        cfg.setJavaFileDoc("启动入口");
        cfg.setClassName(SourceCodeUtil.covertClassName(launcherName));
        cfg.setPackageName(packageName);
        cfg.setOutDir(outDir);

        String name = cfg.getClassName() + "Launcher";

        TypeSpec.Builder builder = TypeSpec.classBuilder(name)
                .addJavadoc(cfg.getJavaFileDoc())
                .addModifiers(Modifier.PUBLIC);
        builder.addAnnotation(SpringBootApplication.class);

        String pool = "threadPoolTaskScheduler";
        String spring = "springApplication";

        FieldSpec logger = JavaFileGenerator.getLogger(JavaFileGenerator.getClassName(name));

        builder.addField(logger);

        String args = "args";

        TypeSpec thread = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(ParameterizedTypeName.get(Runnable.class))
                .addMethod(MethodSpec.methodBuilder("run")
                        .addModifiers(Modifier.PUBLIC)
                        .build())
                .build();

        MethodSpec m = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$N.debug(\"$N start:{}\",$N)", logger, name, args)
                .addStatement("$T.out.println(\"$N start.....\")", System.class, name)
                .addStatement("$T $N =new $T()", ThreadPoolTaskScheduler.class, pool,
                        ThreadPoolTaskScheduler.class)
                .addStatement("$N.initialize()", pool)
                .addStatement("$N.setPoolSize(1)", pool)
                .addStatement("$N.scheduleAtFixedRate($L,new $T(),60*1000L)", pool, thread,
                        Date.class)
                .addStatement("$T $N=new $T($N.class)", SpringApplication.class, spring,
                        SpringApplication.class, name)
                .addStatement("$N.addListeners(new $T())", spring, ApplicationPidFileWriter.class)
                .addStatement("$N.run($N)", spring, args)
                .build();
        builder.addMethod(m);

        JavaFileGenerator generator=new JavaFileGenerator(cfg);

        generator.doGenerate(builder);
    }




}
