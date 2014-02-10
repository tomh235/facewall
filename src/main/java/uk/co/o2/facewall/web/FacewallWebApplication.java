package uk.co.o2.facewall.web;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.MvcFeature;
import uk.co.o2.facewall.view.FreemarkerTemplateProcessor;

public class FacewallWebApplication extends ResourceConfig {

    public FacewallWebApplication() {
        packages(FacewallWebApplication.class.getPackage().getName());
        register(MvcFeature.class);
        register(FreemarkerTemplateProcessor.class);
    }
}
