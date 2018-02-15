/**
 * Created by Anass on 2018-02-12.
 */
public class Etudiant {
    private String nom;
    private int nummerEtudiant;
    private String email;

    public Etudiant() {
    }

    public Etudiant(String nom, int nummerEtudiant, String email) {
        this.nom = nom;
        this.nummerEtudiant = nummerEtudiant;
        this.email = email;
    }

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

    @Override
    public String toString() {
        return "nom='" + nom + '\'' +
                ", nummerEtudiant=" + nummerEtudiant +
                ", email='" + email;
    }

    public void afficher(){
        System.out.println(toString());
    }
}
