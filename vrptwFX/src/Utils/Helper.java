package Utils;

import Objects.Customer;
import Objects.Tournee;

import java.util.ArrayList;

/**
 * Created by Anass on 2018-05-21.
 */
public class Helper {
    public static final double VEHICULS_SPEED = 1.25;// 1.25km/min = 48km/heure;
    public static ArrayList<ArrayList<Double>> matriceDistance;

    public static ArrayList<ArrayList<Double>> matriceDistance(ArrayList<Customer> sommets) {
        ArrayList<ArrayList<Double>> matrice = new ArrayList<>();
        for (Customer s : sommets) {
            ArrayList<Double> row = new ArrayList<>();
            for (Customer ss : sommets) {
                row.add(s.distance(ss));
            }
            matrice.add(row);
        }
        matriceDistance = matrice;
        return matrice;
    }

    public static ArrayList<ArrayList<Double>> matriceDeTemps(ArrayList<Customer> sommets) {
        ArrayList<ArrayList<Double>> matrice = new ArrayList<>();
        for (Customer s : sommets) {
            ArrayList<Double> row = new ArrayList<>();
            for (Customer ss : sommets) {
                row.add(VEHICULS_SPEED * s.distance(ss));
            }
            matrice.add(row);
        }
        return matrice;
    }

    public static boolean isSolutionValid(Tournee t) {
        boolean valid = false;
//        System.out.println(t.customers.size() - 2);
        double soFar = t.customers.get(1).getStartTime();
        for (int i = 1; i < t.customers.size() - 2; i++) {
//            System.out.println("cust "+i);
            Customer c = t.customers.get(i);
            double distance = Helper.matriceDistance
                    .get(c.getId() - 1)
                    .get(t.customers.get(i + 1).getId() - 1);
//            System.out.println(c.getStartTime()+"/"+soFar);
            if (soFar + distance >= c.getStartTime() && soFar + distance <= c.getDueTime()) {
                valid = true;
                soFar += distance;
            } else
                return false;
        }
        return valid;
    }
}