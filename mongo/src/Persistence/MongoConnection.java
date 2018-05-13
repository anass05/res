package Persistence;

import com.mongodb.*;

/**
 * Created by Anass on 2018-05-08.
 */
public class MongoConnection {
    private MongoClient mongoClient;
    private DB database;

    public MongoConnection() {
        mongoClient = new MongoClient("localhost", 27017);
        database = mongoClient.getDB("local");
    }

    public DB getDatabase() {
        return database;
    }
}
