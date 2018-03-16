package Algorithmes;

import objects.Graphe;
import objects.Sommet;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Anass on 2018-03-15.
 */
public class Tabou {
    public static final int MAX_ITERATIONS = 100;
    public ArrayList<Graphe> tabou;
    private double min = 1000000;

    public Tabou() {
        this.tabou = new ArrayList<>();
    }


    public void calcule(Graphe graphe) {
        //1) choisir un solution initial s*=s0
        //2) choisir une solution s dans le voisinage de s* n'appartient pas a tabou
        //3) si f(s) < f(s*) => s* = s
        //4) mettre a jour tabou
        //5) go to 2)
        min = graphe.cout();
        int current = 0;
        Graphe s_ = new Graphe();
        s_.sommets = new ArrayList<>(graphe.sommets);

        while (current < MAX_ITERATIONS) {
            System.out.println(current + ") best yet = " + min);
            //2)
            Graphe s = new Graphe();
            s.sommets = new ArrayList<>(s_.sommets);
            Random r = new Random();
            int n1 = r.nextInt(s.sommets.size());
            int n2 = r.nextInt(s.sommets.size());
            Sommet sommet = s.sommets.get(n1);
            s.sommets.set(n1, graphe.sommets.get(n2));
            s.sommets.set(n2, sommet);
            for (int i = tabou.size() - 1; i >= 0; i--) {
                Graphe g = tabou.get(i);
                if (g.equals(s))
                    break;
            }

            //3)
            if (s.cout() < s_.cout()) {
                s_.sommets = new ArrayList<>(s.sommets);
                if (s.cout() < min) {
                    min = s.cout();
                    System.out.println("better one found " + min);
                }
                current = -1;
            }
            //4)
            tabou.add(s);
            current++;
        }
    }

}
