package Beans;

import java.util.ArrayList;

public class Seance {

    private int semaine;
    private Planning plan;
    private ArrayList<Chapitre> chapitres;
    private String id;

    public Seance(String id, int semaine, Planning plan) {
        this.id = id;
        this.semaine = semaine;
        this.plan = plan;
        if (this.plan != null)
            this.plan.addSeance(this);
        this.chapitres = new ArrayList<>();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSemaine() {
        return semaine;
    }

    public void setSemaine(int semaine) {
        this.semaine = semaine;
    }

    public Planning getPlan() {
        return plan;
    }

    public void setPlan(Planning plan) {
        if (plan != null) {
            this.plan = plan;
            this.plan.addSeance(this);
        }
    }


    public ArrayList<Chapitre> getChapitres() {
        return chapitres;
    }

    public void setChapitres(ArrayList<Chapitre> chapitres) {
        this.chapitres = chapitres;
    }

    public void addChapitre(Chapitre c) {
        this.chapitres.add(c);
    }

    @Override
    public String toString() {
        return "Seance [semaine=" + semaine + ", plan=" + plan + ", chapitre=" + chapitres
                + "]";
    }


}
