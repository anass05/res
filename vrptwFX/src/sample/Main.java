package sample;


import Algorithms.Descente;
import Algorithms.RecuitSimule;
import Algorithms.SolutionInitial;
import Algorithms.Tabou;
import Objects.Customer;
import Objects.Solution;
import Utils.FileIO;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {


    public static void main(String[] args) throws Exception {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        ArrayList<Customer> customers = new FileIO().readFile("100b.in");
        SolutionInitial solutionInitial = new SolutionInitial(customers);
        Solution solution = solutionInitial.runSmall();
        new Sketch(customers,solution,"Vehicle routing with time windows before");
//        Solution solution1 = new Descente(solution).run();
//        Solution solution1 = new Tabou(solution,50000).run();
        Solution solution1 = new RecuitSimule(solution).run();
        System.out.println("before desc "+solution.coutTotal());
        System.out.println("after desc "+solution1.coutTotal());
        new Sketch(customers,solution1,"Vehicle routing with time windows after");
        //Solution solution
    }
}
