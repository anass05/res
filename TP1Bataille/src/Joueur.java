/**
 * Cette classe represente le joueur de la bataille
 *
 * @author Anass
 */
public class Joueur {
    /**
     * Le joueur est définie par son nom
     * et une grille
     */
    private String nom;
    private Grille grille;

    /**
     * Le constructeur par defaut de la classe nom
     *
     * @param nom le nom du joueur
     */
    public Joueur(String nom) {
        this.nom = nom;
        this.grille = new Grille();
    }

    /**
     * getter de l'attribut nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * getter de l'attribut grille
     */
    public Grille getGrille() {
        return grille;
    }
}
