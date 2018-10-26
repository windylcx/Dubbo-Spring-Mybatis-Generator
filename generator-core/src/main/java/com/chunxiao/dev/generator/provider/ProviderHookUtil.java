package com.chunxiao.dev.generator.provider;

import com.chunxiao.dev.config.JavaFileConfig;
import com.chunxiao.dev.generator.common.JavaFileGenerator;
import com.chunxiao.dev.util.SourceCodeUtil;
import com.chunxiao.dev.config.provider.ProviderConfig;
import com.chunxiao.dev.generator.maven.MavenDirUtil;

/**
 * Created by chunxiaoli on 5/23/17.
 */
public class ProviderHookUtil {
    public static void createProviderHookFile(ProviderConfig config) {
        JavaFileConfig cfg = new JavaFileConfig();
        cfg.setJavaFileDoc("Hook");
        cfg.setClassName(getProviderHookClassName(config));
        cfg.setPackageName(ProviderUtil.getProviderPackage(config));
        cfg.setOutDir(MavenDirUtil.getMavenSourceCodeDir(ProviderUtil.getProviderDir(config)));
        new JavaFileGenerator(cfg).generateProviderHookFile();
    }

    private static String getProviderHookClassName(ProviderConfig config) {
        return SourceCodeUtil.covertClassName(config.getName() + "ProviderHook");
    }

    public static String getProviderHookFullName(ProviderConfig config) {
        return ProviderUtil.getProviderPackage(config) + "." + getProviderHookClassName(config);
    }

}
