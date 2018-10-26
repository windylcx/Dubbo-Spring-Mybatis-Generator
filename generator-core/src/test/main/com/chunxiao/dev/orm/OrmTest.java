package com.chunxiao.dev.orm;

import com.chunxiao.dev.BaseTest;
import com.chunxiao.dev.generator.provider.MybatisUtil;
import com.chunxiao.dev.generator.provider.ProviderUtil;
import org.junit.Test;

/**
 * Created by chunxiaoli on 5/9/17.
 */
public class OrmTest extends BaseTest{
    @Test
    public void testCreateOrm(){

        config.setTables("t_sku_snapshot");
        config.setName("goodsorder");
        config.setGroupId("com.chunxiao.mall");
        MybatisUtil.updateOrm(config, config.getName(), ProviderUtil.getProviderDir(config));
    }
}
