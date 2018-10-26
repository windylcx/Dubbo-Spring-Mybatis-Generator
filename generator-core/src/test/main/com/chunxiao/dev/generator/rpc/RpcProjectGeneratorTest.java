package com.chunxiao.dev.generator.rpc;

import com.chunxiao.dev.config.RpcProjectConfigBuilder;
import org.junit.Test;

/**
 * Created by chunxiaoli on 5/27/17.
 */
public class RpcProjectGeneratorTest {
    @Test
    public void generate() throws Exception {
        RpcProjectConfigBuilder builder= new RpcProjectConfigBuilder();
        builder.setName("goodsorder");
        builder.setGroupId("com.chunxiao.mall");
        builder.setDBConfig("127.0.0.1","3306","root","","chunxiao_mall");
        //String tables="t_account,t_activity,t_admin,t_app,t_attribute,t_attribute_cat,t_attribute_group,t_brand,t_brand_cat,t_category,t_consignee_address,t_down_pay,t_goods,t_goods_app,t_goods_detail,t_goods_order,t_instalment,t_invoice_application,t_logistics_order,t_logistics_sku,t_promote_activity,t_promote_sku,t_refund,t_refund_water,t_seller,t_sku,t_sku_app,t_sku_snapshot,t_sms,t_supplier,t_tag,t_topic";
        String tables="t_goods,t_goods_app,t_goods_app,t_goods_detail,";
        builder.setTables(tables);
        new RpcProjectGenerator(builder).generate();
    }

    @Test
    public void updateOrm() throws Exception {
        RpcProjectConfigBuilder builder= new RpcProjectConfigBuilder();
        builder.setDir("/Users/chunxiaoli/IdeaProjects/chunxiaomall_server/goodsorder-rpc");
        builder.setName("goodsorder");
        builder.setGroupId("com.chunxiao.mall");
        builder.setDBConfig("127.0.0.1","3306","root","","chunxiao_mall");
        builder.setTables("t_sku_snapshot");
        new RpcProjectGenerator(builder).updateOrm();
    }

}
