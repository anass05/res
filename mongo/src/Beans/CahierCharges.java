package Beans;

import java.util.ArrayList;

public class CahierCharges {

    private int nbTP;
    private String dscPrjt;
    private ArrayList<Chapitre> chapitres;
    private Module module;

    public CahierCharges(int nbTP, String dscPrjt, ArrayList<Chapitre> chapitres, Module module) {
        super();
        this.nbTP = nbTP;
        this.dscPrjt = dscPrjt;
        this.chapitres = chapitres;
        this.module = module;
        initChaps();
    }

    public void initChaps() {
        if (chapitres != null)
            for (Chapitre c : chapitres)
                c.setCahier(this);
    }


    public int getNbTP() {
        return nbTP;
    }

    public void setNbTP(int nbTP) {
        this.nbTP = nbTP;
    }

    public String getDscPrjt() {
        return dscPrjt;
    }

    public void setDscPrjt(String dscPrjt) {
        this.dscPrjt = dscPrjt;
    }

    public ArrayList<Chapitre> getChapitres() {
        return chapitres;
    }

    public void setChapitres(ArrayList<Chapitre> chapitres) {
        this.chapitres = chapitres;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Chapitre getChapitre(String chapitreName) {
        for (Chapitre cc : chapitres)
            if (cc.getTitre().equals(chapitreName))
                return cc;
        return null;
    }


    @Override
    public String toString() {
        return "CahierCharges [nbTP=" + nbTP + ", dscPrjt=" + dscPrjt + ", chapitres=" + chapitres.size();
    }

}
