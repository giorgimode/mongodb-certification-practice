import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.bson.Document;
import spark.Spark;

import java.io.IOException;
import java.io.StringWriter;

import static spark.Spark.halt;

/**
 * Created by modeg on 6/4/2017.
 */
public class HelloWorldSparkFreeMarkerStyle {
    public static void main(String[] args) {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(HelloWorldFreeMarkerStyle.class, "/");

        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("course");
        MongoCollection<Document> collection = database.getCollection("hello");

        collection.drop();

        collection.insertOne(new Document("name", "MongoDB"));

        Spark.get("/", (request, response) -> {
            StringWriter stringWriter = new StringWriter();
            try {
                Template template = configuration.getTemplate("hello.ftl");

                Document document = collection.find().first();
                template.process(document, stringWriter);

            } catch (IOException | TemplateException e) {
                halt(500);
                e.printStackTrace();
            }

            return stringWriter;
        });

        Spark.get("/echo/:param1", (request, response) -> "Wow param " + request.params(":param1"));
    }
}
