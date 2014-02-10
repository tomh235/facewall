package uk.co.o2.facewall.view;

import com.google.common.base.Charsets;
import freemarker.template.Template;
import org.glassfish.jersey.server.mvc.Viewable;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;

import static com.google.common.base.Charsets.UTF_8;
import static javax.ws.rs.core.MediaType.TEXT_HTML_TYPE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class FreemarkerTemplateProcessorTest {

    private static final String testTemplateName = "/test.ftl";
    private static final MediaType someMediaType = TEXT_HTML_TYPE;

    private final FreemarkerTemplateProcessor templateProcessor = new FreemarkerTemplateProcessor();

    @Test
    public void should_resolve_template() {
        Template template = templateProcessor.resolve(testTemplateName, someMediaType);

        assertThat(template, is(notNullValue()));
    }

    @Test
    public void should_render_with_model_and_utf8_encoded() throws Exception {
        Viewable viewable = new Viewable(testTemplateName, new HashMap<String, String>() {{
            put("greeting", "Hello,");
        }});
        Template template = templateProcessor.resolve(testTemplateName, TEXT_HTML_TYPE);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        templateProcessor.writeTo(template, viewable, someMediaType, outputStream);

        String result = outputStream.toString(UTF_8.name());
        assertThat(result , is("<h1>Hello, world!</h1>"));
    }
}
