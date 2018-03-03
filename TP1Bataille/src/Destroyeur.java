/**
 * Classe Destroyeur qui hérite de la classe Bateau
 *
 * @author Anass
 */
public class Destroyeur extends Bateau {
    /**
     * constructeur par defaut qui fait appelle au constructeur
     * de la super classe en donant une taille de 2
     */
    public Destroyeur() {
        super(2);
    }

    /**
     * a voire {@link Bateau#getSymbole()}.
     */
    public String getSymbole() {
        return "D";
    }

}
