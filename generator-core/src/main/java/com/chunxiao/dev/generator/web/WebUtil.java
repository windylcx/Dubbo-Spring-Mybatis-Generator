package com.chunxiao.dev.generator.web;

import com.chunxiao.dev.config.web.WebConfig;
import com.chunxiao.dev.util.FileUtil;
import com.chunxiao.dev.util.StringUtil;
import com.chunxiao.dev.config.Config;
import com.chunxiao.dev.config.ConfigDefault;
import com.chunxiao.dev.generator.maven.MavenDirUtil;

import java.io.File;

/**
 * Created by chunxiaoli on 5/31/17.
 */
public class WebUtil {

    public static void createDir(WebConfig config){
        String webDir = getRootDir(config);
        FileUtil.createDir(webDir);

        MavenDirUtil.createModuleMavenStructure(webDir,config.getGroupId(),config.getName());

        for (String sub : Config.WEB_SERVER_DIRS) {
            String subDir =getWebSourceCodeParentDir(config)
                    + File.separator + sub;
            FileUtil.createDir(subDir);
        }
    }

    //返回根目录
    private  static String getRootDir(WebConfig config) {
        return getWebRootDir(config);
    }



    public static String getWebRootDir(WebConfig webConfig){
        String parentDir=webConfig.getDir();
        return StringUtil.isEmpty(parentDir)?getWebModuleName(webConfig)
                :(parentDir+File.separator+getWebModuleName(webConfig));
    }

    public  static String getSubPackage(WebConfig config, String subDir) {
        return config.getName() + "." + subDir;
    }

    public  static String getPackageName(WebConfig config,String prefix){
        return config.getGroupId()+"."+prefix;
    }

    public static String getWebDir(WebConfig config) {
        return config.getDir() + File.separator + getWebModuleName(config);
    }

    //return "server"
    private static String getWebDirPostName(WebConfig config){
        return (StringUtil.isEmpty(config.getWebDirName())?
                ConfigDefault.GLOBAL_DIR_WEB:config.getWebDirName());
    }
    //app-server/test-server/task-server
    static  String getWebModuleName(WebConfig config) {
        return config.getName() + "-" + getWebDirPostName(config);
    }

    //out/app-server/src/main/java/www/chunxiao/li/app/
    public static  String getWebSourceCodeParentDir(WebConfig config) {
        return MavenDirUtil.getSourceCodeBaseDirOfGroup(getRootDir(config),config.getGroupId(),config.getName());
    }

    public static  String getWebResourceDir(WebConfig config) {
        return MavenDirUtil.getResourceBaseDir(getWebDir(config));
    }

    public static String getWebPackage(WebConfig config) {
        return config.getGroupId()+"."+config.getName()+ "." + getWebDirPostName(config);
    }
}
