import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Cette classe executable gere les tours de jeu pour une bataille navale
 *
 * @author HAFIDI IMAD
 */
public class Jeu {

    /**
     * Le premier joueur
     */
    private Joueur joueur1;

    /**
     * Le second joueur
     */
    private Joueur joueur2;

    /**
     * Le constructeur d'un nouveau jeu
     *
     * @param args contient les noms de chacun des joueurs. Si un des noms ou les deux manque,
     *             un nom par defaut est attribue
     */
    public Jeu(String[] args) {
        if (args.length > 0) joueur1 = new Joueur(args[0]);
        else joueur1 = new Joueur("joueur1");
        joueur2 = new JoueurOrdi();
    }

    /**
     * Saisie par le clavier un nombre entre 0 et 9
     *
     * @return un nombre entre 0 et 9 entre par l'utilisateur
     */
    public static int saisiePosition() {
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
     * Saisie par le clavier l'orientation a donner au bateau
     *
     * @return un booleen qui a pour valeur <i>true</i> si l'orientation saisie est horizontale et <i>false</i> sinon
     */
    public static boolean saisieOrientation() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                String ch = reader.readLine();
                if (ch.equals("h")) return true;
                else if (ch.equals("v")) return false;
                else System.out.println("Erreur - entrez h ou v");
            } catch (Exception ex) {
                System.out.println("Erreur - entrez h ou v");
            }
        }
    }

    /**
     * Gere la phase de tir des deux joueurs
     */
    public void tirs() {
        while ((!joueur1.aPerdu()) && (!joueur2.aPerdu())) {
            joueur1.tir(joueur2.getGrille());
            joueur2.getGrille().afficheTirs();
            joueur2.tir(joueur1.getGrille());
            joueur1.getGrille().afficheTirs();
        }
    }

    /**
     * Gere la phase de placement des bateaux pour les deux joueurs
     */
    public void placement() {
        System.out.println("Bonjour " + joueur1.getNom());
        System.out.println("Placement des bateaux");
        System.out.println("----------------------");
        joueur1.placementBateaux();
        joueur1.getGrille().affiche();
        System.out.println("Bonjour " + joueur2.getNom());
        System.out.println("Placement des bateaux");
        System.out.println("----------------------");
        joueur2.placementBateaux();
        joueur2.getGrille().affiche();
    }

    /**
     * Demarre une nouvelle partie
     */

    public void demarre() {
        placement();
        tirs();
        if (joueur1.aPerdu()) {
            if (joueur2.aPerdu()) System.out.println("Match nul !");
            else System.out.println(joueur2.getNom() + " a gagne !");
        } else System.out.println(joueur1.getNom() + " a gagne !");
    }

    /**
     * Methode executable qui lance une nouvelle partie
     *
     * @param args Les parametres de la ligne de commande. S'il y en a, ils seront utilises
     *             comme nom pour les joueurs
     */
    public static void main(String[] args) {
        Jeu jeu = new Jeu(args);
        jeu.demarre();
    }
}
