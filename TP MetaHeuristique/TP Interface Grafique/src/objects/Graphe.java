package objects;

import UI.MainInterface;
import src.Main;

import java.util.ArrayList;

/**
 * Created by Anass on 2018-03-13.
 */
public class Graphe {
    public ArrayList<Sommet> sommets;

    public Graphe() {
        sommets = new ArrayList<>();
    }

    public void arrange(MainInterface ui) {
        ArrayList<Sommet> arrangedSommets = new ArrayList<>();
        ui.updates(this);
        System.out.println(sommets.size());
        arrangedSommets.add(sommets.get(0));
        double min = 100000;
        Sommet mini = null;
        for (int j = 0; j < sommets.size()-1; j++) {
            for (int i = 0; i < sommets.size(); i++) {
                Sommet s = sommets.get(i);
                if (!s.equals(arrangedSommets.get(j)) && matriceDistance().get(j).get(i) < min) {
                    boolean exists = false;
                    for (Sommet ss : arrangedSommets)
                        if (ss.equals(s)) {
                            exists = true;
                            break;
                        }
                    if (!exists) {
                        min = matriceDistance().get(0).get(i);
                        mini = sommets.get(i);
                    }
                }
            }
            arrangedSommets.add(mini);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ui.updates(arrangedSommets);
        }
        sommets = new ArrayList<>(arrangedSommets);
        System.out.println(sommets.size());
    }

    @Override
    public String toString() {
        String s = "";
        for (Sommet a : sommets) {
            s = s.concat(a + " ");
        }
//        s = s.concat(sommets.get(0) + " ");
        return s;
    }

    public double cout(){
        double cout = 0;
        for (int i = 0; i < sommets.size()-1; i++) {
            cout += matriceDistance().get(i).get(i+1);
        }
        cout += matriceDistance().get(0).get(sommets.size()-1);
        return cout;
    }

    @Override
    public boolean equals(Object obj) {
        for (int i = 0; i < sommets.size(); i++) {
            if(!((Graphe) obj).sommets.get(i).equals(sommets.get(i)))
                return false;
        }
        return true;
    }

    public ArrayList<ArrayList<Double>> matriceDistance() {
        ArrayList<ArrayList<Double>> matrice = new ArrayList<>();
        for (Sommet s : sommets) {
            ArrayList<Double> row = new ArrayList<>();
            for (Sommet ss : sommets) {
                row.add(s.distance(ss));
            }
            matrice.add(row);
        }
        return matrice;
    }
}

