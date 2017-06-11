package excercise;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Spark;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

/**
 * Created by modeg on 6/4/2017.
 */
public class SparkFruitPicker {
    public static void main(String[] args) {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(SparkFruitPicker.class, "/");

        Spark.get("/", (request, response) -> {
            try {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("fruits", Arrays.asList("apple", "orange", "banana", "strawberry", "peach", "watermelon"));

                Template template = configuration.getTemplate("fruitpicker.ftl");
                StringWriter stringWriter = new StringWriter();
                template.process(paramMap, stringWriter);
                return stringWriter;

            } catch (IOException | TemplateException e) {
                halt(500);
                return null;
            }
        });

        Spark.post("/favorite_fruit", (request, response) -> {
            String fruit = request.queryParams("fruit");
            if (fruit == null) {
                return "Why don't you pick a fruit?";
            } else {
                return "Your favorite fruit is " + fruit;
            }
        });
    }
}
