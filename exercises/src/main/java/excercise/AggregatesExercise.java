package excercise;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.gte;
import static java.util.Arrays.asList;
import static org.bson.Document.parse;


/**
 * db.zipcodes.aggregate( [
 * { $group: { _id: "$state", totalPop: { $sum: "$pop" } } },
 * { $match: { totalPop: { $gte: 10*1000*1000 } } }
 * ] )
 */
public class AggregatesExercise {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("zips");

//        runnFullQuery(collection);
//        groupWithDocuments(collection);
//        groupAndMatchWithDocuments(collection);
//        groupAndMatchWithBuilders(collection);
        groupAndMatchWithShell(collection);

    }

    private static void runnFullQuery(MongoCollection<Document> collection) {
        List<Document> result = collection.find().into(new ArrayList<>());
        print(result);
    }

    private static void print(List<Document> result) {
        for (Document doc : result) {
            System.out.println(doc.toJson());
        }
    }


    /**
     * grouping only
     */
    private static void groupWithDocuments(MongoCollection<Document> collection) {
        List<Document> pipeline = asList(new Document("$group",
                new Document("_id", "$state")
                        .append("totalPop", new Document("$sum", "$pop"))));

        List<Document> result = collection.aggregate(pipeline).into(new ArrayList<>());
        print(result);
    }

    /**
     * grouping and matching
     */
    private static void groupAndMatchWithDocuments(MongoCollection<Document> collection) {
        List<Document> pipeline = asList(new Document("$group",
                        new Document("_id", "$state")
                                .append("totalPop", new Document("$sum", "$pop"))),
                new Document("$match", new Document("totalPop", new Document("$gte", 10000000))));

        List<Document> result = collection.aggregate(pipeline).into(new ArrayList<>());
        print(result);
    }

    private static void groupAndMatchWithBuilders(MongoCollection<Document> collection) {
        List<Bson> pipeline = asList(Aggregates.group("$state", Accumulators.sum("totalPop", "$pop")),
                Aggregates.match(gte("totalPop", 10000000)));

        List<Document> result = collection.aggregate(pipeline).into(new ArrayList<>());
        print(result);
    }

    private static void groupAndMatchWithShell(MongoCollection<Document> collection) {
        List<Document> pipeline = asList(
                parse("{ $group: { _id: \"$state\", totalPop: { $sum: \"$pop\" } } }"),
                parse("{ $match: { totalPop: { $gte: 10000000 } } }"));

        List<Document> result = collection.aggregate(pipeline).into(new ArrayList<>());
        print(result);
    }
}
