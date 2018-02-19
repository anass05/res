/**
 * Classe Professeur qui représente un professeur
 *
 * @author Anass
 */
public class Professeur extends Personne implements Salarie {
    /**
     * Un professeur est définie par
     * un nom: nom de type String (déja défini dans la super classe)
     * un email: email de type String (déja défini dans la super classe)
     * une grade: grade de type String
     * un nummero de Somme: nummerSomme de type int
     */
    private int nummerSomme;
    private String grade;

    /**
     * Constructeur par défaut
     */
    public Professeur() {
        super();
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
        super(nom, email);
        this.nummerSomme = nummerSomme;
        this.grade = grade;
    }

    /**
     * getters and setters
     */
    public int getNummerSomme() {
        return nummerSomme;
    }

    public void setNummerSomme(int nummerSomme) {
        this.nummerSomme = nummerSomme;
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

    /**
     * Methode calculeSalaire calcule le salaire
     *
     * @param NbrHeurs: nombre d'heures
     */
    @Override
    public Float calculeSalaire(Integer NbrHeurs) {
        if (NbrHeurs < 32) {
            Float normal = Float.valueOf(400 * 32);
            return normal - normal * 34 / 100 + 5000;
        }
        Float normal = Float.valueOf(400 * 32);
        Float suplementaire = Float.valueOf(600 * (NbrHeurs - 32));
        return normal - normal * 34 / 100 + suplementaire - suplementaire * 17 / 100 + 5000;
    }

    /**
     * Methode calculeSalaire calcule le salaire
     *
     * @param Nbrjours: nombre de jours
     */
    @Override
    public Float calculeVacances(Integer Nbrjours) {
        return Float.valueOf(Nbrjours / 10);
    }
}
