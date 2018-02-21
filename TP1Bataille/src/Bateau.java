import java.util.ArrayList;

/**
 * Created by Anass on 2018-02-20.
 */
public abstract class Bateau {
    private int taille;
    private ArrayList<Case> cases;
    public boolean horizontal;

    public Bateau(int taille) {
        this.taille = taille;
        this.cases = new ArrayList<>();
    }

    public int getTaille() {
        return taille;
    }

    public boolean estCoule() {
        boolean coule = true;
        for (Case c : cases)
            if (!c.isEtat()) {
                coule = false;
                break;
            }
        return coule;
    }

    public void ajouteCase(Case c) {
        c.setBateau(this);
        cases.add(c);
    }

    public ArrayList<Case> getCases() {
        return cases;
    }

    public void setCases(ArrayList<Case> cases) {
        this.cases = cases;
    }

    public abstract String getSymbole();
}
