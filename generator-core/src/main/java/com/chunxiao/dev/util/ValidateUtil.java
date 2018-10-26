package com.chunxiao.dev.util;

import com.chunxiao.dev.config.GlobalConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chunxiaoli on 12/29/16.
 */
public class ValidateUtil {


    public static List<String> validateConfig(GlobalConfig config){
        List<String> msgs=new ArrayList<>();
        /*if(!FileUtil.checkPermission(config.getDir())){
            msgs.add(config.getDir()+" permission deny");
        }*/
        if(!DBConnTestUtil.testDBConn(config.getMybatisConfig())){
            msgs.add("数据库配置有误");
        }


        return msgs;
    }

    public static boolean isGroupId(String groupId){
        return !StringUtil.isEmpty(groupId)&&groupId.contains(".");
    }
}
