package com.chunxiao.dev.config;

import com.chunxiao.dev.util.ObjectConvertUtil;
import com.chunxiao.dev.util.StringUtil;
import com.chunxiao.dev.config.provider.ProviderConfig;

/**
 * Created by chunxiaoli on 5/27/17.
 */
public class RpcProjectConfigBuilder {

    private final RpcProjectConfig config;

    private  ProviderConfig   providerConfig;

    public RpcProjectConfigBuilder() {
        config = new RpcProjectConfig();
        providerConfig = config.getConfig();
    }


    public RpcProjectConfig build() {
        buildDubboConfig();
        System.out.print("build1:"+providerConfig);
        return config;
    }

    public void setName(String name){
        providerConfig.setName(name);
    }

    public void setDir(String dir){
        providerConfig.setDir(dir);
    }

    private void buildDubboConfig(){
        if(StringUtil.isEmpty(providerConfig.getDubboConfig().getApplicationConfig().getName())){
            providerConfig.getDubboConfig().getApplicationConfig().setName(providerConfig.getName());
        }
        if(StringUtil.isEmpty(providerConfig.getDubboConfig().getApplicationConfig().getOwner())){
            providerConfig.getDubboConfig().getApplicationConfig().setOwner(providerConfig.getOwner());
        }
        System.out.print("build:"+providerConfig);
    }

    /**
     * 设置表 逗号分隔"t_goods,t_goos_order"
     * @param tables
     */
    public void setTables(String tables){
        providerConfig.setTables(tables);
    }

    public void setDataBase(String dataBase){
        providerConfig.getMybatisConfig().setDatabase(dataBase);
    }

    public void setGroupId(String groupId){
        providerConfig.setGroupId(groupId);
    }

    public void setDBHost(String host){
        providerConfig.getMybatisConfig().setHost(host);
    }


    public void setDBConfig(String host,String port,String user,String password,String database){
        providerConfig.getMybatisConfig().setHost(host);
        providerConfig.getMybatisConfig().setPort(port);
        providerConfig.getMybatisConfig().setPassword(password);
        providerConfig.getMybatisConfig().setUsername(user);
        providerConfig.getMybatisConfig().setDatabase(database);
    }

    public void updateConfig(ProviderConfig update){
        providerConfig= ObjectConvertUtil.merge(this.providerConfig,update);
        config.updateProviderConfig(providerConfig);
    }


}
