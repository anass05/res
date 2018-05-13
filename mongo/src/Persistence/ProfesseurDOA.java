package Persistence;

import Beans.Personne;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anass on 2018-05-13.
 */
public class ProfesseurDOA {
    private DB database;

    public ProfesseurDOA() {
        this.database = new MongoConnection().getDatabase();
    }

    public Personne login(String login, String password) {
        Personne p = null;
        BasicDBObject query = new BasicDBObject();
        HashMap<String, String> map = new HashMap<>();
        query.append("password", password);
        query.append("login", login);
        DBObject dbObj = database.getCollection("Personne").findOne(query);
        if(dbObj != null){
            JSONObject personneJSON = new JSONObject(JSON.serialize(dbObj));
            p = new Personne(personneJSON.getJSONObject("_id").getString("$oid"),null,personneJSON.getString("nom"),personneJSON.getString("prenom"));
        }
        return p;
    }
}
