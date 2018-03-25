/**
 * Classe poste
 *
 * @author Anass
 */
public class Poste {
    /**
     * le salaire brut du poste selon la category
     */
    private double salaireBrut;
    /**
     * la category du poste
     */
    private CategoryPoste category;
    /**
     * le compte associé poste
     */
    private Compte compte;
    /**
     * le personne qui occupe le post
     */
    private Personne personne;
    /**
     * le société ou le poste belongs
     */
    private Societe societe;

    /**
     * le constructeur de la classe poste
     *
     * @param category
     * @param compte
     * @param societe
     * @param personne
     */
    public Poste(CategoryPoste category, Compte compte, Personne personne, Societe societe) {
        this.category = category;
        this.compte = compte;
        this.personne = personne;
        this.societe = societe;
        if (category == CategoryPoste.Directeur)
            salaireBrut = 30000;
        if (category == CategoryPoste.Cadre)
            salaireBrut = 70000;
        if (category == CategoryPoste.Technicien)
            salaireBrut = 3000;
    }

    /**
     * methode payer qui paye un eployée selon sont nombre d'enfant et sa category
     */
    public void payer() {
        double ajouter = 0;
        if (category == CategoryPoste.Directeur)
            ajouter += 10000;
        if (category == CategoryPoste.Cadre)
            ajouter += 5000;
        if (category == CategoryPoste.Technicien)
            ajouter += 2000;

        ajouter += 200 * personne.getEnfants().size();
        compte.crediter(ajouter);
    }

    /**
     * le getters
     */
    public double getSalaireBrut() {
        return salaireBrut;
    }

    public Personne getPersonne() {
        return personne;
    }

    public CategoryPoste getCategory() {
        return category;
    }
}
