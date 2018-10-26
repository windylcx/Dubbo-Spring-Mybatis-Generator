package com.chunxiao.dev.docs;

import com.chunxiao.dev.pojo.ProtocolModel;
import com.chunxiao.dev.util.CustomProtocolUtil;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by chunxiaoli on 6/15/17.
 */
public class DocsUtilTest {
    @Test
    public void createMarkDownDocs() throws FileNotFoundException {
        File dir = new File("./docs");
        File[] files = dir.listFiles();
        if(files!=null){
            for (File file :files) {
                InputStream in = new FileInputStream(file);
                try {
                    ProtocolModel protocolModel = CustomProtocolUtil.parseProtocol(in);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void getMarkDown() throws FileNotFoundException {
        String file="./docs/app商品";
        try {
            ProtocolModel protocolModel = CustomProtocolUtil.parseProtocol(new FileInputStream(file));
            System.out.println("markdown:"+MarkDownUtil.getMarkdown(protocolModel));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addDocs() throws FileNotFoundException {
        String file="/Users/chunxiaoli/IdeaProjects/server_docs/docs/协议/v1.1/APP订单";
        try {
            ProtocolModel protocolModel = CustomProtocolUtil.parseProtocol(file);
            System.out.println("markdown:"+MarkDownUtil.getMarkdown(protocolModel));
            MarkDownUtil.createDocs("shop",protocolModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createDocs() throws FileNotFoundException {
        String dir="/Users/chunxiaoli/IdeaProjects/server_docs/docs/协议/v1.1";
        MarkDownUtil.createDocs(dir,"shop");
    }

    @Test
    public void createDtoFromDocs(){
        String file="/Users/chunxiaoli/IdeaProjects/server_docs/docs/协议/v1.1/APP订单";
        ProtocolUtil.generateDto(file);
    }


}
