package Algorithms;

import Objects.Customer;
import Objects.Solution;
import Objects.Tournee;
import Objects.Vehicule;
import Utils.FileIO;
import Utils.Helper;
import Utils.TimeComparator;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Anass on 2018-05-21.
 */
public class T5rbi9a {
    public ArrayList<Customer> customers;
    public ArrayList<Customer> TL;
    public ArrayList<Customer> TR;
    public ArrayList<Customer> BL;
    public ArrayList<Customer> BR;
    public Solution solution;


    public T5rbi9a(ArrayList<Customer> customers) {
        this.customers = customers;
        TL = new ArrayList<>();
        TR = new ArrayList<>();
        BL = new ArrayList<>();
        BR = new ArrayList<>();
        solution = new Solution();
        cluster();
    }

    public Solution run() {
        int i = 0;
        int visited = 0;
        Collections.sort(customers, new TimeComparator());
        while (visited < customers.size() - 1) {
            Vehicule v = new Vehicule("v" + i);
            Tournee tournee = new Tournee(v);
            tournee.addCustomer(FileIO.depot);
            for (int j = 1; j < customers.size(); j++) {
                Customer c = customers.get(j);

                double distance = Helper.matriceDistance
                        .get(tournee.getCustomers().get(tournee.getCustomers().size() - 1).getId() - 1)
                        .get(c.getId() - 1);
                if (tournee.getCustomers().get(tournee.getCustomers().size() - 1).getId() == 1)
                    distance = 0;

                if (!c.isVisited()) {
                    System.out.println(c);
                    //Capacity constraint
                    if (tournee.totalDomandesSoFar() + c.getDemandes() < Vehicule.CAPACITY) {
                        //Time window constraint
                        if (tournee.getCurrentTime() + distance < c.getStartTime()) {
                            tournee.currentTime += distance;
                            System.out.println(tournee.currentTime);
                            tournee.addCustomer(c);
                            c.setVisited(true);
                            visited++;
                        }
                    }
                }
            }
            tournee.addDepot();
//            System.out.println(tournee.getCurrentTime());
            solution.insertSolution(tournee);
            i++;
        }
        return solution;
    }


    public Solution runSmall() {
        int i = 0;
        int visited = 0;
        Collections.sort(customers, new TimeComparator());
        while (visited < customers.size() - 1) {
            Vehicule v = new Vehicule("v" + i);
            Tournee tournee = new Tournee(v);
            tournee.addCustomer(FileIO.depot);
            for (int j = 1; j < customers.size(); j++) {
                Customer c = customers.get(j);

                double distance = Helper.matriceDistance
                        .get(tournee.getCustomers().get(tournee.getCustomers().size() - 1).getId() - 1)
                        .get(c.getId() - 1);
                if (tournee.getCustomers().get(tournee.getCustomers().size() - 1).getId() == 1)
                    distance = 0;

                if (!c.isVisited() && distance < 15) {
                    System.out.println(c);
                    //Capacity constraint
                    if (tournee.totalDomandesSoFar() + c.getDemandes() < Vehicule.CAPACITY) {
                        //Time window constraint
                        if (tournee.getCurrentTime() + distance < c.getStartTime()) {
                            tournee.currentTime += distance;
                            System.out.println(tournee.currentTime);
                            tournee.addCustomer(c);
                            c.setVisited(true);
                            visited++;
                        }
                    }
                }
            }
            tournee.addDepot();
            solution.insertSolution(tournee);
            i++;
        }
        return solution;
    }


    public void cluster() {
        int x, y;
        x = customers.get(0).getX();
        y = customers.get(0).getY();
        for (Customer c : customers) {
            if (c.getX() < x && c.getY() < y)
                TL.add(c);
            if (c.getX() > x && c.getY() < y)
                TR.add(c);
            if (c.getX() < x && c.getY() > y)
                BL.add(c);
            if (c.getX() > x && c.getY() > y)
                BR.add(c);
        }
    }

    public Solution runClustered() {
        Solution solution = new Solution();

        for (Tournee t : bestTournerForCluster(TL))
            solution.insertSolution(t);
        for (Tournee t : bestTournerForCluster(TR))
            solution.insertSolution(t);
        for (Tournee t : bestTournerForCluster(BL))
            solution.insertSolution(t);
        for (Tournee t : bestTournerForCluster(BR))
            solution.insertSolution(t);

        this.solution = solution;
        tabou();
//        descente();

        return solution;
    }

    public ArrayList<Tournee> bestTournerForCluster(ArrayList<Customer> customers) {
        ArrayList<Tournee> tournees = new ArrayList<>();
        int i = 0;
        int visited = 0;
        Collections.sort(customers, new TimeComparator());
        while (visited < customers.size() - 1) {
//            System.out.println(visited);
//            System.out.println(customers.size() - 1);

            Vehicule v = new Vehicule("v" + i);
            Tournee tournee = new Tournee(v);
            tournee.addCustomer(FileIO.depot);
            for (int j = 1; j < customers.size(); j++) {
                Customer c = customers.get(j);

                double distance = Helper.matriceDistance
                        .get(tournee.getCustomers().get(tournee.getCustomers().size() - 1).getId() - 1)
                        .get(c.getId() - 1);
                if (tournee.getCustomers().get(tournee.getCustomers().size() - 1).getId() == 1)
                    distance = 0;

                if (!c.isVisited()) {
                    //System.out.println(c);
                    //Capacity constraint
                    if (tournee.totalDomandesSoFar() + c.getDemandes() < Vehicule.CAPACITY) {
                        //Time window constraint
                        if (tournee.getCurrentTime() + distance < c.getStartTime()) {
                            tournee.currentTime += distance;
                            tournee.addCustomer(c);
                            c.setVisited(true);
                            visited++;
                        }
                    }
                }
            }
            tournee.addDepot();
            tournees.add(tournee);
            i++;
        }
        return tournees;
    }

    public void descente() {
        System.out.println("s=" + solution.getTournees().size());
        System.out.println(voisinLocal(solution.getTournees().get(0)).size());
        System.out.println(voisinLocal(solution.getTournees().get(1)).size());
        System.out.println(voisinLocal(solution.getTournees().get(2)).size());
    }

    public void tabou() {
        System.out.println("s=" + solution.getTournees().size());
        System.out.println(voisinGlobal(solution.getTournees().get(0)).size());
        System.out.println(voisinGlobal(solution.getTournees().get(1)).size());
        System.out.println(voisinGlobal(solution.getTournees().get(2)).size());
    }

    public ArrayList<Tournee> voisinLocal(Tournee tournee) {
        ArrayList<Tournee> tournees = new ArrayList<>();
        tournee.customers.remove(0);
        tournee.customers.remove(tournee.customers.size()-1);
        for (int i = 1; i < tournee.getCustomers().size(); i++) {
            for (int j = i + 1; j < tournee.getCustomers().size(); j++) {
                //System.out.println("mini running");
                Tournee t = new Tournee(tournee.getVehicule());
                t.customers = new ArrayList<>(tournee.getCustomers());
                Customer s = t.customers.get(i);
                t.customers.set(i, tournee.customers.get(j));
                t.customers.set(j, s);
                t.addDepotStartEnd();
                System.out.println(t);
                if (Helper.isSolutionValid(t))
                    tournees.add(t);
            }
        }
        return tournees;
    }

    public ArrayList<Tournee> voisinGlobal(Tournee tournee) {
        ArrayList<Tournee> tournees = new ArrayList<>();
        tournee.customers.remove(0);
        tournee.customers.remove(tournee.customers.size()-1);
        for (int k = 0; k < 10000; k++) {
            Collections.shuffle(tournee.customers);
            for (int i = 1; i < tournee.getCustomers().size() - 1; i++) {
                for (int j = i + 1; j < tournee.getCustomers().size() - 1; j++) {
                    //System.out.println("mini running");
                    Tournee t = new Tournee(tournee.getVehicule());
                    t.customers = new ArrayList<>(tournee.getCustomers());
                    Customer s = t.customers.get(i);
                    t.customers.set(i, tournee.customers.get(j));
                    t.customers.set(j, s);
                    t.addDepotStartEnd();
//                    System.out.println(t);
                    if (Helper.isSolutionValid(t))
                        tournees.add(t);
                }
            }
        }
        return tournees;
    }
}
