import java.util.Calendar;

/**
 * La classe association qui est un marriage
 *
 * @author Anass
 */
public class Marriage {
    /**
     * le marie
     */
    private Personne marie;
    /**
     * la femme
     */
    private Personne femme;
    /**
     * la date de marriage
     */
    private Calendar date;

    /**
     * le constructeur de la claase mariage
     *
     * @param marie
     * @param femme
     * @param date
     */
    public Marriage(Personne marie, Personne femme, Calendar date) {
        this.marie = marie;
        this.femme = femme;
        this.date = date;
    }

    /**
     * les getters et le setters
     */
    public Personne getMarie() {
        return marie;
    }

    public void setMarie(Personne marie) {
        this.marie = marie;
    }

    public Personne getFemme() {
        return femme;
    }

    public void setFemme(Personne femme) {
        this.femme = femme;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
