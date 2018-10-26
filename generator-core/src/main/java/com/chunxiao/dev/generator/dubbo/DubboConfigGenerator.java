package com.chunxiao.dev.generator.dubbo;

import com.chunxiao.dev.util.FileUtil;
import com.chunxiao.dev.util.StringUtil;
import com.chunxiao.dev.config.dubbo.ClientConfig;
import com.chunxiao.dev.config.dubbo.DubboConfig;
import com.chunxiao.dev.config.dubbo.ProtocolConfig;
import com.chunxiao.dev.config.dubbo.DubboProviderConfig;
import com.chunxiao.dev.util.SourceCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xembly.Directives;
import org.xembly.ImpossibleModificationException;
import org.xembly.Xembler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by chunxiaoli on 10/18/16.
 */
public class DubboConfigGenerator {

    private DubboConfig dubboConfig;

    private static final Logger logger = LoggerFactory.getLogger(DubboConfigGenerator.class);

    private Directives directives;

    public DubboConfigGenerator(DubboConfig config) {
        this.dubboConfig = config;
        directives = new Directives().add("beans");
    }

    public void generate() {
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("template/template-dubbo-provider.xml");
        write();
    }

    public void write() {

        addCommonConfig();
        addProviderConfig();
        addClientConfig();
        try {
            doWrite(new FileOutputStream(
                    this.dubboConfig.getOutputPath() + File.separator + "dubbo.xml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void addCommonConfig() {
        directives.attr(DubboConfig.ATTR_XMLNS, DubboConfig.XMLNS);
        directives.attr(DubboConfig.ATTR_XMLNS_XSI, DubboConfig.XMLNS_XSI);
        directives.attr(DubboConfig.ATTR_XMLNS_DUBBO, DubboConfig.XMLNS_DUBBO);
        directives.attr(DubboConfig.ATTR_XSI_SCHEMALOCATION, DubboConfig.XSI_SCHEMALOCATION);

        final String address=dubboConfig.getDubboCommonConfig().getRegistryConfig().getAddress();
        final String protocol=dubboConfig.getDubboCommonConfig().getRegistryConfig().getProtocol();

        final ProtocolConfig protocolConfig=dubboConfig.getDubboCommonConfig().getProtocolConfig();

        directives.add("dubbo:application").attr("name", "${dubbo.application.name}")
                //.attr("owner", "${dubbo.application.owner}")
                .up();

        directives.add("dubbo:registry").attr("protocol", protocol)
                .attr("address",address).up();

        directives.add("dubbo:protocol").attr("name", "${dubbo.protocol.name}")
        .attr("port", protocolConfig.getPort()).up();

    }

    private void addProviderConfig() {
        directives.add("dubbo:provider").attr("retries", "0")
                .attr("loadbalance", "${dubbo.service.loadbalance}").up();

        if(this.dubboConfig.getProviders()!=null){
            for(DubboProviderConfig providerConfig:this.dubboConfig.getProviders()){
                directives.add("dubbo:service")
                        //.attr("timeout", "${dubbo.service.timeout}")
                        .attr("loadbalance", "${dubbo.service.loadbalance}")
                        .attr("interface", providerConfig.getInterfaceName())
                        .attr("ref", getInterfaceRef(providerConfig))
                        .up();
            }
        }

    }

    private void addClientConfig() {
        directives.add("dubbo:consumer").attr("retries", "0")
                .attr("loadbalance", "${dubbo.service.loadbalance}").up();

        if(this.dubboConfig.getClients()!=null){
            for(ClientConfig clientConfig:this.dubboConfig.getClients()){
                directives.add("dubbo:reference")
                        //.attr("timeout", "${dubbo.service.timeout}")
                        .attr("interface", clientConfig.getInterfaceName())
                        .attr("id", getClientId(clientConfig))
                        .up();
            }
        }

    }

    private void doWrite(FileOutputStream out){
        try {
            String content = new Xembler(directives).xml();
            System.out.println("content:" + content);
            FileUtil.write(content, out);
        } catch (ImpossibleModificationException e) {
            e.printStackTrace();
        }
    }

    private  String getInterfaceRef(DubboProviderConfig providerConfig){
        return StringUtil.isEmpty(providerConfig.getRef())?
                SourceCodeUtil.covertFieldName(SourceCodeUtil.getClassName(providerConfig.getInterfaceName()))
                :providerConfig.getRef();
    }

    private  String getClientId(ClientConfig clientConfig){
        return StringUtil.isEmpty(clientConfig.getId())?
                SourceCodeUtil.convertFieldUppercase(SourceCodeUtil.getClassName(clientConfig.getInterfaceName()))
                :clientConfig.getId();
    }
}
