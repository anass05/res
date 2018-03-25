/**
 * Created by Anass on 2018-03-20.
 */
public class Poste {
    private double salaireBrut;
    private CategoryPoste category;
    private Compte compte;
    private Personne personne;
    private Societe societe;

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
