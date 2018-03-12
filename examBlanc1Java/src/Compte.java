import java.util.ArrayList;
import java.util.HashMap;

/**
 * Cette classe représente un compte bancaire
 *
 * @author HAFIDI IMAD
 */
public abstract class Compte {
    /**
     * Date d'ouverture du compte
     */
    private String date;
    /**
     * Représente les propriétaires du compte
     */
    private ArrayList<Client> Signataires;
    /**
     * Représente son numéro
     */
    private Integer numero;
    /**
     * Représente  son solde
     */
    private Float solde;
    /**
     * Représente la liste des opérations éfféctués sur le compte, la clé est
     * la date de l'opération
     */
    private HashMap<String, Operation> listeOperations;

    /**
     * Cnstructeur du compte : la date de'ouverture est la date du système
     */
    public Compte(Integer numero, Float solde) {
        super();
        this.numero = numero;
        this.solde = solde;
        Signataires = new ArrayList<Client>();
        listeOperations = new HashMap<String, Operation>();
    }

    public Integer getNumero() {
        return numero;
    }

    public Float getSolde() {
        return solde;
    }

    public void setSolde(Float solde) {
        this.solde = solde;
    }

    public String getDate() {
        return date;
    }

    public HashMap<String, Operation> getListeOperations() {
        return listeOperations;
    }

    public ArrayList<Client> getSignataires() {
        return Signataires;
    }

    /**
     * Une méthode qui permis d'ajouter un signataire au listes des
     * propriètaires du compte
     */

    /**
     * Methode qui sert a ajouter un signataire
     */
    public void ajouterSignataire(Client c) {
        Signataires.add(c);
    }

    public abstract boolean addSignataires(Client client);


    /**
     * Methode qui sert a supprimer un signataire
     */
    public void supprimerSignataire(Client c) {
        Signataires.remove(c);
    }

    /**
     * Une méthode qui permis supprimer un signataire au listes des
     * propriètaires du compte
     */
    public abstract boolean removeSignataires(Integer id);


    /**
     * Methode qui sert a ajouter une opperation
     */
    public void ajouterOpperation(Operation p) {
        listeOperations.put(p.getDate(), p);
    }

    /**
     * Une méthode qui permis d'ajouter une opération
     */
    public abstract boolean addOperation(Operation operation);


}
