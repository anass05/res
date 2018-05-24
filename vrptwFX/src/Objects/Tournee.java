package Objects;

import Utils.FileIO;
import Utils.Helper;

import java.util.ArrayList;

/**
 * Created by Anass on 2018-05-21.
 */
public class Tournee {
    public static ArrayList<ArrayList<Double>> matriceDistance;
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

//            currentTime += Helper.matriceDistance
//                    .get(getCustomers().get(getCustomers().size() - 1).getId() - 1)
//                    .get(c.getId() - 1);
//            System.out.println(currentTime);
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
        customers.add(0,FileIO.depot);
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        return "Tournee{" +
                "vehicule=" + vehicule +
                ", customers=" + customers +
                '}';
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public double getCout() {
        double cout = 0;
        for (Customer c : customers) {
            if (c.getId() != 1)
                cout += Helper.matriceDistance
                        .get(customers.get(customers.size() - 1).getId() - 1)
                        .get(c.getId() - 1);
        }
        return cout;
    }

}
