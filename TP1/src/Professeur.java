/**
 * Classe Professeur qui représente un professeur
 *
 * @author Anass
 */
public class Professeur {
    /**
     * Un professeur est définie par
     * un nom: nom de type String
     * un email: email de type String
     * une grade: grade de type String
     * un nummero de Somme: nummerSomme de type int
     */
    private String nom;
    private int nummerSomme;
    private String email;
    private String grade;

    /**
     * Constructeur par défaut
     */
    public Professeur() {
    }

    /**
     * Constructeur principal
     *
     * @param nom:         nom de type String
     * @param email:       email de type String
     * @param grade:       grade de type String
     * @param nummerSomme: nummero de Somme de type int
     */
    public Professeur(String nom, int nummerSomme, String email, String grade) {
        this.nom = nom;
        this.nummerSomme = nummerSomme;
        this.email = email;
        this.grade = grade;
    }

    /**
     * getters and setters
     */

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNummerSomme() {
        return nummerSomme;
    }

    public void setNummerSomme(int nummerSomme) {
        this.nummerSomme = nummerSomme;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * La méthode toString qui retourne un String affichant l'objet
     */
    @Override
    public String toString() {
        return "nom='" + nom + '\'' +
                ", nummerSomme=" + nummerSomme +
                ", email='" + email + '\'' +
                ", grade=" + grade;
    }

    /**
     * La méthode afficher affiche les informations du professeur
     */
    public void afficher() {
        System.out.println(toString());
    }
}
