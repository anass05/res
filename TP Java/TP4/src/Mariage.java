import java.util.Calendar;

/**
 * Created by Anass on 2018-03-20.
 */
public class Mariage {
    private Personne marie;
    private Personne femme;
    private Calendar date;

    public Mariage(Personne marie, Personne femme, Calendar date) {
        this.marie = marie;
        this.femme = femme;
        this.date = date;
    }

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
