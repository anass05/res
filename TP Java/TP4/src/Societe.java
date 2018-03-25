import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Anass on 2018-03-20.
 */
public class Societe {
    private Personne Directeur;
    private ArrayList<Personne> employees;
    private ArrayList<Poste> postes;

    public Societe(Personne directeur, ArrayList<Personne> employees, ArrayList<Poste> postes) {
        Directeur = directeur;
        this.employees = employees;
        this.postes = postes;
    }

    public double getMasseSalariele() {
        double mass = 0;
        for (Poste p : postes)
            mass += p.getSalaireBrut();
        return mass;
    }

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
