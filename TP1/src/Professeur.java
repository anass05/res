/**
 * Created by Anass on 2018-02-12.
 */
public class Professeur {
    private String nom;
    private int nummerSomme;
    private String email;
    private String grade;

    public Professeur() {
    }

    public Professeur(String nom, int nummerSomme, String email, String grade) {
        this.nom = nom;
        this.nummerSomme = nummerSomme;
        this.email = email;
        this.grade = grade;
    }

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

    @Override
    public String toString() {
        return  "nom='" + nom + '\'' +
                ", nummerSomme=" + nummerSomme +
                ", email='" + email + '\'' +
                ", grade=" + grade ;
    }

    public void afficher(){
        System.out.println(toString());
    }
}
