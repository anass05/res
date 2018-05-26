package Algorithms;

import Objects.Solution;

/**
 * Created by Anass on 2018-05-24.
 */
public class RecuitSimuleThread implements Runnable {
    private Solution solutionInitial;
    public static int MAX_TEMPERTAURE = 1000;
    public static double DECRESING_VALUE = 0.1;
    private Thread t;
    private RSTListener rstListener;

    public RecuitSimuleThread(Solution solutionInitial, int max, double dec, RSTListener rstListener) {
        this.solutionInitial = new Solution(solutionInitial);
        MAX_TEMPERTAURE = max;
        DECRESING_VALUE = dec;
        this.rstListener = rstListener;
        System.out.println("v num : " + solutionInitial.getTournees().size());
    }

    public void run() {
//        1. Choisir une solution s dans S ainsi qu’une température
//           initiale T.
//        2. Tant qu’aucun critère d’arrêt n’est satisfait faire
//                  3. Choisir aléatoirement s’ dans N(s);
//                  4. Générer un nombre réel aléatoire r dans [0,1];
//                  5. Si r < p(T,s,s’) alors poser s := s’;
//                  6. Mettre à jour T;
//        7. Fin du tant que

        double temperature = MAX_TEMPERTAURE;
        Solution courante = new Solution(solutionInitial);
        Solution meilleur = new Solution(solutionInitial);
        double min = courante.coutTotal();

        while (temperature > 0) {

            rstListener.onRSUpdate((float) (1 - temperature / MAX_TEMPERTAURE));
            Solution s = generateRandomSolution();
            //5)
//            double r = 1 / ((double) new Random().nextInt(10) + 1);

            //6)
            double P = Math.exp((courante.coutTotal() - s.coutTotal()) / temperature);
            if (P > Math.random()) {
                courante = new Solution(s);
            }

            if (courante.coutTotal() < meilleur.coutTotal()) {
                min = courante.coutTotal();
                meilleur = new Solution(courante);
            }
            temperature -= DECRESING_VALUE;
        }
        //System.out.println("recuit min = " + min);
        try {
            rstListener.hasRSDone(meilleur);
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Solution generateRandomSolution() {
        return new Tabou(solutionInitial, 1).run();
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, "RecuitSimuléThread");
            t.start();
        }
    }

    public interface RSTListener {
        void hasRSDone(Solution solution);

        void onRSUpdate(float percentage);
    }
}
