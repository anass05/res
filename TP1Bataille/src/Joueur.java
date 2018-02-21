import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Cette classe represente le joueur de la bataille
 *
 * @author Anass
 */
public class Joueur {
    /**
     * Le joueur est définie par son nom
     * et une grille
     */
    protected String nom;
    protected Grille grille;
    protected ArrayList<Bateau> bateaux;
    public static int NB_DESTROYEURS = 1;
    public static int NB_CROISEURS = 1;
    public static int NB_PORTEAVIONS = 1;

    /**
     * Le constructeur par defaut de la classe Joueur
     *
     * @param nom le nom du joueur
     */
    public Joueur(String nom) {
        this.nom = nom;
        this.grille = new Grille();
        bateaux = new ArrayList<>();
    }

    /**
     * getter de l'attribut nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * getter de l'attribut grille
     */
    public Grille getGrille() {
        return grille;
    }

    public void placementBateaux() {
        for (int i = 0; i < NB_CROISEURS; i++)
            ajouterUnBateau('C');
        for (int i = 0; i < NB_DESTROYEURS; i++)
            ajouterUnBateau('D');
        for (int i = 0; i < NB_PORTEAVIONS; i++)
            ajouterUnBateau('P');
    }

    public void ajouterUnBateau(char symbole) {
        int x, y;
        boolean orientation;
        Scanner sc = new Scanner(System.in);
        System.out.print("x : ");
        x = sc.nextInt();
        sc = new Scanner(System.in);
        System.out.print("y : ");
        y = sc.nextInt();
        System.out.print("orientation (h/v): ");
        sc = new Scanner(System.in);
        orientation = sc.nextLine().equals("h") ? true : false;
        Bateau b = null;
        if (symbole == 'C')
            b = new Croiseur();
        if (symbole == 'D')
            b = new Destroyeur();
        if (symbole == 'P')
            b = new PorteAvions();
        b.horizontal = orientation;
        grille.place(b, x, y);
    }

    public boolean aPerdu() {
        for (Bateau b : bateaux)
            if (!b.estCoule()) {
                return false;
            }
        return true;
    }

    public int saisiePosition() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int pos = -1;
        while (pos == -1) {
            try {
                pos = Integer.parseInt(reader.readLine());
                if ((pos < 0) || (pos > 9)) {
                    System.out.println("Erreur - entrez une position entre 0 et 9");
                    pos = -1;
                }
            } catch (Exception ex) {
                pos = -1;
                System.out.println("Erreur - entrez une position valide");
            }
        }
        return pos;
    }

    public void tir(Grille grilleAdversaire) {
        System.out.println("Indiquez les coordonnees du tir (x puis y) entre 0 et 9");
        int x = saisiePosition();
        int y = saisiePosition();
        grilleAdversaire.tir(x, y);
    }
}
