package Beans;

public class Chapitre {

    private String titre;
    private CahierCharges cahier;
    private Seance seance;
    private String id;

    public Chapitre(String titre) {
        this.titre = titre;
        this.id = "-";
    }

    public Chapitre(String id, String titre, CahierCharges cahier, Seance seance) {
        this.id = id;
        this.titre = titre;
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
        seance.addChapitre(this);
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public CahierCharges getCahier() {
        return cahier;
    }

    public void setCahier(CahierCharges cahier) {
        this.cahier = cahier;
    }

    @Override
    public String toString() {
        return "Chapitre [titre=" + titre + ", cahier=" + cahier + "]";
    }

    @Override
    public boolean equals(Object obj) {
        Chapitre c = (Chapitre) obj;
        if (c.getTitre().equals(titre))
            return true;
        return false;
    }
}
