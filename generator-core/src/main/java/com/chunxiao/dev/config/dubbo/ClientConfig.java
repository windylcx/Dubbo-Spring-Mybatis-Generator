package com.chunxiao.dev.config.dubbo;

/**
 * Created by chunxiaoli on 5/31/17.
 */
public class ClientConfig {
    private String interfaceName;
    private String timeout;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }
}
