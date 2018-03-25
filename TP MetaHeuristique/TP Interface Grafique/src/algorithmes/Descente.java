package algorithmes;

import UI.MainInterface;
import objects.Graphe;
import objects.Sommet;

import java.util.ArrayList;

/**
 * Created by Anass on 2018-03-14.
 */
public class Descente {

    private MainInterface ui;

    public Descente(MainInterface ui) {
        this.ui = ui;
    }

    public void calcule(Graphe graphe) {
        //1) solution initial
        //2) 2-opt
        //3) trouver la bonne solution
        //4) repeat
//        try {
//            Thread.sleep(50);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        ui.updates(graphe);
        String[] ms = {"cout: " + graphe.cout()};
        ui.updated(ms);
        System.out.println("running");
        ArrayList<Graphe> graphes = new ArrayList<>();

        for (int i = 0; i < graphe.sommets.size(); i++) {
            for (int j = i + 1; j < graphe.sommets.size(); j++) {
                System.out.println("mini running");
                Graphe g = new Graphe();
                g.sommets = new ArrayList<>(graphe.sommets);
                Sommet s = g.sommets.get(i);
                g.sommets.set(i, graphe.sommets.get(j));
                g.sommets.set(j, s);
                graphes.add(g);
            }
        }

        double premierMin = graphe.cout();
        double currentMin = premierMin;
        Graphe meilleur = graphe;
        for (Graphe gg : graphes) {
            double temp = gg.cout();
            if (temp < currentMin) {
                currentMin = temp;
                meilleur = gg;
            }
        }
        if (premierMin > currentMin)
            calcule(meilleur);
    }
}















