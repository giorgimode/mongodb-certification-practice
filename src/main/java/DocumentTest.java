import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.Date;

import static util.Helpers.printJson;

/**
 * Created by modeg on 6/8/2017.
 */
public class DocumentTest {
    public static void main(String[] args) {
        Document document = new Document()
                .append("str", "hello!")
                .append("intt", 42)
                .append("l", 42L)
                .append("double", 42.42)
                .append("boolean", false)
                .append("date", new Date())
                .append("objectId", new ObjectId())
                .append("null", null)
                .append("embeddedDoc", new Document("xx", 42))
                .append("list", Arrays.asList(1, 3, 4));

//        document.get("str");
//        document.getString("str");
//        document.getInteger("intt");

        printJson(document);
    }
}
