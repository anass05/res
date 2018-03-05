import java.util.ArrayList;

/**
 * Classe Departement qui représente une département
 *
 * @author Anass
 */
public class Departement implements Collection {
    /**
     * Un département est définie par:
     * un nom de type String
     * un professeur de type Professeur
     * un tablea des membre de type pesonnes
     */
    private String nom;
    private Professeur chefDepartement;
    public ArrayList<Personne> membres;

    /**
     * Le constructeur par defaut de la classe département
     */
    public Departement() {
        membres = new ArrayList<>();
    }

    /**
     * La méthode masseSalariale qui calcule la somme
     * de tous les salaires des membres du département
     */
    public Float masseSalariale() {
        float f = 0;
        for (Personne p : membres)
            if (p instanceof Professeur)
                f += ((Professeur) p).calculeSalaire();
            else
                f += ((Doctorant) p).calculeSalaire();
        return f;
    }

    /**
     * Le getter et le setters
     */
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Professeur getChefDepartement() {
        return chefDepartement;
    }

    public void setChefDepartement(Professeur chefDepartement) {
        this.chefDepartement = chefDepartement;
    }

    @Override
    public Boolean addObject(Object o, int i) {
        return null;
    }

    @Override
    public Boolean removeObject(Object o, int i) {
        return null;
    }

    @Override
    public Object getObject(int i) {
        return null;
    }
}
