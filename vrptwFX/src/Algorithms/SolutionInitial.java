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
public class SolutionInitial {
    public ArrayList<Customer> customers;
    public Solution solution;


    public SolutionInitial(ArrayList<Customer> customers) {
        this.customers = customers;
        solution = new Solution();
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

                if (!c.isVisited() && distance < 35) {
//                    System.out.println(c)4
                    //Capacity constraint
                    if (tournee.totalDomandesSoFar() + c.getDemandes() < Vehicule.CAPACITY) {
                        //Time window constraint
                        if (tournee.getCurrentTime() + distance < c.getStartTime()) {
                            tournee.currentTime += c.getStartTime();
                            tournee.addCustomer(c);
                            c.setVisited(true);
                            visited++;
                        } else if (tournee.getCurrentTime() + distance >= c.getStartTime() && tournee.getCurrentTime() + distance <= c.getDueTime()) {
                            tournee.currentTime += distance;
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
            /*if (i == 3)
                break;*/
        }
        return solution;
    }
}