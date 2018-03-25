package objects;

import UI.MainInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Anass on 2018-03-13.
 */
public class Graphe {
    public ArrayList<Sommet> sommets;
    public static ArrayList<ArrayList<Double>> matriceDistance;

    public Graphe() {
        sommets = new ArrayList<>();
    }

    @Override
    public String toString() {
        String s = "";
        for (Sommet a : sommets) {
            s = s.concat(a + " ");
        }
//        s = s.concat(sommets.get(0) + " ");
        return s;
    }

    public double cout() {
        double cout = 0;
        for (int i = 0; i < sommets.size() - 1; i++) {
            int index = Integer.parseInt(sommets.get(i).toString().split("s")[1]);
            int indexNext = Integer.parseInt(sommets.get(i+1).toString().split("s")[1]);
            cout += matriceDistance.get(index).get(indexNext);
        }
        int index = Integer.parseInt(sommets.get(0).toString().split("s")[1]);
        int indexNext = Integer.parseInt(sommets.get(sommets.size() - 1).toString().split("s")[1]);
        cout += matriceDistance.get(index).get(indexNext);
        return cout;
    }

    @Override
    public boolean equals(Object obj) {
        for (int i = 0; i < sommets.size(); i++) {
            if (!((Graphe) obj).sommets.get(i).equals(sommets.get(i)))
                return false;
        }
        return true;
    }


    public void shuffle() {
        Collections.shuffle(sommets);
//        int done = sommets.size()/2;
//        while (done > 0) {
//            Random r = new Random();
//            int n1, n2;
//            do {
//                n1 = r.nextInt(sommets.size());
//                n2 = r.nextInt(sommets.size());
//            } while (n1 == n2);
//            Sommet s = sommets.get(n1);
//            sommets.set(n1, sommets.get(n2));
//            sommets.set(n2, s);
//            done--;
//        }
    }

    public double fitness(){
        return 1/cout()*25*sommets.size();
    }
}

