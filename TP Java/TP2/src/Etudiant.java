/**
 * Classe Etudiant qui représente un étudiant
 *
 * @author Anass
 */
public class Etudiant extends Personne {
    /**
     * Un étudiant est définie par:
     * un nom: nom de type String (déja défini dans la super classe)
     * un email: email de type String (déja défini dans la super classe)
     * un numéro: nummerEtudiant de type int
     */
    protected int nummerEtudiant;

    /**
     * Le constructeur par défaut qui fait appelle au constructeur par default de la super classe
     */
    public Etudiant() {
        super();
    }

    /**
     * Le constructeur principal
     * @param nom:            nom de type String
     * @param nummerEtudiant: nummerEtudiant de type int
     * @param email:          email de type String
     */
    public Etudiant(String nom, int nummerEtudiant, String email) {
        super(nom, email);
        this.nummerEtudiant = nummerEtudiant;
    }

    /**
     * Les getter et le setters
     */
    public int getNummerEtudiant() {
        return nummerEtudiant;
    }

    public void setNummerEtudiant(int nummerEtudiant) {
        this.nummerEtudiant = nummerEtudiant;
    }

    /**
     * La Méthode toString qui retourne un String affichant les informations de l'étudiant
     */
    @Override
    public String toString() {
        return "nom='" + nom + '\'' +
                ", nummerEtudiant=" + nummerEtudiant +
                ", email='" + email;
    }

    /**
     * La méthode afficher affiche les informations de l'étudiant
     */
    public void afficher() {
        System.out.println(toString());
    }
}
