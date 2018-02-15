/**
 * Cette classe represente la Grille de bataille
 * Chaque joueur à sa propre Grille
 * @author Anass
 */
public class Grille {
    /**
     * La grille est composé de 10*10 cases
     */
    private Case[][] grille;
    /**
     * Le constructeur par defaut de la classe Grille
     * */
    public Grille(){
        grille = new Case[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grille[i][j] = new Case();
            }
        }
    }
    /**
     * La methode afficheTirs qui affiche dans le terminal le caractère o pour toutes cases
     ayant fait l'objet d'un tir et un espace pour les autres cases
     */
    public void afficheTirs(){
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
     * @param x le cordonné x de la case
     * @param y le cordonné y de la case
     * */
    public void tir(int x,int y){
        grille[x][y].setEtat(true);
    }

}
