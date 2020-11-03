import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class DBConnector {

    private static volatile DBConnector db;

    private DBConnector(){};

    public static DBConnector getDBConnector(){
        if (db == null)
            synchronized (DBConnector.class) {
                if (db == null)
                    db = new DBConnector();
            }
        return db;
    }

    public MongoDatabase getDatabase(String database, String pwd) {
        String uri = "mongodb+srv://admin_role:password@lab7db.0cjdf.mongodb.net/database?retryWrites=true&w=majority";
        uri = uri.replace("password",pwd);
        uri = uri.replace("database", database);
        MongoClient mongoClient = new MongoClient(new MongoClientURI(uri));
        return mongoClient.getDatabase(database);
    }

    public void writeToDB(MongoCollection<Document> collection, String key, String value){
        Document tempDoc = new Document();
        tempDoc.put(key,value);
        collection.insertOne(tempDoc);
    }

    public List<Document> readMongoDB(MongoCollection<Document> collection){
        List<Document> docs = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                docs.add(cursor.next());
            }
        }
        return docs;
    }
}
