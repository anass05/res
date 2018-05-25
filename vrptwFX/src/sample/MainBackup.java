package sample;

import Objects.Customer;
import Objects.Solution;
import Objects.Tournee;
import Utils.FileIO;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class MainBackup extends Application {

    public static ArrayList<Customer> customers;
    public static Solution solution;
    //Group root;

    public static void main(String[] args) throws Exception {
        /*Sketch rf = new Sketch();
        javafx.application.Application.launch(Sketch.class);*/

/*
        customers = new FileIO().readFile("rc208.in");
        solution = new Solution();
        SolutionInitial solutionInitial = new SolutionInitial(customers);
        solution = solutionInitial.runSmall();
        System.out.println(solution.coutTotal());
        System.out.println(solution.getTournees().size());
        new Descente(solution).run();*/
        /*for (Tournee t : solution.getTournees())
            System.out.println(t.getCustomers().size());*/
        launch(args);



    }


    public MainBackup() {
        //solution = new Descente(solution).run();
     //   initialise();
    }

    @Override
    public void start(Stage stage) throws Exception {
        //new Sketch();
    }

    public void initialise() {
        Group root = new Group();
        Stage stage = new Stage();
        drawSolution(root);

        for (Customer c : customers) {
            Circle circle = new Circle();
            circle.setCenterX(c.getX() * 5 + 10);
            circle.setCenterY(c.getY() * 5 + 10);
            circle.setRadius(3.0f);
            root.getChildren().add(circle);
        }


        Circle circle = new Circle();
        circle.setCenterX(FileIO.depot.getX() * 5 + 10);
        circle.setCenterY(FileIO.depot.getY() * 5 + 10);
        circle.setFill(Color.RED);
        circle.setRadius(6.0f);
        root.getChildren().add(circle);


        Scene scene = new Scene(root, 500, 500);
        stage.setTitle("Vehicle routing with time windows");
        stage.setScene(scene);
        stage.show();

    }

    public void drawSolution(Group root) {
        int multiplier = 5;
        int ind = 0;
        Color cc;
        if (solution != null) {
            for (Tournee tournee : solution.getTournees()) {
                Random random = new Random();
                int r = random.nextInt(255);
                int g = random.nextInt(255);
                int b = random.nextInt(255);
                cc = Color.rgb(r, g, b);
                for (int i = 0; i < tournee.getCustomers().size() - 1; i++) {
                    Customer s = tournee.getCustomers().get(i);
                    Customer e = tournee.getCustomers().get(i + 1);
                    Line line = new Line();
                    line.setStartX(s.getX() * multiplier + 10);
                    line.setStartY(s.getY() * multiplier + 10);
                    line.setEndX(e.getX() * multiplier + 10);
                    line.setEndY(e.getY() * multiplier + 10);
                    line.setStroke(cc);
                    root.getChildren().add(line);
                }
                Customer e = tournee.getCustomers().get(0);
                Customer s = tournee.getCustomers().get(tournee.getCustomers().size() - 1);
                Line line = new Line();
                line.setStartX(s.getX() * multiplier + 10);
                line.setStartY(s.getY() * multiplier + 10);
                line.setEndX(e.getX() * multiplier + 10);
                line.setEndY(e.getY() * multiplier + 10);
                line.setStroke(cc);
                root.getChildren().add(line);
                ind++;
            }
        }
    }

    public void drawCircles(ArrayList<Customer> customersx, Group root) {
        Color cc;
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        cc = Color.rgb(r, g, b);


        for (Customer c : customersx) {
            Circle circle = new Circle();
            circle.setCenterX(c.getX() * 5 + 10);
            circle.setCenterY(c.getY() * 5 + 10);
            circle.setRadius(3.0f);
            circle.setFill(cc);
            root.getChildren().add(circle);
        }

    /*    Circle circle = new Circle();
        circle.setCenterX(FileIO.depot.getX() * 5 + 10);
        circle.setCenterY(FileIO.depot.getY() * 5 + 10);
        circle.setFill(Color.RED);
        circle.setRadius(6.0f);
        root.getChildren().add(circle);*/

    }

}
