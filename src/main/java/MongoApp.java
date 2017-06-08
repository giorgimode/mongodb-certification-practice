import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;

/**
 * Created by modeg on 6/8/2017.
 */
public class MongoApp {
    public static void main(String[] args) {
//        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
//        MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017));
        MongoClientOptions options = MongoClientOptions.builder().build();
//        MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017), options);
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
        MongoCollection<BsonDocument> mongoCollection = mongoDatabase.getCollection("test", BsonDocument.class);
    }
}
