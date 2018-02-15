/**
 * Classe Module qui représente un Module
 *
 * @author Anass
 */
public class Module {
    /**
     * Un module est définie par:
     * une libellé: libelle de type String
     * un coefficient: coefficient de type int
     * un nombre d'heures: nbrHeures de type int
     * un enseignent: enseignent de type Professeur
     */
    private String libelle;
    private int coefficient;
    private int nbrHeures;
    private Professeur enseignent;

    /**
     * Constructeur par defaut
     */
    public Module() {
    }

    /**
     * Constructeur principal
     *
     * @param libelle:     libelle de type String
     * @param coefficient: coefficient de type int
     * @param nbrHeures    d'heures: nbrHeures de type int
     * @param enseignent:  enseignent de type Professeur
     */
    public Module(String libelle, int coefficient, int nbrHeures, Professeur enseignent) {
        this.libelle = libelle;
        this.coefficient = coefficient;
        this.nbrHeures = nbrHeures;
        this.enseignent = enseignent;
    }

    /**
     * Les getters et les setters
     */

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

    /**
     * La méthode afficher qui affiche les informations d'un module
     */
    public void afficher() {
        System.out.println("libelle: " + libelle + ", coefficient: " + coefficient
                + ", nombre d'heures: " + nbrHeures + ", prof: " + enseignent);
    }
}
