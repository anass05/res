package Beans;

public class Chapitre {

    private String titre;
    private String desc;
    private CahierCharges cahier;
    private Seance seance;
    private String id;

    public Chapitre(String id, String titre, String desc, CahierCharges cahier, Seance seance) {
        this.id = id;
        this.titre = titre;
        this.desc = desc;
        this.cahier = cahier;
        this.seance = seance;
//		this.seance.addChapitre(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Seance getSeance() {
        return seance;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public CahierCharges getCahier() {
        return cahier;
    }

    public void setCahier(CahierCharges cahier) {
        this.cahier = cahier;
    }

    @Override
    public String toString() {
        return "Chapitre [titre=" + titre + ", desc=" + desc + ", cahier=" + cahier + "]";
    }

    @Override
    public boolean equals(Object obj) {
        Chapitre c = (Chapitre) obj;
        if (c.getTitre().equals(titre))
            return true;
        return false;
    }
}
