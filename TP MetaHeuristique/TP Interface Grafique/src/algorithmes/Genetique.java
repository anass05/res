package algorithmes;

import UI.MainInterface;
import objects.Graphe;
import objects.Sommet;
import src.CoutComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Anass on 2018-03-16.
 */
public class Genetique {
    public static final int GENERATION_COUNT = 300;
    public static final int POPULATION_SIZE = 20;
    private ArrayList<Graphe> population;
    private ArrayList<Graphe> newGeneration;
    private MainInterface ui;

    public Genetique(Graphe graphe, MainInterface ui) {
        Graphe g = new Graphe();
        g.sommets = new ArrayList<>(graphe.sommets);
        population = new ArrayList<>();
        newGeneration = new ArrayList<>();
        this.ui = ui;
        for (int i = 0; i < POPULATION_SIZE; i++) {
            Graphe s = new Graphe();
            s.sommets = new ArrayList<>(graphe.sommets);
            Random r = new Random();
            int n1, n2;
            do {
                n2 = r.nextInt(s.sommets.size());
                n1 = r.nextInt(s.sommets.size());
            } while (n1 == n2);
            Sommet sommet = s.sommets.get(n1);
            s.sommets.set(n1, s.sommets.get(n2));
            s.sommets.set(n2, sommet);
            population.add(s);
        }
//        for (int i = 0; i < POPULATION_SIZE; i++) {
//            Graphe s = new Graphe();
//            s.sommets = new ArrayList<>(graphe.sommets);
//            Random r = new Random();
//            int n1 = r.nextInt(s.sommets.size());
//            int n2 = r.nextInt(s.sommets.size());
//            Sommet sommet = s.sommets.get(n1);
//            s.sommets.set(n1, s.sommets.get(n2));
//            s.sommets.set(n2, sommet);
//            newGeneration.add(s);
//
//        }
        newGeneration = new ArrayList<>(population);

    }

    public void calcule() {
        //1) selection
        //2) mutation
        //3) cross over
        //4) replacement
        Graphe gg = population.get(0);
        double min = population.get(0).cout();
        for (int i = 0; i < GENERATION_COUNT; i++) {
            selection();
            mutation();
            crossOver();


            for (Graphe g : population) {
                if (g.cout() < min) {
                    min = g.cout();
                    gg = g;
                }
            }
            try {
                Thread.sleep(0);
                ui.updates(gg);
                String[] ms = {"cout = " + min, "generation = " + i, "population = " + POPULATION_SIZE};
                ui.updated(ms);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            System.out.println(i + ") " + min);

        }


    }

    public void selection() {

        Collections.sort(population, new CoutComparator());
        Collections.sort(newGeneration, new CoutComparator());
//        System.out.println(population);

        int n = population.size();
        for (int i = n; i > n / 2; i--) {
            population.remove(i - 1);
        }

        int m = newGeneration.size();
        for (int i = m; i > m / 2; i--) {
            newGeneration.remove(i - 1);
        }

        for (Graphe g : newGeneration)
            population.add(g);
    }

    public void mutation() {
        for (int i = 0; i < population.size(); i++) {
            Graphe s = population.get(i);
            s.sommets = new ArrayList<>(s.sommets);
            Random r = new Random();
            int n1, n2;
            do {
                n2 = r.nextInt(population.get(0).sommets.size());
                n1 = r.nextInt(population.get(0).sommets.size());
            } while (n1 == n2);

            Sommet sommet = s.sommets.get(n1);
            s.sommets.set(n1, s.sommets.get(n2));
            s.sommets.set(n2, sommet);
            population.set(i, s);
        }
    }

    public void crossOver() {

        Random r = new Random();
        int n1, n2;
        do {
            n2 = r.nextInt(population.get(0).sommets.size());
            n1 = r.nextInt(population.get(0).sommets.size());
        } while (n1 >= n2);
//        n1 = 1;
//        n2 = 1;
        newGeneration = new ArrayList<>();
        boolean isFirst = true;
        for (int k = 0; k < 2; k++) {
            for (int i = 0; i < population.size() / 2; i++) {
                Graphe left, right;
                if (isFirst) {
                    left = population.get(i);
                    right = population.get(population.size() - 1 - i);
                } else {
                    left = population.get(population.size() - 1 - i);
                    right = population.get(i);
                }
                Graphe kid = new Graphe();
                ArrayList<Sommet> a1, a2, a3;
                a1 = new ArrayList<>();
                a2 = new ArrayList<>();
                a3 = new ArrayList<>();
                //for a2
                for (int j = n1; j < n2; j++) {
                    a2.add(left.sommets.get(j));
                }
                //for a1
                for (Sommet s : right.sommets) {
                    boolean found = false;
                    for (Sommet ss : a1) {
                        if (ss.equals(s)) {
                            found = true;
                            break;
                        }
                    }
                    for (Sommet ss : a2) {
                        if (ss.equals(s)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        a1.add(s);
                    }
                    if (a1.size() == n1)
                        break;
                }
                //for a3
                for (Sommet s : right.sommets) {
                    boolean found = false;
                    for (Sommet ss : a1) {
                        if (ss.equals(s)) {
                            found = true;
                            break;
                        }
                    }
                    for (Sommet ss : a2) {
                        if (ss.equals(s)) {
                            found = true;
                            break;
                        }
                    }
                    for (Sommet ss : a3) {
                        if (ss.equals(s)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        a3.add(s);
                    }
                    if (a3.size() == population.get(0).sommets.size() - a1.size() - a2.size())
                        break;
                }
                a1.addAll(a2);
                a1.addAll(a3);
                kid.sommets = new ArrayList<>(a1);
                newGeneration.add(kid);
            }
            isFirst = false;
        }
    }
}
