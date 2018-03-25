package src;

import objects.Sommet;

import java.util.ArrayList;

/**
 * Created by Anass on 2018-03-22.
 */
public class Helper {

    public static ArrayList<ArrayList<Double>> matriceDistance(ArrayList<Sommet> sommets){
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
