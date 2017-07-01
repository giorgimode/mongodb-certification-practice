package excercise;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

//import static com.mongodb.client.model.Filters.elemMatch;


public class Hw3_1 {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("school");
        MongoCollection<Document> collection = database.getCollection("students");


        List<Document> documents = collection.find().into(new ArrayList<>());

        for (Document cur : documents) {
            ArrayList<Document> scores = (ArrayList<Document>) cur.get("scores");
            Double adouble = scores.stream()
                                   .filter(document -> document.get("type").equals("homework"))
                                   .map(document -> (double) document.get("score"))
                                   .min(Double::compare)
                                   .orElseGet(null);


            Bson filter = eq("_id", cur.get("_id"));

            Bson delete = Updates.pull("scores", new Document("score", adouble));
            UpdateResult updateResult = collection.updateOne(filter, delete);

            System.out.println(updateResult.getModifiedCount());
//            printJson(cur);
        }

    }
}
