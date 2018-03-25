import java.util.HashSet;

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

    /**
     * methode place qui sert a placer un bateau dans la grille
     *
     * @param b: le bateau à ajouter dans la grille
     * @param x: la position x du bateau
     * @param y: la position y du bateau
     */
    public void place(Bateau b, int x, int y) throws BateauHorsGrilleException, ToutesCasesAffecteesException, CaseOccupeeException {
        boolean isHorizontal;
        isHorizontal = b.horizontal;
        int taille = b.getTaille();
        removeBateau(b);
        b.setCases(new HashSet<>());
        if (isHorizontal)
            if ((taille + x) < grille[y].length) {
                for (int i = 0; i < taille; i++) {
                    if (grille[y][x + i].getBateau() != null)
                        throw new CaseOccupeeException();
                    else
                        b.ajouteCase(grille[y][x + i]);
                }
            } else
                throw new BateauHorsGrilleException();
        if (!isHorizontal)
            if ((taille + y) < grille.length) {
                for (int i = 0; i < taille; i++) {
                    if (grille[y + i][x].getBateau() != null)
                        throw new CaseOccupeeException();
                    else
                        b.ajouteCase(grille[y + i][x]);
                }
            } else
                throw new BateauHorsGrilleException();

    }

    /**
     * methode qui test l'intersection des bateau
     *
     * @param b: le bateau à ajouter dans la grille
     * @param x: la position x du bateau
     * @param y: la position y du bateau
     */
//    public boolean testIntersection(Bateau b, int x, int y) {
//
//        boolean isHorizontal;
//        isHorizontal = b.horizontal;
//        int taille = b.getTaille();
//
//        if (isHorizontal && ((taille + x) < grille[y].length)) {
//            for (int i = 0; i < taille; i++) {
//                if (grille[y][x + i].getBateau() != null)
//                    return true;
//            }
//            return false;
//        }
//
//        if (!isHorizontal && ((taille + y) < grille.length)) {
//            for (int i = 0; i < taille; i++) {
//                if (grille[y + i][x].getBateau() != null)
//                    return true;
//            }
//            return false;
//        }
//        return true;
//    }

    /**
     * methode qui sert a suprimer un bateau de la grille
     *
     * @param b: le bateau a supprimer
     */
    public void removeBateau(Bateau b) {
        for (Case c : b.getCases())
            c.setBateau(null);
        b.setCases(new HashSet<>());
    }

    /**
     * methode qui affiche la grille
     */
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
