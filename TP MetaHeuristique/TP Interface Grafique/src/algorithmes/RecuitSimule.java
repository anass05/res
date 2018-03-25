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
    public static final double DECRESING_VALUE = 0.1;

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

        double temperature = MAX_TEMPERTAURE;
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

//            System.out.println(temperature + ") best yet = " + min);
            //3)
            //Graphe s = new Graphe();
            //s.sommets = new ArrayList<>(courante.sommets);

            ArrayList<Graphe> graphes = new ArrayList<>();
            for (int i = 0; i < graphe.sommets.size(); i++) {
                for (int j = i + 1; j < graphe.sommets.size(); j++) {
                    Graphe g = new Graphe();
                    g.sommets = new ArrayList<>(courante.sommets);
                    Sommet som = g.sommets.get(i);
                    g.sommets.set(i, courante.sommets.get(j));
                    g.sommets.set(j, som);
                    graphes.add(g);
                }
            }
            Graphe s = new Graphe();
            s.sommets = new ArrayList<>(graphes.get(new Random().nextInt(graphes.size())).sommets);
            //5)
//            double r = 1 / ((double) new Random().nextInt(10) + 1);

            //6)
            double P = Math.exp((courante.cout() - s.cout()) / temperature);
            if (P > Math.random()) {
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
