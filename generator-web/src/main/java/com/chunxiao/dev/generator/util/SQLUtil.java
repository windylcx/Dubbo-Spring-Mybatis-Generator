package com.chunxiao.dev.generator.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chunxiaoli on 7/12/17.
 */
public class SQLUtil {
    private final static String CREATE_TABLE   = "(CREATE TABLE|create table) (.*) \\(";
    private final static String packageReg = "package\\s+[a-zA-Z0-9.]+;";

    private final static Pattern CREATE_TABLE_PARTTERN = Pattern.compile(CREATE_TABLE);

    public static List<String> parseTables(String sql){
        Matcher m = CREATE_TABLE_PARTTERN.matcher(sql);
        List<String> tables= new ArrayList<>();
        while (m.find()){
            String t=m.group();

            t=t.replaceAll("(CREATE TABLE|create table)","").
                    replaceAll("\\(","").
                    replaceAll("`","");
            System.out.println(t);
            tables.add(t.trim());
        }
        return tables;
    }

    public static String filter(String sql){
        return sql.replaceAll("^#.*$","").replaceAll("#.*\n","").
                replaceAll("/\\*.*\\*/;","")
                .replaceAll("\\n","");
    }
}
