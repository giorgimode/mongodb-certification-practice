package excercise;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


public class Final7 {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("photoalbum");
        MongoCollection<Document> images = database.getCollection("images");
        MongoCollection<Document> albums = database.getCollection("albums");

        MongoCursor<Integer> files = albums.distinct("images", Integer.class).iterator();
        List<Integer> numbers = new ArrayList<>();
        while (files.hasNext()) {
            numbers.add(files.next());
        }

        BasicDBObject query2 = new BasicDBObject();
        query2.put("_id", new BasicDBObject("$nin", numbers));
        DeleteResult deleteResult = images.deleteMany(query2);
        System.out.println(deleteResult.wasAcknowledged() + ": " + deleteResult.getDeletedCount());

    }

}

