package com.chunxiao.dev.config.dubbo;

/**
 * Created by chunxiaoli on 5/31/17.
 */
public class DubboProviderConfig {
    private String interfaceName;
    private String timeout;

    private String ref;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
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
