package Algorithms;

import Objects.Customer;
import Objects.Solution;
import Objects.Tournee;
import Objects.Vehicule;
import Utils.Helper;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Anass on 2018-05-24.
 */
public class TabouThread implements Runnable {
    private Solution solutionInitial;
    private static int TABOU_SIZE = 50000;
    private Thread t;
    private TBListener rstListener;

    public TabouThread(Solution solutionInitial, int tabou, TBListener rstListener) {
        this.rstListener = rstListener;
        TABOU_SIZE = tabou;
        this.solutionInitial = new Solution(solutionInitial);
        //System.out.println("v num : " + solutionInitial.getTournees().size());

    }

    public void run() {
        double minBefore, minAfter;
        //minAfter = minBefore = solutionInitial.coutTotal();
        int count = 0;
        do {
            count++;
            rstListener.onTBUpdate((float) count * (TABOU_SIZE / 10) / TABOU_SIZE);
            minBefore = solutionInitial.coutTotal();
            for (int i = 0; i < solutionInitial.getTournees().size(); i++) {
                //System.out.println(solutionInitial.getTournees().size()-i);
                Tournee tournee = solutionInitial.getTournees().get(i);
                Tournee best = solutionInitial.getTournees().get(i);
                double min = tournee.getCout();
                for (Tournee t : voisinGlobal(tournee)) {
                    //System.out.println(t.getCout());
                    if (t.getCout() < min) {
                        min = t.getCout();
                        best = t;
                    }
                }
                if (tournee.getCout() > min)
                    solutionInitial.tournees.set(i, best);
                //   System.out.println("M " + tournee.getCout() + " m " + min);
            }
            minAfter = solutionInitial.coutTotal();
        } while (minAfter < minBefore);
        try {
            rstListener.hasTBDone(solutionInitial);
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Tournee> voisinGlobal(Tournee tournee) {

        ArrayList<Tournee> tournees = new ArrayList<>();
        Tournee ttt = new Tournee(new Vehicule("f"));
        for (Customer c : tournee.customers)
            ttt.addCustomer(c);

        ttt.customers.remove(0);
        ttt.customers.remove(ttt.customers.size() - 1);
        for (int k = 0; k < TABOU_SIZE; k++) {
            for (int i = 1; i < ttt.getCustomers().size(); i++) {
                for (int j = i + 1; j < ttt.getCustomers().size(); j++) {
                    Tournee t = new Tournee(ttt.getVehicule());
                    t.customers = new ArrayList<>(ttt.getCustomers());
                    Customer s = t.customers.get(i);
                    t.customers.set(i, ttt.customers.get(j));
                    t.customers.set(j, s);
                    t.addDepotStartEnd();
                    if (Helper.isSolutionValid(t))
                        tournees.add(t);
                }
            }
            Collections.shuffle(ttt.customers);
        }
        return tournees;
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, "TabouThread");
            t.start();
        }
    }

    public interface TBListener {
        void hasTBDone(Solution solution);

        void onTBUpdate(float percentage);
    }

}
