/**
 * Created by Anass on 2018-02-12.
 */
public class Main {
    public static void main(String[] args) {
        Etudiant e = new Etudiant("Sadik anass",33,"anassda99@gmail.com");
        e.afficher();

        Professeur p = new Professeur("Mr. Aziz",131520,"aziz@gmail.com","2 éme");
        System.out.println(p);

        Module maths = new Module("Maths",5,75,p);
        maths.afficher();
    }
}
