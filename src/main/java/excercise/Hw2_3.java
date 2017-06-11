package excercise;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.orderBy;
import static util.Helpers.printJson;

public class Hw2_3 {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("students");
        MongoCollection<Document> collection = database.getCollection("grades");


//        printJson(collection.find().first());
        Bson filter2 = and(eq("x", 0), gt("y", 10), lt("y", 90));
        Bson filter = eq("type", "homework");
        Bson sort = orderBy(ascending("student_id"), descending("score"));

/*        List<Document> all = collection.find()
//                                       .projection(projection)
                                       .filter(filter)
                                       .sort(sort)
                                       .into(new ArrayList<>());*/

        List<Document> all = collection.aggregate(
                Arrays.asList(
                        Aggregates.match(filter),
                        Aggregates.group("$student_id", Accumulators.min("score", "$score")),
                        Aggregates.sort(ascending("_id"))
                             )
                                                 )
                                       .into(new ArrayList<>());


        for (Document cur : all) {
            DeleteResult deleteResult = collection.deleteOne(and(eq("student_id", cur.get("_id")), eq("score", cur.get("score"))));
            printJson(cur);
            System.out.println(deleteResult.wasAcknowledged());
        }
    }
}
