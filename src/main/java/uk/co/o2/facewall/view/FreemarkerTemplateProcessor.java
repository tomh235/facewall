package uk.co.o2.facewall.view;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.glassfish.jersey.server.ContainerException;
import org.glassfish.jersey.server.mvc.Viewable;
import org.glassfish.jersey.server.mvc.spi.TemplateProcessor;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import static com.google.common.base.Charsets.UTF_8;

public class FreemarkerTemplateProcessor implements TemplateProcessor<Template> {

    private final static Configuration configuration = initConfig();

    private static Configuration initConfig() {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(FreemarkerTemplateProcessor.class, "/views");

        DefaultObjectWrapper objectWrapper = new DefaultObjectWrapper();
        objectWrapper.setExposeFields(true);

        configuration.setObjectWrapper(objectWrapper);
        configuration.setDefaultEncoding(UTF_8.name());

        return configuration;
    }

    @Override public Template resolve(String name, MediaType mediaType) {
        try {
            return configuration.getTemplate(name);
        } catch (IOException e) {
            throw new RuntimeException("Could not load template: " + name, e);
        }
    }

    @Override
    public void writeTo(Template template, final Viewable viewable, MediaType mediaType, OutputStream out) throws IOException {
        try {
            template.process(viewable.getModel(), new OutputStreamWriter(out));
        } catch (TemplateException te) {
            throw new ContainerException(te);
        }
    }
}
