/**
 * Classe Croiseur qui hérite de la classe Bateau
 *
 * @author Anass
 */
public class Croiseur extends Bateau {
    /**
     * constructeur par defaut qui fait appelle au constructeur
     * de la super classe en donant une taille de 3
     */
    public Croiseur() {
        super(3);
    }

    /**
     * a voire {@link Bateau#getSymbole()}.
     */
    public String getSymbole() {
        return "C";
    }

}
