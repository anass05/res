package Algorithmes;

import objects.Graphe;
import objects.Sommet;

import java.util.ArrayList;

/**
 * Created by Anass on 2018-03-14.
 */
public class Descente {
    public void calcule(Graphe graphe) {
        //1) solution initial
        //2) 2-opt
        //3) trouver la bonne solution
        //4) repeat
        System.out.println("best so far " + graphe.cout());
        ArrayList<Graphe> graphes = new ArrayList<>();
        for (int i = 0; i < graphe.sommets.size() - 2; i++) {
            Graphe g = new Graphe();
            g.sommets = new ArrayList<>(graphe.sommets);
            Sommet s = g.sommets.get(i);
            g.sommets.set(i, graphe.sommets.get(i + 2));
            g.sommets.set(i + 2, s);
            graphes.add(g);
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
        else
            System.out.println("done best = " + currentMin);
    }
}















