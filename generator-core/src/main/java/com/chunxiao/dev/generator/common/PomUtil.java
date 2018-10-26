package com.chunxiao.dev.generator.common;

import com.chunxiao.dev.config.CommonConfig;
import com.chunxiao.dev.config.ConfigDefault;
import com.chunxiao.dev.config.PomConfig;
import com.chunxiao.dev.generator.maven.PomGenerator;

import java.util.List;

/**
 * Created by chunxiaoli on 5/26/17.
 */
public class PomUtil {


    public static PomGenerator getPomGenerator(CommonConfig config, List<String> modules,
                                   String parentTemplate, String template, String dir,
                                                String moduleName,String packing){
        PomConfig cfg = ConfigDefault.getDefaultPomConfig(config, dir, moduleName);
        cfg.setTemplate(template);
        cfg.setParentTemplate(parentTemplate);
        cfg.setModules(modules);
        cfg.setPackaging(packing);
        return new PomGenerator(cfg);
    }
}
