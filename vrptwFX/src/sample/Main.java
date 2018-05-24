package sample;

import Algorithms.T5rbi9a;
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

public class Main extends Application {

    public static ArrayList<Customer> customers;
    public static ArrayList<Customer> TL, TR, BL, BR;
    public static Solution solution;
    Group root;

    public static void main(String[] args) throws Exception {
        customers = new FileIO().readFile("100.in");
        solution = new Solution();
        T5rbi9a t5 = new T5rbi9a(customers);
        solution = t5.runSmall();
        TL = t5.TL;
        TR = t5.TR;
        BL = t5.BL;
        BR = t5.BR;
        launch(args);

    }

    public Main() {
        root = new Group();
    }

    @Override
    public void start(Stage stage) throws Exception {
/*
        for (Customer c : customers) {
            Circle circle = new Circle();
            circle.setCenterX(c.getX() * 5 + 10);
            circle.setCenterY(c.getY() * 5 + 10);
            circle.setRadius(3.0f);
            root.getChildren().add(circle);
        }
*/
        drawSolution();
        Circle circle = new Circle();
        circle.setCenterX(FileIO.depot.getX() * 5 + 10);
        circle.setCenterY(FileIO.depot.getY() * 5 + 10);
        circle.setFill(Color.RED);
        circle.setRadius(6.0f);
        root.getChildren().add(circle);

        drawCircles(TL);
        drawCircles(BL);
        drawCircles(TR);
        drawCircles(BR);


        Scene scene = new Scene(root, 500, 500);
        stage.setTitle("Vehicle routing with time windows");
        stage.setScene(scene);
        stage.show();

    }

    public void drawSolution() {
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

    public void drawCircles(ArrayList<Customer> customersx) {
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
