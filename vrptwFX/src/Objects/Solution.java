package Objects;

import Utils.Helper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Anass on 2018-05-21.
 */
public class Solution {
    private HashMap<Customer, Vehicule> customersVehicules;
    public ArrayList<Tournee> tournees;

    public Solution() {
        customersVehicules = new HashMap<>();
        tournees = new ArrayList<>();
    }

    public Solution(Solution solutionInitial) {
        tournees = new ArrayList<>();
        for (Tournee t : solutionInitial.getTournees()) {
            Tournee TT = new Tournee(t.getVehicule());
            TT.setCustomers(new ArrayList<>(t.getCustomers()));
            this.insertSolution(TT);
        }
    }

    public void makeSolutionHash(ArrayList<Tournee> tournes) {
        for (Tournee tournee : tournes) {
            for (Customer c : tournee.getCustomers())
                customersVehicules.put(c, tournee.getVehicule());
        }
    }

    public void insertSolutionHash(Tournee tournee) {
        for (Customer c : tournee.getCustomers())
            customersVehicules.put(c, tournee.getVehicule());
    }

    public void insertSolution(Tournee tournee) {
        tournees.add(tournee);
    }

    public ArrayList<Tournee> getTournees() {
        return tournees;
    }

    public double coutTotal() {
        double cout = 0;
        for (Tournee t : tournees) {
            for (int i = 0; i < t.customers.size() - 1; i++) {
                cout += Helper.matriceDistance
                        .get(t.customers.get(i).getId() - 1)
                        .get(t.customers.get(i + 1).getId() - 1);
            }
            //cout += t.getCout();
        }
        return cout;
    }
}
