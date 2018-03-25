import java.util.ArrayList;
import java.util.Calendar;

/**
 * La classe personne qui represente une personne
 *
 * @author Anass
 */
public class Personne {
    /**
     * Le nom de la personne
     */
    private String nom;
    /**
     * Le prenom de la personne
     */
    private String prenom;
    /**
     * la date de naissance de la personne
     */
    private Calendar date_Naissance;
    /**
     * le genre de la personne
     */
    private Genre genre;
    /**
     * le pere de la personne
     */
    private Personne pere;
    /**
     * le mere de la personne
     */
    private Personne mere;
    /**
     * le femme de la personne
     */
    private Personne femme;
    /**
     * le meri de la personne
     */
    private Personne mari;
    /**
     * le enfants de la personne
     */
    private ArrayList<Personne> enfants;
    /**
     * le société oû cette personne travaille comme directeur
     */
    private ArrayList<Societe> directeurDeSocietes;
    /**
     * les société oû cette personne travaille
     */
    private ArrayList<Societe> employeDansSocietes;
    /**
     * les postes qui occupe cette personne
     */
    private ArrayList<Poste> postes;

    /**
     * Le constructeur de la classe personne
     *
     * @param nom
     * @param prenom
     * @param date_Naissance
     * @param genre
     * @param pere
     * @param mere
     */
    public Personne(String nom, String prenom, Calendar date_Naissance, Genre genre, Personne pere, Personne mere) {
        this.nom = nom;
        this.prenom = prenom;
        this.date_Naissance = date_Naissance;
        this.genre = genre;
        directeurDeSocietes = new ArrayList<>();
        enfants = new ArrayList<>();
    }

    /**
     * setter des attributs
     */
    public void setDirecteurDeSocietes(ArrayList<Societe> directeurDeSocietes) {
        this.directeurDeSocietes = directeurDeSocietes;
    }

    public void setEmployeDansSocietes(ArrayList<Societe> employeDansSocietes) {
        this.employeDansSocietes = employeDansSocietes;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDate_Naissance(Calendar date_Naissance) {
        this.date_Naissance = date_Naissance;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setPere(Personne pere) {
        this.pere = pere;
    }

    public void setMere(Personne mere) {
        this.mere = mere;
    }

    public boolean setFemme(Personne femme) {
        if (genre == Genre.HOMME && getAge() > 18) {
            this.femme = femme;
            return true;
        }
        return false;
    }

    public boolean setMari(Personne mari) {
        if (genre == Genre.FEMME && getAge() > 18) {
            this.mari = mari;
            return true;
        }
        return false;
    }

    public void setPostes(ArrayList<Poste> postes) {
        this.postes = postes;
    }

    public void setEnfants(ArrayList<Personne> enfants) {
        if (mari != null || femme != null)
            this.enfants = enfants;
    }

    public int getAge() {
        return Calendar.getInstance().get(Calendar.YEAR) - date_Naissance.get(Calendar.YEAR);
    }

    public ArrayList<Personne> getEnfants() {
        return enfants;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    /**
     * methode qui calcule le revenue d'une personne
     */
    public double revenue() {
        double revenue = 0;
        for (Poste p : postes)
            revenue += p.getSalaireBrut();
        return revenue;
    }

    /**
     * methode qui affiche l'etat civile d'une personne
     */
    public void getEtatcivile() {
        StringBuffer s = new StringBuffer();
        s.append("nom :" + nom);
        s.append("\n prenom:" + prenom);
        s.append("\n date naissance:" + date_Naissance);
        String com = "aucun";
        if (mari != null)
            com = mari.nom;
        if (femme != null)
            com = femme.nom;
        s.append("\n compagnon:" + com);
        s.append("\n pere:" + pere);
        s.append("\n mere:" + mere);
        for (Personne p : enfants)
            s.append("\n enfant:" + p.nom);
        System.out.println(s);
    }
}
