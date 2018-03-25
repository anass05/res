/**
 * Une exception indiquant que toutes les cases d'un bateau ont ete affectees
 *
 * @author HAFIDI IMAD
 */
public class ToutesCasesAffecteesException extends Exception {

    /**
     * Un constructeur
     */
    public ToutesCasesAffecteesException() {
        super("Toutes les cases du bateau ont deja ete affectees");
    }
}
