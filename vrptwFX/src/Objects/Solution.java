package Objects;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Anass on 2018-05-21.
 */
public class Solution {
    private HashMap<Customer, Vehicule> customersVehicules;
    private ArrayList<Tournee> tournees;

    public Solution() {
        customersVehicules = new HashMap<>();
        tournees = new ArrayList<>();
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
}
