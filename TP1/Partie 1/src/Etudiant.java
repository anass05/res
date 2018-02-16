/**
 * Classe Etudiant qui représente un étudiant
 * @author Anass
 */
public class Etudiant {
    /**
     *Un étudiant est définie par:
     * un nom: nom de type String
     * un numéro: nummerEtudiant de type int
     * un email: email de type String
     * */
    private String nom;
    private int nummerEtudiant;
    private String email;

    /**
     * Le constructeur par défaut*/
    public Etudiant() {
    }

    /**
     * Le constructeur principal
     * @param nom: nom de type String
     * @param nummerEtudiant: nummerEtudiant de type int
     * @param email: email de type String
     * */
    public Etudiant(String nom, int nummerEtudiant, String email) {
        this.nom = nom;
        this.nummerEtudiant = nummerEtudiant;
        this.email = email;
    }

    /**
     * Les getter et le setters
     * */
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNummerEtudiant() {
        return nummerEtudiant;
    }

    public void setNummerEtudiant(int nummerEtudiant) {
        this.nummerEtudiant = nummerEtudiant;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
     * */
    public void afficher(){
        System.out.println(toString());
    }
}
