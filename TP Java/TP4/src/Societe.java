import java.util.ArrayList;
import java.util.Collections;

/**
 * La classe société
 *
 * @author Anass
 */

public class Societe {
    /**
     * Le directeur de la societe
     */
    private Personne Directeur;
    /**
     * la liste de employees
     */
    private ArrayList<Personne> employees;
    /**
     * le liste des postes
     */
    private ArrayList<Poste> postes;

    /**
     * le constructeur de la classe societe
     *
     * @param directeur
     * @param employees
     * @param postes
     */
    public Societe(Personne directeur, ArrayList<Personne> employees, ArrayList<Poste> postes) {
        Directeur = directeur;
        this.employees = employees;
        this.postes = postes;
    }

    /**
     * la methode qui calcule la somme des salaires de la sociétés
     */
    public double getMasseSalariele() {
        double mass = 0;
        for (Poste p : postes)
            mass += p.getSalaireBrut();
        return mass;
    }

    /**
     * la methode qui affiche les informations des salariés (Nom, prénom)
     * suivant leur catégorie et en ordre croissant de leur salaire. 2
     */
    public void afficheSalarie() {
        ArrayList<Poste> directeurs = new ArrayList<>();
        ArrayList<Poste> cadres = new ArrayList<>();
        ArrayList<Poste> techniciens = new ArrayList<>();
        for (Poste p : postes)
            switch (p.getCategory()) {
                case Technicien:
                    techniciens.add(p);
                    break;
                case Directeur:
                    directeurs.add(p);
                    break;
                case Cadre:
                    cadres.add(p);
                    break;
            }
        Collections.sort(techniciens, new SalaireCategoryComparator());
        Collections.sort(directeurs, new SalaireCategoryComparator());
        Collections.sort(cadres, new SalaireCategoryComparator());

        System.out.println("les techniciens:");
        for (Poste p : techniciens)
            System.out.println(p.getPersonne().getNom() + " " + p.getPersonne().getPrenom());
        System.out.println("les cades:");
        for (Poste p : cadres)
            System.out.println(p.getPersonne().getNom() + " " + p.getPersonne().getPrenom());
        System.out.println("les directeurs:");
        for (Poste p : directeurs)
            System.out.println(p.getPersonne().getNom() + " " + p.getPersonne().getPrenom());
    }

    /**
     * le getters
     */
    public Personne getDirecteur() {
        return Directeur;
    }

    public void setDirecteur(Personne directeur) {
        Directeur = directeur;
    }

    public ArrayList<Personne> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Personne> employees) {
        this.employees = employees;
    }
}
