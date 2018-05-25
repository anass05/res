package Objects;

import Utils.FileIO;
import Utils.Helper;

import java.util.ArrayList;

/**
 * Created by Anass on 2018-05-21.
 */
public class Tournee {
    private Vehicule vehicule;
    public ArrayList<Customer> customers;
    public double currentTime;

    public Tournee(Vehicule vehicule) {
        this.vehicule = vehicule;
        customers = new ArrayList<>();
    }

    public void addCustomer(Customer c) {
        if (totalDomandesSoFar() + c.getDemandes() < Vehicule.CAPACITY) {
            customers.add(c);
        } else
            throw new RuntimeException("max demands exceeded for a single vehicle!");
    }

    public int totalDomandesSoFar() {
        int total = 0;
        for (Customer c : customers)
            total += c.getDemandes();
        return total;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void addDepot() {
        customers.add(FileIO.depot);
    }

    public void addDepotStartEnd() {
        customers.add(FileIO.depot);
        customers.add(0, FileIO.depot);
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        String d = "";
        for (Customer c : customers)
            d += " " + c.getId();
        return d;
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public double getCout() {
        double cout = 0;
        for (int i = 0; i < customers.size() - 1; i++) {
            cout += Helper.matriceDistance.
                    get(customers.get(i).getId() - 1).
                    get(customers.get(i + 1).getId() - 1);

        }

        return cout;
    }

}
