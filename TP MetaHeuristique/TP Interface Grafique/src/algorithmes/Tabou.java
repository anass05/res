package algorithmes;

import UI.MainInterface;
import objects.Graphe;
import objects.Sommet;

import java.util.ArrayList;

/**
 * Created by Anass on 2018-03-15.
 */
public class Tabou {
    public static final int MAX_ITERATIONS = 100;
    public ArrayList<Graphe> tabou;
    private double best_min = 1000000;
    private MainInterface ui;
    private Graphe best;

    public Tabou(MainInterface ui) {
        this.tabou = new ArrayList<>();
        this.ui = ui;
        this.best = new Graphe();
    }

    public void calcule(Graphe graphe) {
        calcule(graphe, 0);
        this.best.sommets = new ArrayList<>(graphe.sommets);
    }


    public void calcule(Graphe graphe, int current_itteration) {

//        try {
//            Thread.sleep(50);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        ui.updates(this.best);
        String[] ms = {"cout: " + best_min, "itteration: " + current_itteration, "MAX_ITERATIONS: " + MAX_ITERATIONS};
        ui.updated(ms);

        ArrayList<Graphe> graphes = new ArrayList<>();
        for (int i = 0; i < graphe.sommets.size(); i++) {
            for (int j = i + 1; j < graphe.sommets.size(); j++) {
                Graphe g = new Graphe();
                g.sommets = new ArrayList<>(graphe.sommets);
                Sommet s = g.sommets.get(i);
                g.sommets.set(i, graphe.sommets.get(j));
                g.sommets.set(j, s);
                graphes.add(g);
            }
        }

        double currentMin = graphe.cout();
        Graphe meilleur = graphe;
//        for (Graphe gg : graphes) {
        for (int i = 0; i < graphes.size(); i++) {
            Graphe gg = graphes.get(i);
            double temp = gg.cout();
            if (temp < currentMin) {
                currentMin = temp;
                meilleur = gg;
            }
        }
//        }
//        Collections.sort(graphes,new CoutComparator());
//        Graphe meilleur = graphes.get(0);
        //meilleur = la solution qui minimise f(s’) dans N(s)
        if (meilleur.cout() < best_min) {
            best_min = meilleur.cout();
            graphe.sommets = new ArrayList<>(meilleur.sommets);
            this.best.sommets = new ArrayList<>(meilleur.sommets);
            current_itteration = 0;
        } else {
            graphe.shuffle();
            current_itteration++;
        }

        for (int i = tabou.size() - 1; i >= 0; i--) {
            if (tabou.get(i).equals(graphe)) {
                graphe.shuffle();
                i = tabou.size() - 1;
                if (current_itteration >= MAX_ITERATIONS)
                    return;
            }
        }

        Graphe tabouEntry = new Graphe();
        tabouEntry.sommets = new ArrayList<>(meilleur.sommets);
        tabou.add(tabouEntry);
        if (current_itteration < MAX_ITERATIONS)
            calcule(graphe, ++current_itteration);

    }
}
