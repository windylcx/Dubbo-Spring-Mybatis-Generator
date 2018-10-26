package com.chunxiao.dev.docs;

import com.alibaba.dubbo.common.json.JSON;
import com.chunxiao.dev.pojo.ProtocolModel;
import com.chunxiao.dev.util.CustomProtocolUtil;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by chunxiaoli on 6/15/17.
 */
public class MarkDownUtil {

    private final static String URL = "http://nobug.zeus.com/api/markdown/";

    public static void createDocs(String project, ProtocolModel model) {
        addDocs(project,model);
    }

    public static void createDocs(String dir,String project) {
        File files[] = new File(dir).listFiles();
        if(files!=null){
            for (File f:files){
                try {
                    ProtocolModel model= CustomProtocolUtil.parseProtocol(f.getAbsolutePath());
                    if(model!=null){
                        addDocs(project,model);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    private static void addDocs(String project,ProtocolModel model){
            Request request = getMarkdown(model);
            try {
                try {
                    String req=JSON.json(request);
                    String url=URL+project+"/add";
                    HttpResponse<String> res = Unirest.post(url).
                            header("Content-Type","application/json").body(req).asString();
                    System.out.println("url:"+url);
                    System.out.println("req:"+req);
                    System.out.println("res:"+res.getStatus());
                    System.out.println("res:"+res.getBody());
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    private static void modDocs(){

    }


    /*
    ### 3. 发送分期借款短信 ###
    >
    CGI URL : `/sevend_mall_app/send_loan_sms`
    >
    `请求参数 : `

    ```
    {
        "session_key": "asfwe-va3asdf-af23234",
        "principal": 1200000,	// 借款金额
        "period": 6,		// 分期期数
        "rate":"0.1245",		// 利率
        "voice_sms":1,              // 是否发送语音验证码，1-是
        "order_no" : "1415185184"	// 订单号
    }
    ```
    >
    `返回数据 :`

    ```
    {
        "code": 0,
        "subcode": 0,
        "msg": "",
        "data": {   }
    }
    ```
    * * * * *
    */

    public static Request getMarkdown(ProtocolModel model){
        Request request =new Request();
        Request.Data data = new Request.Data();
        data.setTitle(model.moduleName);
        StringBuffer stringBuffer = new StringBuffer();
        int i=1;
        for(ProtocolModel.CGI cgi:model.cgiList){
            stringBuffer.append("### ").append(i++).append(".").append(cgi.name).append(" ###\n");
            stringBuffer.append(">\n");
            stringBuffer.append("CGI URL : ").append(getLink(model.testHost,cgi.url)).append("   \n\n");
            stringBuffer.append("> \n");
            stringBuffer.append(">\n");
            stringBuffer.append("正式环境 : ").append(getLink(model.productHost,cgi.url)).append("   \n\n");
            stringBuffer.append("> \n");
            stringBuffer.append("`请求参数 : `   \n").append("\n");
            stringBuffer.append("``` \n");
            stringBuffer.append(cgi.reqStr==null?"":cgi.reqStr);
            stringBuffer.append("``` \n");
            stringBuffer.append("> \n\n");
            stringBuffer.append("`返回数据 : ` \n").append("\n");
            stringBuffer.append("```\n");
            stringBuffer.append(cgi.resStr);
            stringBuffer.append("```\n");
            stringBuffer.append("* * * * *\n");
        }
        data.setCnt(stringBuffer.toString());
        request.setData(data);
        request.setRt_time(new Date().getTime());
        return request;
    }

    private static String getLink(String host,String url){
        return "["+host+url+"]"+"("+host+url+")";
    }


    private static boolean projectExists(String project) {
        try {
            HttpResponse<String> res = Unirest.get(URL + project+"/list").asString();
            System.out.println(res.getBody());
            return res.getStatus()==200;
        } catch (UnirestException e) {
            e.printStackTrace();
            return false;
        }
    }

    static class Request{


        private Data data;
        private long rt_time;

        @Override
        public String toString() {
            return "Request{" +
                    "data=" + data +
                    ", rt_time='" + rt_time + '\'' +
                    '}';
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public long getRt_time() {
            return rt_time;
        }

        public void setRt_time(long rt_time) {
            this.rt_time = rt_time;
        }

        static class Data{
            private String title;
            private String cnt;

            @Override
            public String toString() {
                return "Data{" +
                        "title='" + title + '\'' +
                        ", cnt='" + cnt + '\'' +
                        '}';
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCnt() {
                return cnt;
            }

            public void setCnt(String cnt) {
                this.cnt = cnt;
            }
        }
    }

}
