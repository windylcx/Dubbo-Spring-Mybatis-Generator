package com.chunxiao.dev.config.dubbo;

/**
 * Created by chunxiaoli on 5/31/17.
 */
public class RegistryConfig {
    private String address;
    private String protocol;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
