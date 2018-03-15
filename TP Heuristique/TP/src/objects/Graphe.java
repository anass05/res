package objects;

import java.util.ArrayList;

/**
 * Created by Anass on 2018-03-13.
 */
public class Graphe {
    public ArrayList<Sommet> sommets;

    public Graphe() {
        sommets = new ArrayList<>();
    }

    public void attache() {
    }

    @Override
    public String toString() {
        String s = "";
        for (Sommet a : sommets) {
            s = s.concat(a + " ");
        }
        s = s.concat(sommets.get(0) + " ");
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

