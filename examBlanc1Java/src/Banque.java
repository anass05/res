import java.util.ArrayList;
import java.util.HashMap;


/**
 * Cette classe représente une banque
 *
 * @author HAFIDI IMAD
 */
public class Banque {
    /**
     * Liste des clients, la clé est le numéro du client
     */
    private HashMap<Integer, Client> mesClients;
    /**
     * Nom de la banque
     */
    private String nom;

    /**
     * La méthode respecte les régles suivantes :
     * 1-Le client ne doit pas exister
     * 2-Le numéro du client est généré automatiquement ( dernier numéro +1)
     *
     * @param nom
     * @param age
     * @param prenom
     * @return false : si le client n'est pas ajouté
     */
    public boolean addClient(String nom, String prenom, int age) {
        ArrayList<Client> listDesClient = new ArrayList(mesClients.keySet());
        Client cx = new Client(nom, prenom, listDesClient.size());
        cx.setAge(age);
        mesClients.put(cx.getId(), cx);
        return true;
    }

    /**
     * La méthode ajoute un compte et l'attribue à un client
     *
     * @param client
     * @param typeCompte
     * @return false : si le compte n'est pas crée
     */
    public boolean addCompte(Client client, String typeCompte) {
        if (typeCompte.equals("CompteCourant")) {
            Compte cp = new CompteCourant(52, 0f, 1000000f);
            if (cp.addSignataires(client)) {
                client.mesComptes.add(cp);
                return true;
            }
        } else {
            Compte cp = new CompteEpargne(52, 0f);
            if (cp.addSignataires(client)) {
                client.mesComptes.add(cp);
                return true;
            }
        }
        return false;
    }

    /**
     * La méthode supprime un client si seulement si le solde de tous ses comptes
     * est zéro
     *
     * @param client
     * @return false si le client est supprimé
     */
    public boolean removeClient(Client client) {
        if (mesClients.containsKey(client.getId())) {
            mesClients.remove(client.getId());
            return false;
        }
        return true;
    }

    /**
     * Cette méthode affiche les relvés bancaires d'un client pour une date précise(05/14)
     *
     * @param id
     */
    public void afficheTousComptesClient(Integer id, String mois, String année) {
        Client c = mesClients.get(id);
        if (c != null) {
            for (Compte cc : c.mesComptes) {
                if (cc instanceof CompteCourant)
                    ((CompteCourant) cc).afficheReleveCourant(mois, année);
            }
        }

    }

}
