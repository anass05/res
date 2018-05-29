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
public class ModulesDAO2 {
    private DB database;

    public ModulesDAO2() {
        this.database = new MongoConnection().getDatabase();
    }

    public ArrayList<Module> getModules() {
        ArrayList<Module> modules = new ArrayList<>();
        DBCollection collection = database.getCollection("Modules");
        DBCursor cursor = collection.find();
        while (cursor.hasNext()) {
            JSONObject moduleJson = new JSONObject(JSON.serialize(cursor.next()));
            modules.add(makeModule(moduleJson));
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
        Module m;
        String id = moduleJson.getJSONObject("_id").getString("$oid");
        Planning planning = getPlanning(moduleJson);
        CahierCharges cahierDesCharges = getCahierCharge(moduleJson.getString("id_cahier_charge"), planning, planning != null);
        m = new Module(id,
                moduleJson.getString("libele"),
                getClasse(moduleJson.getString("id_classe")),
                getChargeHoraire(moduleJson.getString("id_charge_prevu")),
                getChargeHoraire(moduleJson.getString("id_charge_prevu")),
                planning,
                getPersonne(),
                cahierDesCharges,
                Semestre.valueOf("S" + moduleJson.getInt("semestre")));
        return m;
    }


    public CahierCharges getCahierCharge(String idCahierCharge, Planning planning, boolean isSuivi) {
        CahierCharges ch;
        BasicDBObject query = new BasicDBObject();
        query.putAll((BSONObject) query);
        DBObject dbObj = database.getCollection("CahierCharges").findOne(query);
        JSONObject chJson = new JSONObject(JSON.serialize(dbObj));
        ch = new CahierCharges(chJson.getInt("nbTP"), chJson.getString("dcsPrjt"), getChapitres(idCahierCharge, planning, isSuivi), null);
        return ch;
    }

    public ArrayList<Chapitre> getChapitres(String idCahierCharge, Planning planning, boolean isSuivi) {
        ArrayList<Chapitre> chapitres = new ArrayList<>();
        ObjectId id = new ObjectId(idCahierCharge);
        BasicDBObject obj = new BasicDBObject();
        obj.append("_id", id);
        BasicDBObject query = new BasicDBObject();
        query.putAll((BSONObject) query);
        DBCursor cursor = database.getCollection("Chapitres").find(query);
        while (cursor.hasNext()) {
            JSONObject chapitreJson = new JSONObject(JSON.serialize(cursor.next()));
            Chapitre chapitre = new Chapitre(chapitreJson.getJSONObject("_id").getString("$oid"), chapitreJson.getString("titre"), null, getSeance(chapitreJson, planning, isSuivi));
            chapitres.add(chapitre);
            if (chapitre.getSeance() != null)
                chapitre.getSeance().addChapitre(chapitre);
        }
        return chapitres;
    }

    public Seance getSeance(JSONObject chapitreJson, Planning planning, boolean isSuivi) {
        String ss = isSuivi ? "id_seance_suivi" : "id_seance";

        if (planning == null)
            return null;
        try {
            String id = chapitreJson.getString(ss);
            for (Seance seance : planning.getSeances()) {
                if (seance.getId().equals(id))
                    return seance;
            }
            return null;
        } catch (JSONException e) {
        }
        return null;
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
                planning.addSeance(s);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public Personne getPersonne() {
        return new Personne("ee", "ff", "ff", "ff");
    }

    public ChargeHoraire getChargeHoraire(String idChargeHoraire) {
        ObjectId id = new ObjectId(idChargeHoraire);
        BasicDBObject obj = new BasicDBObject();
        obj.append("_id", id);
        BasicDBObject query = new BasicDBObject();
        query.putAll((BSONObject) query);
        DBObject dbObj = database.getCollection("ChargeHoraire").findOne(query);
        JSONObject classeJson = new JSONObject(JSON.serialize(dbObj));
        ChargeHoraire chargeHoraire = new ChargeHoraire(idChargeHoraire, classeJson.getInt("nhC"),
                classeJson.getInt("nhTD"), classeJson.getInt("nhTP"), classeJson.getInt("nhP"), null, null);

        return chargeHoraire;
    }

    public void definePlanning(Module module) {
        String planning_id = insertPlanning(module);
        for (Seance s : module.getPlan().getSeances()) {
            String seance_id = createSeance(s.getSemaine(), planning_id);
            System.out.println("creating seance");
            for (Chapitre c : s.getChapitres()) {
                if (c.getId().equals("-")) {
                    //insert TD/TP/Project
                    insertTDIntoSeance(seance_id, c.getTitre(), false);
                } else {
                    //update chapitre's seance
                    insertSeanceIntoChapitre(seance_id, c.getId(), false);
                }
            }
        }
    }

    public String insertPlanning(Module module) {
        DBCollection collection = database.getCollection("Plannings");
        BasicDBObject document = new BasicDBObject("isValid", false);
        collection.insert(document);
        setModulePlanning(module, document.get("_id").toString());
        return document.get("_id").toString();
    }

    public void setModulePlanning(Module module, String planningID) {
        DBCollection collection = database.getCollection("Modules");

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.append("$set", new BasicDBObject().append("id_planning", planningID));

        BasicDBObject searchQuery = new BasicDBObject().append("_id", new ObjectId(module.getId()));
        collection.update(searchQuery, newDocument);
    }

    public String createSeance(int sem, String planning_id) {
        DBCollection collection = database.getCollection("Seance");
        BasicDBObject document = new BasicDBObject();
        document.put("id_planning", planning_id);
        document.put("semaine", sem);
        collection.insert(document);
        return document.get("_id").toString();
    }

    public void insertTDIntoSeance(String seance_id, String title, boolean isSuivi) {
        String ss = isSuivi ? "id_seance_suivi" : "id_seance";

        DBCollection collection = database.getCollection("Chapitres");
        BasicDBObject document = new BasicDBObject();
        document.put("titre", title);
        document.put(ss, seance_id);
        collection.insert(document);
    }

    public void insertSeanceIntoChapitre(String seance_id, String chapitreId, boolean isSuivi) {
        String ss = isSuivi ? "id_seance_suivi" : "id_seance";

        DBCollection collection = database.getCollection("Chapitres");

        BasicDBObject searchQuery = new BasicDBObject().append("_id", new ObjectId(chapitreId));

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.append("$set", new BasicDBObject().append(ss, seance_id));

        collection.update(searchQuery, newDocument);
    }

    public void suiviPlanning(Module module) {
        if (module.getPlan() != null)
            for (Seance s : module.getPlan().getSeances()) {
                String seance_id = s.getId();
                for (Chapitre c : s.getChapitres()) {
                    if (c.getId().equals("-")) {
                        //insert TD/TP/Project
                        insertTDIntoSeance(seance_id, c.getTitre(), true);
                    } else {
                        //update chapitre's seance
                        insertSeanceIntoChapitre(seance_id, c.getId(), true);
                    }
                }
            }
    }

    public void saveModuleChargeHoraire(Module module) {

        DBCollection collection = database.getCollection("ChargeHoraire");

        BasicDBObject searchQuery = new BasicDBObject().append("_id", new ObjectId(module.getPrevue().getId()));

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.append("nhC", module.getPrevue().getNhC());
        newDocument.append("nhTD", module.getPrevue().getNhTD());
        newDocument.append("nhTP", module.getPrevue().getNhTP());
        newDocument.append("nhP", module.getPrevue().getNhP());

        BasicDBObject setQuery = new BasicDBObject();
        setQuery.append("$set", newDocument);


        System.out.println(module.getPrevue().getNhP());

        collection.update(searchQuery, setQuery);
    }
}