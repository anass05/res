import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Anass on 2018-02-06.
 */
public class Main {
    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        double n = sc.nextDouble();
//        System.out.println(harmonique(n));
//
//        triangle((int) n);
//
//        moyenne();

        ex3();

        //first call was at 10:36 result was 1517826958920
        //second call was at 10:38 resutl was 1517827120069



    }

    public static double harmonique(double n) {
        double res = 0;
        while (n > 0) {
            res += 1 / n;
            n--;
        }
        return res;
    }

    public static void triangle(int n) {
        int miniCount = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < miniCount; j++) {
                System.out.print("*");
            }
            miniCount++;
            System.out.println();
        }
    }

    public static void moyenne() {
        ArrayList<Integer> integers = new ArrayList<>();
        Random r = new Random();
        int n = r.nextInt(250000);
        for (int i = 0; i < n; i++) {
            integers.add(r.nextInt(2));
        }
        System.out.println(integers);
        double S = somme(integers);
        double Q = sommeCarre(integers);
        double M = S / (double) n;
        double V = Q / n - M * M;
        double s = Math.sqrt(V);
        System.out.println(s);
    }

    public static int somme(ArrayList<Integer> list) {
        int somme = 0;
        for (Integer i : list)
            somme += i;
        return somme;
    }

    public static int sommeCarre(ArrayList<Integer> list) {
        int somme = 0;
        for (Integer i : list)
            somme += i * i;
        return somme;
    }

    public static void ex3() {
        System.out.println(new Date());
        System.out.println(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        Calendar c = Calendar.getInstance();
        String[] mois = {"janvier", "fevrier", " mars", "avril", "mai", "juin", "juillet", "août", "septembre", "octobre", "novembre", "décembre"};
        String[] jours = {"dimanche", "lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi"};
        System.out.println(jours[c.get(Calendar.DAY_OF_WEEK) - 1] + "/" + c.get(Calendar.DAY_OF_MONTH) + "/" + mois[c.get(Calendar.MONTH)] + "/" + c.get(Calendar.YEAR));

        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd MMMMM yyyy HH:mm " , Locale.FRENCH);
        System.out.println("Maintenant:" + f.format(d));
    }

}
