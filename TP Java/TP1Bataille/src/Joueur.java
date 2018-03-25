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

    /**
     * methode qui sert a boucler su le nombre des 3 types de bateaux
     * pour les ajouter a la grille
     */
    public void placementBateaux() {
        for (int i = 0; i < NB_CROISEURS; i++)
            ajouterUnBateau('C');
        for (int i = 0; i < NB_DESTROYEURS; i++)
            ajouterUnBateau('D');
        for (int i = 0; i < NB_PORTEAVIONS; i++)
            ajouterUnBateau('P');
    }

    /**
     * methode qui ajoute un bateau a la grille
     *
     * @param symbole: le symbole du bateau à ajouter
     *                 le symbole sert a definire la taille
     */
    public void ajouterUnBateau(char symbole) {
        boolean intersection;
        do {
            //intersection = false;
            int x, y;
            boolean orientation;
            System.out.print("x : ");
            x = saisiePosition();
            System.out.print("y : ");
            y = saisiePosition();
            System.out.print("orientation (h/v): ");
            Scanner sc = new Scanner(System.in);
            orientation = sc.nextLine().equals("h");
            Bateau b = null;
            if (symbole == 'C')
                b = new Croiseur();
            if (symbole == 'D')
                b = new Destroyeur();
            if (symbole == 'P')
                b = new PorteAvions();
            b.horizontal = orientation;
            intersection = true;
            try {
                grille.place(b, x, y);
                intersection = false;
                bateaux.add(b);
            } catch (ToutesCasesAffecteesException e) {
                e.printStackTrace();
            } catch (BateauHorsGrilleException e) {
                e.printStackTrace();
            } catch (CaseOccupeeException e) {
                e.printStackTrace();
            }
        } while (intersection);
    }

    /**
     * methode retourne true si le joueur a perdu
     */

    public boolean aPerdu() {
        for (Bateau b : bateaux)
            if (!b.estCoule())
                return false;
        return true;
    }

    /**
     * Methode qui retourne une postion
     */
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

    /**
     * methode qui sert a saisire un postion de tir
     */
    public int positionTir() throws TireSurCaseInexistanteException {
        Scanner sc = new Scanner(System.in);
        int pos = -1;
        while (pos == -1) {
            pos = sc.nextInt();
            if ((pos < 0) || (pos > 9)) {
                throw new TireSurCaseInexistanteException();
            }
        }
        return pos;
    }

    /**
     * methode tir
     *
     * @param grilleAdversaire: la grille de l'enemy
     */
    public void tir(Grille grilleAdversaire) {
        System.out.println("Indiquez les coordonnees du tir (x puis y) entre 0 et 9");
        int x;
        int y;
        try {
            x = positionTir();
            y = positionTir();
            grilleAdversaire.tir(x, y);
        } catch (TireSurCaseInexistanteException e) {
            System.out.println(e.getMessage());
        }

    }
}
