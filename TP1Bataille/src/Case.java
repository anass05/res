/**
 * Cette classe represente la petite instance qui est une case de la grille du bataille
 *
 * @author Anass
 */
public class Case {
    /**
     * L'état d'une case prend la valeur true si elle a fait l'objet d'un tir et false sinon
     */
    private boolean etat;

    /**
     * Le constructeur par defaut de la classe Case
     * oû l'état est fausse
     */
    public Case() {
        this.etat = false;
    }

    /**
     * Le 2 eme constructeur de la classe Case
     * oû l'état est fausse ou vrai
     *
     * @param etat l'état de la case
     */
    public Case(boolean etat) {
        this.etat = etat;
    }

    /**
     * methode qui renvoie l'état de la case
     */
    public boolean isEtat() {
        return etat;
    }

    /**
     * methode qui définie l'état de la case
     */
    public void setEtat(boolean etat) {
        this.etat = etat;
    }
}
