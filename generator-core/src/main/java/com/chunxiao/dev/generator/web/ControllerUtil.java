package com.chunxiao.dev.generator.web;

import com.chunxiao.dev.config.JavaFileConfig;
import com.chunxiao.dev.config.web.WebConfig;
import com.chunxiao.dev.generator.maven.MavenDirUtil;
import com.chunxiao.dev.util.SourceCodeUtil;
import com.chunxiao.dev.util.StringUtil;

/**
 * Created by chunxiaoli on 5/31/17.
 */
public class ControllerUtil {
    public static void createController(WebConfig config, String configClass) {
        if(config.getCgiConfig()!=null){
            config.getCgiConfig().getControllerConfigList().forEach(item->{

                String className =!StringUtil.isEmpty(item.getName())?
                        SourceCodeUtil.covertClassName(item.getName())+"Controller"
                        :config.getName() + "Controller";

                String packageNamePrefix =WebUtil.getSubPackage(config,config.getControllerDir());

                JavaFileConfig cfg = new JavaFileConfig();
                cfg.setJavaFileDoc("Controller");
                cfg.setClassName(SourceCodeUtil.getClassName(className));
                cfg.setPackageName(WebUtil.getPackageName(config,packageNamePrefix));
                cfg.setOutDir(MavenDirUtil.getMavenSourceCodeDir(config.getDir()));

                new ControllerJavaFileGenerator(cfg,DtoUtil.getDtoConfig(config)).generateControllerFile(item,configClass);
            });
        }else {
            System.out.println("cgi config is null");
        }

    }
}
