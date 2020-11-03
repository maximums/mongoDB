
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONObject;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE);

        DataEncryptor encryptor = new DataEncryptor();
        DBConnector connector = DBConnector.getDBConnector();
        MongoDatabase db = connector.getDatabase("lab7DB","GAcHhuD8mNS8HmjM");
        MongoCollection<Document> collection = db.getCollection("email");
        for (Document doc : connector.readMongoDB(collection))
            System.out.println(doc.toJson());
        for (Document doc : connector.readMongoDB(collection))
            System.out.println(encryptor.decrypt(fromJsonStringToJson(doc.toJson()).keySet().toArray()[0].toString()) +" : " +
                    encryptor.decrypt(fromJsonStringToJson(doc.toJson()).getString(fromJsonStringToJson(doc.toJson()).keySet().toArray()[0].toString())));
//        try {
//            connector.writeToDB(collection, encryptor.encrypt("1"), encryptor.encrypt("dodi@gmail.com"));
////            connector.writeToDB(collection, encryptor.encrypt("2"), encryptor.encrypt("cristian@gmail.com"));
////            connector.writeToDB(collection, encryptor.encrypt("3"), encryptor.encrypt("dumitru@gmail.com"));
////            connector.writeToDB(collection, encryptor.encrypt("4"), encryptor.encrypt("dodi@mail.ru"));
////            connector.writeToDB(collection, encryptor.encrypt("5"), encryptor.encrypt("dodi@mail.yahoo.com"));
//        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
//            e.printStackTrace();
//        }

    }

    public static JSONObject fromJsonStringToJson(String data) {
        return new JSONObject(data);
    }
}
