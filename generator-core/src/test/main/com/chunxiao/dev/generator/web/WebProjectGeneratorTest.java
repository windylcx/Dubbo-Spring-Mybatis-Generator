package com.chunxiao.dev.generator.web;

import com.chunxiao.dev.config.WebProjectConfigBuilder;
import org.junit.Test;

/**
 * Created by chunxiaoli on 5/31/17.
 */
public class WebProjectGeneratorTest {
    @Test
    public void generate() throws Exception {
        WebProjectConfigBuilder builder = new WebProjectConfigBuilder();
        builder.setJsonFileConfig("");
        new WebProjectGenerator(builder).generate();
    }

}
