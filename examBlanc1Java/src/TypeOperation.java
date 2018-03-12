/**
 * Cette ennumération représente le type d'opération, une opération est un débit si le
 * le montant est strictement positif et un crédit si le montant est strictement négative
 *
 * @author HAFIDI IMAD
 */
public enum TypeOperation {
    Debit(1, "Débit"), Credit(2, "Crédit");
    private Integer code;
    private String nom;

    private TypeOperation(Integer code, String nom) {
        this.code = code;
        this.nom = nom;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


}
