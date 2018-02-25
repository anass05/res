import java.util.Random;

/**
 * Created by Anass on 2018-02-20.
 */
public class JoueurOrdi extends Joueur {

    /**
     * Le constructeur par defaut de la classe JoueurOrdi
     */
    public JoueurOrdi() {
        super("Ordinateur");
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
        boolean placerSuccess;
        boolean orientation;
        do {
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
            placerSuccess = grille.place(b, x, y);
            bateaux.add(b);
        } while (!placerSuccess);

    }

    @Override
    public void tir(Grille grilleAdversaire) {

    }
}
