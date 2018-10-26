package com.chunxiao.dev.pojo;

import java.util.List;
import java.util.Map;

/**
 * Created by m000665 on 2017/2/16.
 */
public class ProtocolModel {
    public String testHost;
    public String productHost;
    public String moduleName;
    public String pathPrefix;
    public String module;
    public String desc;
    public List<CGI> cgiList;

    public static class CGI {
        public String requestWay;
        public String url;
        public String name;
        public Map    request;
        public Object    response;
        public String reqStr;
        public String resStr;

        @Override
        public String toString() {
            return "CGI{" +
                    "requestWay='" + requestWay + '\'' +
                    ", url='" + url + '\'' +
                    ", name='" + name + '\'' +
                    ", request=" + request +
                    ", response=" + response +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ProtocolModel{" +
                "testHost='" + testHost + '\'' +
                ", productHost='" + productHost + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", pathPrefix='" + pathPrefix + '\'' +
                ", module='" + module + '\'' +
                ", desc='" + desc + '\'' +
                ", cgiList=" + cgiList +
                '}';
    }
}
