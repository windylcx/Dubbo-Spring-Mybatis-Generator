package com.chunxiao.dev.generator.provider;

import com.chunxiao.dev.config.ConfigDefault;
import com.chunxiao.dev.generator.api.ApiModuleUtil;
import com.chunxiao.dev.generator.common.PomUtil;
import com.chunxiao.dev.util.StringUtil;
import com.chunxiao.dev.config.api.ApiConfig;
import com.chunxiao.dev.config.provider.ProviderConfig;
import com.chunxiao.dev.generator.maven.PomGenerator;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chunxiaoli on 5/26/17.
 */
public class ProviderPomUtil {


    public static void createPom(ProviderConfig config){
        createRootPom(config);
        createApiPom(config,getRootPomFile(config));
        createProviderPom(config,getRootPomFile(config));
    }

    private static void createRootPom(ProviderConfig config) {
        List<String> modules = new ArrayList<>();
        modules.add(ApiModuleUtil.getApiModuleName(config));
        modules.add(ProviderUtil.getProviderModuleName(config));
        //create root pom

        String template = config.getServiceParentPomTemplatePath();

        template = StringUtil.isEmpty(template) ? ConfigDefault.POM_TEMPLATE_ROOT : template;

        PomUtil.getPomGenerator(config, modules, null, template, config.getDir(), config.getName(),"pom").generate();

    }

    public static void createProviderPom(ProviderConfig config, String rootPomPath) {
        String providerDir = ProviderUtil.getProviderDir(config);
        String module = ProviderUtil.getProviderModuleName(config);
        String template = config.getServiceProviderPomTemplatePath();
        template = StringUtil.isEmpty(template) ? ConfigDefault.POM_TEMPLATE_PROVIDER : template;


        PomGenerator generator =PomUtil.getPomGenerator(config,null,rootPomPath,template,providerDir,module,null);

        Model model = generator.build().getModel();

        Dependency dependency = new Dependency();

        //增加api 依赖
        try {
            Model api = com.chunxiao.dev.util.PomUtil.read(new FileInputStream(getApiPomFile(config)));
            assert api != null;
            if (api.getParent() != null) {
                dependency.setGroupId(api.getParent().getGroupId());
            } else if (api.getGroupId() != null) {
                dependency.setGroupId(api.getGroupId());
            } else {
            }

            dependency.setArtifactId(api.getArtifactId());
            dependency.setVersion(api.getVersion());

            model.addDependency(dependency);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        generator.write();
    }

    private static void createApiPom(ApiConfig config, String rootPomPath) {
        String module = ApiModuleUtil.getApiModuleName(config);
        String template = config.getServiceApiPomTemplatePath();
        template = StringUtil.isEmpty(template) ? ConfigDefault.POM_TEMPLATE_API : template;
        PomUtil.getPomGenerator(config, null, rootPomPath, template,
                ApiModuleUtil.getApiDir(config), module,null).generate();
    }


    public static String getApiPomFile(ProviderConfig config) {
        return ApiModuleUtil.getApiDir(config)+ File.separator + "pom.xml";
    }

    private static String getRootPomFile(ProviderConfig config) {
        return config.getDir() + File.separator + "pom.xml";
    }

}
