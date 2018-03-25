import java.util.HashSet;

/**
 * La classe qui represente un bateau
 *
 * @author Anass
 */
public abstract class Bateau {
    /**
     * Le bateau est définie par une taille des cases et une orientation
     */
    private int taille;
    private HashSet<Case> cases;
    public boolean horizontal;

    /**
     * Constructeur par defaut du bateau
     *
     * @param taille: la taille du bateau
     */
    public Bateau(int taille) {
        this.taille = taille;
        this.cases = new HashSet<>();
    }

    /**
     * methode qui renvoie la taille du bateau
     */
    public int getTaille() {
        return taille;
    }

    /**
     * methode qui renvoie un boolean true si le bateau est écoulé
     */
    public boolean estCoule() {
        for (Case c : cases) {
            if (!c.isEtat())
                return false;
        }
        return true;
    }

    /**
     * methode qui ajoute une case au bateau
     *
     * @param c: la case a ajouter
     * @throws ToutesCasesAffecteesException si toutes les cases du bateau on ete assignee
     */
    public void ajouteCase(Case c) throws ToutesCasesAffecteesException {
        if (cases.size() == taille)
            throw new ToutesCasesAffecteesException();
        c.setBateau(this);
        cases.add(c);
    }

    /**
     * la methode qui renvoie la liste des cases du bateau
     */
    public HashSet<Case> getCases() {
        return cases;
    }

    /**
     * la methode qui definie les case du bateau
     */
    public void setCases(HashSet<Case> cases) {
        this.cases = cases;
    }

    /**
     * la methode abstraite qui renvoie le symbole d u beateu
     */
    public abstract String getSymbole();
}
