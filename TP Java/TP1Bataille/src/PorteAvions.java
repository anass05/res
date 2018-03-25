/**
 * Classe PorteAvions qui hérite de la classe Bateau
 *
 * @author Anass
 */
public class PorteAvions extends Bateau {
    /**
     * constructeur par defaut qui fait appelle au constructeur
     * de la super classe en donant une taille de 4
     */
    public PorteAvions() {
        super(4);
    }

    /**
     * a voire {@link Bateau#getSymbole()}.
     */

    public String getSymbole() {
        return "P";
    }

}
