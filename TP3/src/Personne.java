/**
 * Classe Personne qui définie une personne
 *
 * @author Anass
 */
public abstract class Personne {
    /**
     * Une Personne est définie par:
     * un nom: nom de type String (déja défini dans la super classe)
     * un email: email de type String (déja défini dans la super classe)
     */
    protected String nom;
    protected String email;

    /**
     * Constructeur par defaut de la classe Personne
     */
    public Personne() {
        nom = "";
        email = "";
    }

    /**
     * Le constructeur principal
     *
     * @param nom:   nom de type String
     * @param email: email de type String
     */
    public Personne(String nom, String email) {
        this.nom = nom;
        this.email = email;
    }

    /**
     * les Getter et les setters
     */
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
