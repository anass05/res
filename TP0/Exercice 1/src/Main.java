import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Anass on 2018-02-06.
 */
public class Main {
    public static void main(String[] args) {
        moyenne();
    }

    public static void moyenne() {
        ArrayList<Integer> integers = new ArrayList<>();
        Random r = new Random();
        int n = r.nextInt(250000);
        System.out.println(n+" nombres générés ");
        for (int i = 0; i < n; i++) {
            integers.add(r.nextInt(2));
        }
        //System.out.println(integers);
        double S = somme(integers);
        double Q = sommeCarre(integers);
        double M = S / (double) n;
        double V = Q / n - M * M;
        double s = Math.sqrt(V);
        System.out.println("s = "+s);
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

}
