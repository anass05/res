/**
 * Created by Anass on 2018-03-20.
 */
public class Compte {
    private double solde;
    private Personne[] personnes;
    private Poste poste;

    public Compte(double solde,Personne p1, Personne p2) {
        this.solde = solde;
        personnes = new Personne[2];
        personnes[0] = p1;
        personnes[1] = p2;
    }

    public double getSolde() {
        return solde;
    }

    public void crediter(double m) {
        solde += m;
    }

    public boolean debiter(double m) {
        if (m > solde)
            return false;
        solde -= m;
        return true;
    }

    public Personne[] getPersonnes() {
        return personnes;
    }

    public void setPersonnes(Personne[] personnes) {
        this.personnes = personnes;
    }

}
