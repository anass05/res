import java.util.ArrayList;

/**
 * Cette classe represente la Grille de bataille
 * Chaque joueur à sa propre Grille
 *
 * @author Anass
 */
public class Grille {
    /**
     * La grille est composé de 10*10 cases
     */
    private Case[][] grille;
    public static int NB_LIGNES = 10;
    public static int NB_COLONNES = 10;

    /**
     * Le constructeur par defaut de la classe Grille
     */
    public Grille() {
        grille = new Case[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grille[i][j] = new Case();
            }
        }
    }

    /**
     * La methode afficheTirs qui affiche dans le terminal le caractère o pour toutes cases
     * ayant fait l'objet d'un tir et un espace pour les autres cases
     */
    public void afficheTirs() {
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[i].length; j++) {
                String s = grille[i][j].isEtat() ? "o " : " ";
                System.out.print(s);
            }
            System.out.println();
        }
    }

    /**
     * la methode tire sert à mettre a joure une case (x,y) à vrai
     *
     * @param x le cordonné x de la case
     * @param y le cordonné y de la case
     */
    public void tir(int x, int y) {
        grille[x][y].setEtat(true);
    }

    public Case[][] getGrille() {
        return grille;
    }

    public boolean place(Bateau b, int x, int y) {
        boolean isHorizontal;
        isHorizontal = b.horizontal;
        int taille = b.getTaille();
        //clearing old cases from the old position
        for (Case c : b.getCases())
            c.setBateau(null);
        //creating new cases for the new postition
        b.setCases(new ArrayList<>());
        if (isHorizontal && ((taille + x) < grille[y].length)) {
            for (int i = 0; i < taille; i++) {
                b.ajouteCase(grille[y][x+i]);
            }
            return true;
        }
        if (!isHorizontal && ((taille + y) < grille.length)) {
            for (int i = 0; i < taille; i++) {
                b.ajouteCase(grille[y+i][x]);
                System.out.println("["+(y+i)+"]["+x+"]");
            }
            return true;
        }
        return false;
    }

    public void affiche() {
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[i].length; j++) {
                String s = grille[i][j].isEtat() ? "o " : " ";
                if (grille[i][j].getBateau() != null)
                    s = grille[i][j].getBateau().getSymbole() + " ";
                System.out.print(s);
            }
            System.out.println();
        }

    }

}
