/**
 * Une exception indiquant que vous avez tirez sur une case inexistante
 *
 * @author Anass
 */
public class TireSurCaseInexistanteException extends Exception {

    /**
     * Un constructeur
     */
    public TireSurCaseInexistanteException() {
        super("Tire sur une case inexistante");
    }
}
