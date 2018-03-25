import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Anass on 2018-02-06.
 */
public class Main {
    public static void main(String[] args) {
        ex3();
    }

    public static void ex3() {
        System.out.println(new Date());
        System.out.println(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        Calendar c = Calendar.getInstance();
        String[] mois = {"janvier", "fevrier", " mars", "avril", "mai", "juin", "juillet", "août", "septembre", "octobre", "novembre", "décembre"};
        String[] jours = {"dimanche", "lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi"};
        System.out.println(jours[c.get(Calendar.DAY_OF_WEEK) - 1] + "/" + c.get(Calendar.DAY_OF_MONTH) + "/" + mois[c.get(Calendar.MONTH)] + "/" + c.get(Calendar.YEAR));

        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd MMMMM yyyy HH:mm ", Locale.FRENCH);
        System.out.println("Maintenant:" + f.format(d));
    }

}
