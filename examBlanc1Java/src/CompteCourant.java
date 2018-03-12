import java.util.Map;

public class CompteCourant extends Compte {
    /**
     * le solde Max est une valeur négative que le compte ne doit jamais dépasser
     */
    private Float soldeMax;

    public CompteCourant(Integer numero, Float solde, Float soldeMax) {
        super(numero, solde);
        this.soldeMax = soldeMax;
    }

    @Override
    /**
     * Le  nombre de signataire ne doit jamais dépasser 4
     */
    public boolean addSignataires(Client client) {
        if (getSignataires().size() > 3)
            return false;
        super.ajouterSignataire(client);
        return true;
    }

    @Override
    public boolean removeSignataires(Integer id) {
        for (Client c : super.getSignataires())
            if (c.getId().equals(id)) {
                super.supprimerSignataire(c);
                return true;
            }
        return false;
    }

    /**
     * Recalcule le solde
     */
    @Override
    public boolean addOperation(Operation operation) {
        super.ajouterOpperation(operation);
        return true;
    }

    /**
     * Une méthode qui affiche le relvé d'un mois pour une année (5/14)
     */
    public void afficheReleveCourant(String mois, String anne) {
        Float tot = new Float(0);
        for (Map.Entry<String, Operation> entry : getListeOperations().entrySet()) {
            Operation p = entry.getValue();
            if ((p.getMois().equals(mois) && p.getAnnee().equals(anne)) && p.getType().getCode().equals(TypeOperation.Debit))
                tot -= p.getMontant();
            if ((p.getMois().equals(mois) && p.getAnnee().equals(anne)) && p.getType().getCode().equals(TypeOperation.Credit))
                tot += p.getMontant();
        }
        System.out.println(tot);
    }

    /**
     * Une méthode qui affiche la somme des opérations de Débit d'un mois
     * pour une année (5/14)
     */
    public void afficheDébitCourant(String mois, String année) {
        Float tot = new Float(0);
        for (Map.Entry<String, Operation> entry : getListeOperations().entrySet()) {
            Operation p = entry.getValue();
            if ((p.getMois().equals(mois) && p.getAnnee().equals(année)) && p.getType().getCode().equals(TypeOperation.Debit))
                tot += p.getMontant();
        }
        System.out.println(tot);
    }

    /**
     * Une méthode qui affiche la somme des opérations de Crédit d'un mois
     * pour une année (5/14)
     *
     * @param mois
     * @param année
     */
    public void afficheCréditCourant(String mois, String année) {
        Float tot = new Float(0);
        for (Map.Entry<String, Operation> entry : getListeOperations().entrySet()) {
            String key = entry.getKey();
            Operation p = entry.getValue();
            if ((p.getMois().equals(mois) && p.getAnnee().equals(année)) && p.getType().getCode().equals(TypeOperation.Credit))
                tot += p.getMontant();
        }
        System.out.println(tot);
    }


}
