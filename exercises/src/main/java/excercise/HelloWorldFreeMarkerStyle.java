package excercise;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by modeg on 6/4/2017.
 */
public class HelloWorldFreeMarkerStyle {
    public static void main(String[] args) throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(HelloWorldFreeMarkerStyle.class, "/");
        StringWriter stringWriter = new StringWriter();

        Template template = configuration.getTemplate("hello.ftl");
        Map<String, Object> helloMap = new HashMap<>();
        helloMap.put("name", "Giorgi The Beast");
        template.process(helloMap, stringWriter);

        System.out.println(stringWriter);
    }
}
