package Persistence;

import Beans.*;
import com.mongodb.*;
import com.mongodb.util.JSON;
import org.bson.BSONObject;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Anass on 2018-05-08.
 */
public class ModulesDAO {
    private DB database;

    public ModulesDAO() {
        this.database = new MongoConnection().getDatabase();
    }

    public ArrayList<Module> getModules() {
        ArrayList<Module> modules = new ArrayList<>();
        DBCollection collection = database.getCollection("Modules");
        DBCursor cursor = collection.find();
        while (cursor.hasNext()) {
            JSONObject moduleJson = new JSONObject(JSON.serialize(cursor.next()));
            modules.add(makeModule(moduleJson));
            break;
        }
        return modules;
    }

    public ArrayList<Module> getModules(Personne p) {
        ArrayList<Module> modules = new ArrayList<>();
        BasicDBObject query = new BasicDBObject();
        query.put("id_personne", p.getIDPersonne());
        DBCursor cursor = database.getCollection("Modules").find(query);
        while (cursor.hasNext()) {
            JSONObject moduleJson = new JSONObject(JSON.serialize(cursor.next()));
            modules.add(makeModule(moduleJson));
        }
        return modules;
    }

    public Module makeModule(JSONObject moduleJson) {
        Module m = null;
        String id = moduleJson.getJSONObject("_id").getString("$oid");
        Planning planning = getPlanning(moduleJson);
        m = new Module(moduleJson.getString("libele"),
                getClasse(moduleJson.getString("id_classe")),
                getChargeHoraire(moduleJson.getString("id_charge_prevu")),
                getChargeHoraire(moduleJson.getString("id_charge_prevu")),
                planning,
                getPersonne(), getCahierCharge(moduleJson.getString("id_cahier_charge")),
                Semestre.valueOf("S" + moduleJson.getInt("semestre")));
//        for (Chapitre c : m.getCahierCharges().getChapitres()) {
//            c.getSeance().setPlan(planning);
//        }
        return m;
    }

    public Personne getPersonne() {
        return new Personne("ee", "ff", "ff", "ff");
    }

    public Classe getClasse(String idClasse) {
        Classe c;
        ObjectId id = new ObjectId(idClasse);
        BasicDBObject obj = new BasicDBObject();
        obj.append("_id", id);
        BasicDBObject query = new BasicDBObject();
        query.putAll((BSONObject) query);
        DBObject dbObj = database.getCollection("Classes").findOne(query);
        JSONObject classeJson = new JSONObject(JSON.serialize(dbObj));
        c = new Classe(classeJson.getString("nom"), classeJson.getInt("promo") + "",
                classeJson.getInt("promo"), classeJson.getString("filiere"), classeJson.getInt("effectif"), null);
        return c;
    }

    public ChargeHoraire getChargeHoraire(String idChargeHoraire) {
        ObjectId id = new ObjectId(idChargeHoraire);
        BasicDBObject obj = new BasicDBObject();
        obj.append("_id", id);
        BasicDBObject query = new BasicDBObject();
        query.putAll((BSONObject) query);
        DBObject dbObj = database.getCollection("ChargeHoraire").findOne(query);
        JSONObject classeJson = new JSONObject(JSON.serialize(dbObj));
        ChargeHoraire chargeHoraire = new ChargeHoraire(classeJson.getInt("nhC"),
                classeJson.getInt("nhTD"), classeJson.getInt("nhTP"), classeJson.getInt("nhP"), null, null);

        return chargeHoraire;
    }


    public CahierCharges getCahierCharge(String idCahierCharge) {
        CahierCharges ch = null;
        ObjectId id = new ObjectId(idCahierCharge);
        BasicDBObject obj = new BasicDBObject();
        obj.append("_id", id);
        BasicDBObject query = new BasicDBObject();
        query.putAll((BSONObject) query);
        DBObject dbObj = database.getCollection("CahierCharges").findOne(query);
        JSONObject chJson = new JSONObject(JSON.serialize(dbObj));
        ch = new CahierCharges(chJson.getInt("nbTP"), chJson.getString("dcsPrjt"), getChapitres(idCahierCharge), null);
        return ch;
    }

    public Planning getPlanning(JSONObject moduleJson) {
        Planning planning = null;
        try {
            ObjectId id = new ObjectId(moduleJson.getString("id_planning"));
            BasicDBObject obj = new BasicDBObject();
            obj.append("_id", id);
            BasicDBObject query = new BasicDBObject();
            query.putAll((BSONObject) query);
            DBObject dbObj = database.getCollection("Plannings").findOne(query);
            JSONObject chJson = new JSONObject(JSON.serialize(dbObj));
            planning = new Planning(moduleJson.getString("id_planning"), chJson.getBoolean("isValid"));
            getSeancesOfPlanning(planning);
        } catch (JSONException e) {

        }
        return planning;
    }

    public ArrayList<Chapitre> getChapitres(String idCahierCharge) {
        ArrayList<Chapitre> chapitres = new ArrayList<>();
        ObjectId id = new ObjectId(idCahierCharge);
        BasicDBObject obj = new BasicDBObject();
        obj.append("_id", id);
        BasicDBObject query = new BasicDBObject();
        query.putAll((BSONObject) query);
        DBCursor cursor = database.getCollection("Chapitres").find(query);
        while (cursor.hasNext()) {
            JSONObject chapitreJson = new JSONObject(JSON.serialize(cursor.next()));
            Chapitre chapitre = new Chapitre(chapitreJson.getJSONObject("_id").getString("$oid"), chapitreJson.getString("titre"), chapitreJson.getString("desc"), null, getSeance(chapitreJson));
            chapitres.add(chapitre);
        }
        return chapitres;
    }

    public Seance getSeance(JSONObject chapitre) {
        Seance c = null;
        try {
            ObjectId id = new ObjectId(chapitre.getString("id_seance"));
            BasicDBObject obj = new BasicDBObject();
            obj.append("_id", id);
            BasicDBObject query = new BasicDBObject();
            query.putAll((BSONObject) query);
            DBObject dbObj = database.getCollection("Seance").findOne(query);
            JSONObject chJson = new JSONObject(JSON.serialize(dbObj));
            c = new Seance(chJson.getJSONObject("_id").getString("$oid"), chJson.getInt("semaine"), null);
        } catch (JSONException e) {

        }
        return c;
    }

    public void getSeancesOfPlanning(Planning planning) {
        try {
            ObjectId id = new ObjectId(planning.getId());
            BasicDBObject obj = new BasicDBObject();
            obj.append("_id", id);
            BasicDBObject query = new BasicDBObject();
            query.put("id_planning", planning.getId());
            DBCursor cursor = database.getCollection("Seance").find(query);
            while (cursor.hasNext()) {
                JSONObject seanceJson = new JSONObject(JSON.serialize(cursor.next()));
                Seance s = new Seance(seanceJson.getJSONObject("_id").getString("$oid"), seanceJson.getInt("semaine"), planning);
                getChapitresOfSeance(s);
                planning.addSeance(s);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getChapitresOfSeance(Seance s) {
        ObjectId id = new ObjectId(s.getId());
        BasicDBObject obj = new BasicDBObject();
        obj.append("_id", id);
        BasicDBObject query = new BasicDBObject();
        query.put("id_seance", s.getId());
        DBCursor cursor = database.getCollection("Chapitres").find(query);
        while (cursor.hasNext()) {
            JSONObject chapitreJson = new JSONObject(JSON.serialize(cursor.next()));
            Chapitre chapitre = new Chapitre(chapitreJson.getJSONObject("_id").getString("$oid"), chapitreJson.getString("titre"), chapitreJson.getString("desc"), null, s);
            s.addChapitre(chapitre);
        }
    }


}
