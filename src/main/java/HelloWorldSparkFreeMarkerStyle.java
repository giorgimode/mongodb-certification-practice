import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

/**
 * Created by modeg on 6/4/2017.
 */
public class HelloWorldSparkFreeMarkerStyle {
    public static void main(String[] args) {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(HelloWorldFreeMarkerStyle.class, "/");

        Spark.get("/", (request, response) -> {
            StringWriter stringWriter = new StringWriter();
            try {
                Template template = configuration.getTemplate("hello.ftl");
                Map<String, Object> helloMap = new HashMap<>();
                helloMap.put("name", "Giorgi The Beast");
                template.process(helloMap, stringWriter);
            } catch (IOException | TemplateException e) {
                halt(500);
                e.printStackTrace();
            }

            return stringWriter;
        });

        Spark.get("/echo/:param1", (request, response) -> "Wow param " + request.params(":param1"));
    }
}
