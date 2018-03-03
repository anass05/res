import java.util.Random;

/**
 * Classe JoueurOrdi qui hérite de la classe Joueur
 *
 * @author Anass
 */
public class JoueurOrdi extends Joueur {

    /**
     * Le constructeur par defaut de la classe JoueurOrdi
     */
    public JoueurOrdi() {
        super("Ordinateur");
    }


    /**
     * a voire {@link Joueur#placementBateaux()}.
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
     * a voire {@link Joueur#ajouterUnBateau(char)} ()}.
     */
    public void ajouterUnBateau(char symbole) {
        boolean intersection;
        do {
            int x, y;
            boolean orientation;
            x = new Random().nextInt(9);
            y = new Random().nextInt(9);
            orientation = new Random().nextBoolean();
            Bateau b = null;
            if (symbole == 'C')
                b = new Croiseur();
            if (symbole == 'D')
                b = new Destroyeur();
            if (symbole == 'P')
                b = new PorteAvions();

            b.horizontal = orientation;
            intersection = grille.testIntersection(b, x, y);
            if (!intersection) {
                grille.place(b, x, y);
                bateaux.add(b);
            }
        } while (intersection);

    }


    /**
     * a voire {@link Joueur#tir(Grille)}.
     */
    @Override
    public void tir(Grille grilleAdversaire) {

    }
}
