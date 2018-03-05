/**
 * Classe Doctorant qui sert a representé un doctorant
 *
 * @author Anass
 */
public class Doctorant extends Etudiant implements Salarie {
    /**
     * Un professeur est définie par
     * un nom: nom de type String (déja défini dans la super classe)
     * un email: email de type String (déja défini dans la super classe)
     * une grade: grade de type String
     * un nummero de Somme: nummerSomme de type int
     */
    private String SujetThese;
    private Professeur encadrant;

    /**
     * Constructeur par defaut de la classe Doctorant
     */
    public Doctorant() {
        super();
    }

    /**
     * getters et setters
     */
    public String getSujetThese() {
        return SujetThese;
    }

    public void setSujetThese(String sujetThese) {
        SujetThese = sujetThese;
    }

    public Professeur getEncadrant() {
        return encadrant;
    }

    public void setEncadrant(Professeur encadrant) {
        this.encadrant = encadrant;
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
            return normal - normal * 34 / 100;
        }
        Float normal = Float.valueOf(400 * 32);
        Float suplementaire = Float.valueOf(600 * (NbrHeurs - 32));
        return normal - normal * 34 / 100 + suplementaire - suplementaire * 17 / 100;
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
