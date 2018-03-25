package algorithmes;

import UI.MainInterface;
import objects.Graphe;
import objects.Sommet;
import src.CoutComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Anass on 2018-03-22.
 */
public class Genetic2 {
    public static final int GENERATION_COUNT = 100000;
    public static final int POPULATION_SIZE = 48;
    public static final double MUTATION_RATE = 0.05;
    private ArrayList<Graphe> population;
    private ArrayList<Graphe> selected;
    private ArrayList<Graphe> children;
    private MainInterface ui;

    public Genetic2(Graphe graphe, MainInterface ui) {
        population = new ArrayList<>();
        this.ui = ui;
        for (int i = 0; i < POPULATION_SIZE; i++) {
            Graphe g = new Graphe();
            g.sommets = new ArrayList<>(graphe.sommets);
            g.shuffle();
            population.add(g);
        }
    }

    public void calcule() {
        Graphe gg = population.get(0);
        double min = population.get(0).cout();
        for (int i = 0; i < GENERATION_COUNT; i++) {

            selection();
            crossOver();
            mutation();

//            System.out.println("++++++++++++++++");
            for (Graphe g : population) {
//                System.out.println(g.cout());
                if (g.cout() < min) {
                    min = g.cout();
                    gg = g;
                }
            }
//            System.out.println();

            try {
                Thread.sleep(0);
//                System.out.println(gg);
                ui.updates(gg);
                String[] ms = {"cout = " + min, "generation = " + i, "population = " + POPULATION_SIZE};
                ui.updated(ms);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void selection() {
        double sommeFitnesses = 0;
        for (Graphe g : population)
            sommeFitnesses += g.fitness();


        double r = 0 + (sommeFitnesses) * new Random().nextDouble();

        Collections.sort(population, new CoutComparator());


        double tempSum = 0;
        selected = new ArrayList<>();

        for (int j = 0; j < population.size() / 2; j++) {
            for (int i = 0; i < population.size(); i++) {
                tempSum += population.get(i).fitness();
                if (tempSum > r) {
                    selected.add(population.get(i));
                    break;
                }
            }
        }
    }


    public void crossOver() {
        children = new ArrayList<>();
//        System.out.println(population.size());
//        System.out.println("++++");
        int k = 0;
//        for (int k = 0; k < selected.size() - 1; k += 2) {
        Random r = new Random();
        int n1, n2;
        do {
            n2 = r.nextInt(selected.get(0).sommets.size() - 1);
            n1 = r.nextInt(selected.get(0).sommets.size() - 1);
        } while (n1 == 0 || n1 >= n2);
//        n1 = 3;
//        n2 = 5;

        Graphe p1, p2;
        p1 = selected.get(k);
        p2 = selected.get(k + 1);

        Graphe kid1, kid2;

        ArrayList<Sommet> a11, a12, a13;
        ArrayList<Sommet> a21, a22, a23;
        a11 = new ArrayList<>();
        a12 = new ArrayList<>();
        a13 = new ArrayList<>();

        a21 = new ArrayList<>();
        a22 = new ArrayList<>();
        a23 = new ArrayList<>();

        for (int j = n1; j < n2; j++) {
            a12.add(p2.sommets.get(j));
            a22.add(p1.sommets.get(j));
        }

        HashMap<Sommet, Sommet> corrector1 = new HashMap<>();
        HashMap<Sommet, Sommet> corrector2 = new HashMap<>();
        for (int i = 0; i < a12.size(); i++) {
            corrector1.put(a12.get(i), a22.get(i));
            corrector2.put(a22.get(i), a12.get(i));
        }

        for (Sommet s : p1.sommets) {
            if (corrector1.get(s) != null)
                if (corrector1.get(corrector1.get(s)) != null)
                    a11.add(corrector1.get(corrector1.get(s)));
                else
                    a11.add(corrector1.get(s));
            else
                a11.add(s);

            if (a11.size() == n1)
                break;
        }

        for (int i = n2; i < p1.sommets.size(); i++) {
            Sommet s = p1.sommets.get(i);
            if (corrector1.get(s) != null)
                if (corrector1.get(corrector1.get(s)) != null)
                    a13.add(corrector1.get(corrector1.get(s)));
                else
                    a13.add(corrector1.get(s));
            else
                a13.add(s);

            if (a13.size() == selected.get(0).sommets.size() - a11.size() - a12.size())
                break;
        }

        //for the kid parent

        for (Sommet s : p2.sommets) {
            if (corrector2.get(s) != null)
                if (corrector2.get(corrector2.get(s)) != null)
                    a21.add(corrector2.get(corrector2.get(s)));
                else
                    a21.add(corrector2.get(s));
            else
                a21.add(s);

            if (a21.size() == n1)
                break;
        }

        for (int i = n2; i < p2.sommets.size(); i++) {
            Sommet s = p2.sommets.get(i);
            if (corrector2.get(s) != null)
                if (corrector2.get(corrector2.get(s)) != null)
                    a23.add(corrector2.get(corrector2.get(s)));
                else
                    a23.add(corrector2.get(s));
            else
                a23.add(s);

            if (a23.size() == selected.get(0).sommets.size() - a21.size() - a22.size())
                break;
        }

        a11.addAll(a12);
        a11.addAll(a13);

        a21.addAll(a22);
        a21.addAll(a23);

        kid1 = new Graphe();
        kid1.sommets = new ArrayList<>(a11);
        kid2 = new Graphe();
        kid2.sommets = new ArrayList<>(a21);
        children.add(kid1);
        children.add(kid2);
//        }
        ArrayList<Graphe> newPop = new ArrayList<>(selected);
        newPop.addAll(children);
        population.clear();
        population.addAll(newPop);

//        System.out.println(kid1);
//        System.out.println(kid2);
//        System.out.println(p1);
//        System.out.println(p2);

        for (int i = 0; i < kid1.sommets.size(); i++) {
            for (int j = i + 1; j < kid1.sommets.size(); j++) {
                if (kid1.sommets.get(i).equals(kid1.sommets.get(j))) {
                    System.out.println(n1 + "|" + n2);
                    System.out.println(kid1);
                    System.out.println();
                    System.out.println(p1);
                    System.out.println(corrector1);
                    System.out.println(p2);
                    System.exit(0);
                }
            }
        }

        for (int i = 0; i < kid2.sommets.size(); i++) {
            for (int j = i + 1; j < kid2.sommets.size(); j++) {
                if (kid2.sommets.get(i).equals(kid2.sommets.get(j)))
                    System.out.println(kid2);
            }
        }

    }


    public void mutation() {
        for (int i = 0; i < population.size(); i++) {
            if (Math.random() < MUTATION_RATE) {
                Graphe s = population.get(i);
                s.sommets = new ArrayList<>(s.sommets);
                Random r = new Random();
                int n1, n2;
                do {
                    n2 = r.nextInt(population.get(0).sommets.size() - 1);
                    n1 = r.nextInt(population.get(0).sommets.size() - 1);
                } while (n1 == n2);
                Sommet sommet = s.sommets.get(n1);
                s.sommets.set(n1, s.sommets.get(n2));
                s.sommets.set(n2, sommet);
                population.set(i, s);
            }
        }
    }

    public boolean sommetExist(ArrayList<Sommet> sommets, Sommet s) {
        for (Sommet ss : sommets)
            if (s.equals(ss))
                return true;
        return false;
    }
}
