/**
 * Une exception indiquant qu'un bateau est hors grille
 *
 * @author Anass
 */
public class BateauHorsGrilleException extends Exception {

    /**
     * Un constructeur
     */
    public BateauHorsGrilleException() {
        super("Le bateau est hors grille");
    }
}
