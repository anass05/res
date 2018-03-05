import java.util.ArrayList;

/**
 * Classe Filiere qui représente une filiere
 *
 * @author Anass
 */
public class Filiere implements Collection{
    /**
     * Une filiere est définie par
     * un tableau des étudiants
     * un numero de section
     * et un professeur
     */
    public ArrayList<Etudiant> etudiants;
    private Integer numeroSection;
    private Professeur chefSection;

    /***
     * Le constructeur par defaut de la classe filiere
     */
    public Filiere() {
        etudiants = new ArrayList<>();
    }

    /**
     * getters et le setters
     */
    public Integer getNumeroSection() {
        return numeroSection;
    }

    public void setNumeroSection(Integer numeroSection) {
        this.numeroSection = numeroSection;
    }

    public Professeur getChefSection() {
        return chefSection;
    }

    public void setChefSection(Professeur chefSection) {
        this.chefSection = chefSection;
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
