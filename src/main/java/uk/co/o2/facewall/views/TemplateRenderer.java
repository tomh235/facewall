package uk.co.o2.facewall.views;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;

public class TemplateRenderer {

    private final static Configuration configuration = initConfig();

    private static Configuration initConfig() {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(TemplateRenderer.class, "");

        DefaultObjectWrapper objectWrapper = new DefaultObjectWrapper();
        objectWrapper.setExposeFields(true);

        configuration.setObjectWrapper(objectWrapper);
        configuration.setDefaultEncoding("UTF-8");

        return configuration;
    }

    public static String renderTemplate(String templateName) {
        return renderTemplate(templateName, null);
    }

    public static String renderTemplate(String templateName, Object dataModel) {
        try {
            StringWriter stringWriter = new StringWriter();

            configuration.getTemplate(templateName).process(dataModel, stringWriter);
            return stringWriter.toString();

        } catch (IOException e) {
            throw new RuntimeException("template not found: " + templateName, e);
        } catch (TemplateException e) {
            throw new RuntimeException("error parsing template: " + templateName, e);
        }
    }
}
