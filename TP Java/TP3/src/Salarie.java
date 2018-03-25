/**
 * Interface Salarié
 *
 * @author Anass
 */
public interface Salarie {
    /**
     * Methode qui calcule le salaire
     */
    Float calculeSalaire(Integer NbrHeurs);

    /**
     * Methode qui calcule le nombre de jours de vacances
     */
    Float calculeVacances(Integer Nbrjours);
}
