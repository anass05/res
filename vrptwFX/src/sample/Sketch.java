package sample;

import Objects.Customer;
import Objects.Solution;
import Objects.Tournee;
import Utils.FileIO;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Sketch extends Application {

    private ArrayList<Customer> customers;
    private Solution solution;
    private String title;
    //Group root;


    public Sketch(ArrayList<Customer> customers, Solution solution, String title) {
        this.customers = customers;
        this.title = title;
        this.solution = new Solution(solution);
        initialise();
    }

    @Override
    public void start(Stage stage) throws Exception {
        initialise();
    }

    public void initialise() {
        Group root = new Group();
        Stage stage = new Stage();
        BorderPane borderPane = new BorderPane();
        Pane pane = new Pane();

        drawSolution(pane);
        drawDetails(borderPane);

        for (Customer c : customers) {
            Circle circle = new Circle();
            circle.setCenterX(c.getX() * 5 + 10);
            circle.setCenterY(c.getY() * 5 + 10);
            circle.setRadius(3.0f);
            pane.getChildren().add(circle);
        }


        Circle circle = new Circle();
        circle.setCenterX(FileIO.depot.getX() * 5 + 10);
        circle.setCenterY(FileIO.depot.getY() * 5 + 10);
        circle.setFill(Color.RED);
        circle.setRadius(6.0f);
        pane.getChildren().add(circle);

        borderPane.setCenter(pane);
        root.getChildren().add(borderPane);

        Scene scene = new Scene(root, 850, 500);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();

    }

    public void drawSolution(Pane root) {
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

    public void drawDetails(BorderPane borderPane) {
        GridPane gridPane = new GridPane();

        //Setting the padding
        gridPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 50));

        //Setting the vertical and horizontal gaps between the columns
        gridPane.setVgap(20);
        gridPane.setHgap(20);

        //Setting the Grid alignment
        gridPane.setAlignment(Pos.CENTER);

        Label l00 = new Label("Nombre de vehicules: ");
        l00.setFont(Font.font(18));
        Label l01 = new Label(solution.tournees.size() + "");
        l01.setFont(Font.font(18));
        Label l10 = new Label("Distance parcourue: ");
        l10.setFont(Font.font(18));
        String ss = solution.coutTotal() + "";
        if (ss.length() > 6)
            ss = ss.substring(0, 6);
        Label l11 = new Label(ss);
        l11.setFont(Font.font(18));
        Label l20 = new Label("Nombre de clients: ");
        l20.setFont(Font.font(18));
        Label l21 = new Label(customers.size() + "");
        l21.setFont(Font.font(18));
        Button details = new Button("Plus de détails");

        details.setOnAction(event -> new SketchTournee(customers, solution, "Résultats détaillés"));

        gridPane.add(l00, 0, 0);
        gridPane.add(l01, 1, 0);
        gridPane.add(l10, 0, 1);
        gridPane.add(l11, 1, 1);
        gridPane.add(l20, 0, 2);
        gridPane.add(l21, 1, 2);
        gridPane.add(details, 1, 3);

        borderPane.setRight(gridPane);
    }


}
