package com.chunxiao.dev.util;

import com.chunxiao.dev.generator.util.SQLUtil;
import org.junit.Test;
import org.springframework.util.FileCopyUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by chunxiaoli on 7/12/17.
 */
public class SQLUtilTest {

    @Test
    public void testParseTable(){
        String dir = "/Users/chunxiaoli/IdeaProjects/Dubbo-Spring-Mybatis-Generator/generator-web/src/test/resource/";
        try {
            FileInputStream  file = new FileInputStream(dir+"sql.sql");
            try {
                 String sqls = new String(FileCopyUtils.copyToByteArray(file),"UTF-8") ;

                 System.out.println(SQLUtil.filter(sqls));

                List<String> list= SQLUtil.parseTables(sqls);
                System.out.println(list);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
