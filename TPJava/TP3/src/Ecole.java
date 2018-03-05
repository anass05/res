/**
 * @author Anass
 */
public class Ecole {
    public static void main(String[] args) {
        Etudiant e = new Etudiant("Sadik anass", 33, "anassda99@gmail.com");
        e.afficher();

        Professeur p = new Professeur("Mr. Aziz", 131520, "aziz@gmail.com", "2 éme");
        System.out.println(p.calculeSalaire(40) + " DH");
        System.out.println(p.calculeVacances(29) + " jours de vacances");
        p.afficher();

        Doctorant d = new Doctorant();
        d.setNom("Mohammed YH");
        d.setEmail("mohammed@gmail.com");
        d.afficher();
        System.out.println(d.calculeSalaire(40) + " DH");
        System.out.println(d.calculeVacances(29) + " jours de vacances");
    }
}
