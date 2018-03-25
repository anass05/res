/**
 * classe compte qui represente un compte de un ou 2 personnes
 *
 * @author Anass
 */
public class Compte {
    /**
     * le sole du compte
     */
    private double solde;
    /**
     * le tableau ou les 2 personnes sont stoqués
     */
    private Personne[] personnes;

    /**
     * le constructeur de la classe compte
     *
     * @param solde le solde du compte
     * @param p1    la premiere personne
     * @param p2    le deuxieme personne
     */
    public Compte(double solde, Personne p1, Personne p2) {
        this.solde = solde;
        personnes = new Personne[2];
        personnes[0] = p1;
        personnes[1] = p2;
    }


    /**
     * getter du compte
     */
    public double getSolde() {
        return solde;
    }

    /**
     * le methode qui crédite le compte
     *
     * @param m la somme a crédité
     */
    public void crediter(double m) {
        solde += m;
    }

    /**
     * la methode qui debite un compte
     *
     * @param m la somme a débiter
     */
    public boolean debiter(double m) {
        if (m > solde)
            return false;
        solde -= m;
        return true;
    }

    /**
     * la methode qui retourne les personnes du compte
     */
    public Personne[] getPersonnes() {
        return personnes;
    }

    /**
     * la methode qui definie les personne d'un compte
     */
    public void setPersonnes(Personne[] personnes) {
        this.personnes = personnes;
    }

}
