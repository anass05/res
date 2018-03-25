import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Anass on 2018-03-20.
 */
public class Personne {
    private String nom;
    private String prenom;
    private Calendar date_Naissance;
    private Genre genre;
    private Personne pere;
    private Personne mere;
    private Personne femme;
    private Personne mari;
    private ArrayList<Personne> enfants;
    private ArrayList<Societe> directeurDeSocietes;
    private ArrayList<Societe> employeDansSocietes;
    private ArrayList<Poste> postes;
    private Compte compte;

    public Personne(String nom, String prenom, Calendar date_Naissance, Genre genre, Personne pere, Personne mere) {
        this.nom = nom;
        this.prenom = prenom;
        this.date_Naissance = date_Naissance;
        this.genre = genre;
        directeurDeSocietes = new ArrayList<>();
        enfants = new ArrayList<>();
    }

    public void setDirecteurDeSocietes(ArrayList<Societe> directeurDeSocietes) {
        this.directeurDeSocietes = directeurDeSocietes;
    }

    public void setEmployeDansSocietes(ArrayList<Societe> employeDansSocietes) {
        this.employeDansSocietes = employeDansSocietes;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
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

    public double revenue() {
        double revenue = 0;
        for (Poste p : postes)
            revenue += p.getSalaireBrut();
        return revenue;
    }

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
