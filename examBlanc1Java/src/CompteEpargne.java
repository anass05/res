
public class CompteEpargne extends Compte {
    private Double interet;


    public CompteEpargne(Integer numero, Float solde) {
        super(numero, solde);
    }

    @Override
    /**
     * Cette méthode doit respecter les régles métier suivantes :
     * O- Verifier que le client n'est pas déja signataire
     * 1-Les signataires doivent avoir le même nom de famille
     * 2-Le nombre de signataire ne doit pas dépasser 3
     * 3-L'interet est calculé par la moyenne d'interer offert de chaque tranche
     * d'age des signataires ( 5% pour les mineurs, 4% de 18 à 25, 3.5 de 25 à 40 ,
     * 3% de 40 à 60 et 2.5 pour le reste)
     */
    public boolean addSignataires(Client client) {
        if (this.getSignataires().contains(client)) return false;
        if (!this.getSignataires().get(0).getNom().equals(client.getNom())) return false;
        if (this.getSignataires().size() > 2) return false;
        this.addIntert();
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
     * Cette méthode ajoute une opération en respectant les régles suivantes :
     * 1- Pour un débit :  la somme ne doit jamais dépasser le solde
     * 2- Pour un crédit : l'opération est permise seulement en anniversaire du compte
     * 3- La méthode doit recalculer le solde
     */
    @Override
    public boolean addOperation(Operation operation) {
        if (operation.getType().getCode().equals(TypeOperation.Debit)) {
            if (getSolde() < operation.getMontant())
                return false;
            setSolde(getSolde() - operation.getMontant());
            return true;
        }

        if (operation.getType().getCode().equals(TypeOperation.Credit)) {
            String j, m, a;
            String[] sp = getDate().split("/");
            j = sp[0];
            m = sp[1];
            a = sp[2];
            if (operation.getAnnee().equals(a) && operation.getMois().equals(m) && operation.getJour().equals(j))
                setSolde(getSolde() + operation.getMontant());
            return true;
        }

        return false;
    }

    /**
     * Calcul l'interet et ajoute la somme au solde
     */
    public void addIntert() {
        float in = 0;
        float s;
        for (int i = 0; i < this.getSignataires().size(); i++) {
            if (this.getSignataires().get(i).getAge() < 18) s = (float) 0.05;
            else {
                if (this.getSignataires().get(i).getAge() < 25) s = (float) 0.04;
                else {
                    if (this.getSignataires().get(i).getAge() < 40) s = (float) 0.035;
                    else {
                        if (this.getSignataires().get(i).getAge() < 60) s = (float) 0.03;
                        else s = (float) 0.025;
                    }
                }
            }
            in = in + s;
        }
        this.interet = (double) (in / this.getSignataires().size());
    }
}
