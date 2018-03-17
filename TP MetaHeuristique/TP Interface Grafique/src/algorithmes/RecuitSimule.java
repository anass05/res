package algorithmes;

import UI.MainInterface;
import objects.Graphe;
import objects.Sommet;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Anass on 2018-03-15.
 */
public class RecuitSimule {
    public static final int MAX_TEMPERTAURE = 10000;
    public static final int DECRESING_VALUE = 1;

    private MainInterface ui;

    public RecuitSimule(MainInterface ui) {
        this.ui = ui;
    }

    public void calcule(Graphe graphe) {
//        1. Choisir une solution s dans S ainsi qu’une température
//           initiale T.
//        2. Tant qu’aucun critère d’arrêt n’est satisfait faire
//                  3. Choisir aléatoirement s’ dans N(s);
//                  4. Générer un nombre réel aléatoire r dans [0,1];
//                  5. Si r < p(T,s,s’) alors poser s := s’;
//                  6. Mettre à jour T;
//        7. Fin du tant que

        int temperature = MAX_TEMPERTAURE;
        double min = 100000;
        Graphe courante = new Graphe();
        Graphe meilleur = new Graphe();
        courante.sommets = new ArrayList<>(graphe.sommets);
        meilleur.sommets = new ArrayList<>(graphe.sommets);

        while (temperature > 0) {
//            try {
//                Thread.sleep(5);

            ui.updates(meilleur);
            String ms[] = {"temperature: " + temperature, "cout: " + min};
            ui.updated(ms);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            System.out.println(temperature + ") best yet = " + min);
            //3)
            Graphe s = new Graphe();
            s.sommets = new ArrayList<>(courante.sommets);
            Random random = new Random();
            int n1 = random.nextInt(s.sommets.size());
            int n2 = random.nextInt(s.sommets.size());
            Sommet sommet = s.sommets.get(n1);
            s.sommets.set(n1, s.sommets.get(n2));
            s.sommets.set(n2, sommet);
            System.out.println(s);
            //5)
            double r = 1 / ((double) random.nextInt(1000) + 1);

            //6)
            double P = Math.exp((s.cout() - courante.cout()) / (double) temperature);
            if (P > r) {
                courante.sommets = new ArrayList<>(s.sommets);
            }

            if (courante.cout() < meilleur.cout()) {
                min = courante.cout();
                meilleur.sommets = new ArrayList<>(courante.sommets);
            }
            temperature -= DECRESING_VALUE;
        }
    }

}
