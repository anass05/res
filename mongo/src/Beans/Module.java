package Beans;

import java.util.ArrayList;

public class Module {

    private String libele;
    private Classe classe;
    private ChargeHoraire prevue;
    private ChargeHoraire reelle;
    private Planning plan;
    private Personne personne;
    private CahierCharges cahierCharges;
    private Semestre semestre;
    private String id;

    public Module(String id, String libele, Classe classe, ChargeHoraire prevue, ChargeHoraire reelle, Planning plan,
                  Personne personne, CahierCharges cahierCharges, Semestre semestre) {
        super();
        this.id = id;
        this.libele = libele;
        this.classe = classe;
        ArrayList<Module> modules = classe.getModules();
        modules.add(this);
        this.classe.setModules(modules);
        this.prevue = prevue;
        this.prevue.setModule(this);
        this.reelle = reelle;
        this.reelle.setModule(this);
        if (plan != null) {
            this.plan = plan;
            this.plan.setModule(this);
        }
        this.personne = personne;
        ArrayList<Module> modules1 = personne.getModules();
        modules1.add(this);
        this.personne.setModules(modules1);
        this.cahierCharges = cahierCharges;
        this.cahierCharges.setModule(this);
        this.semestre = semestre;
    }

    public String getLibele() {
        return libele;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public ChargeHoraire getPrevue() {
        return prevue;
    }

    public void setPrevue(ChargeHoraire prevue) {
        this.prevue = prevue;
    }

    public ChargeHoraire getReelle() {
        return reelle;
    }

    public void setReelle(ChargeHoraire reelle) {
        this.reelle = reelle;
    }

    public Planning getPlan() {
        return plan;
    }

    public void setPlan(Planning plan) {
        this.plan = plan;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public CahierCharges getCahierCharges() {
        return cahierCharges;
    }

    public void setCahierCharges(CahierCharges cahierCharges) {
        this.cahierCharges = cahierCharges;
    }

    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    public String getPrevu() {
        return "xx";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Module{" +
                "libele='" + libele + '\'' +
                ", classe=" + classe +
                ", prevue=" + prevue +
                ", reelle=" + reelle +
                ", plan=" + plan +
                ", personne=" + personne +
                ", cahierCharges=" + cahierCharges +
                ", semestre=" + semestre +
                '}';
    }
}
