/**
 * Created by Anass on 2018-02-12.
 */
public class Module {
    private String libelle;
    private int coefficient;
    private int nbrHeures;
    private Professeur enseignent;

    public Module() {
    }

    public Module(String libelle, int coefficient, int nbrHeures, Professeur enseignent) {
        this.libelle = libelle;
        this.coefficient = coefficient;
        this.nbrHeures = nbrHeures;
        this.enseignent = enseignent;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public int getNbrHeures() {
        return nbrHeures;
    }

    public void setNbrHeures(int nbrHeures) {
        this.nbrHeures = nbrHeures;
    }

    public Professeur getEnseignent() {
        return enseignent;
    }

    public void setEnseignent(Professeur enseignent) {
        this.enseignent = enseignent;
    }

    public void afficher(){
        System.out.println("libelle: "+libelle+", coefficient: "+coefficient
        +", nombre d'heures: "+nbrHeures+", prof: "+enseignent);
    }
}
