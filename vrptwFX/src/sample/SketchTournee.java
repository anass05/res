package sample;

import Objects.Customer;
import Objects.Solution;
import Objects.Tournee;
import Utils.FileIO;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class SketchTournee extends Application {

    private ArrayList<Customer> customers;
    private Solution solution;
    private String title;
    private Tournee chosenTournee;
    //Group root;


    public SketchTournee(ArrayList<Customer> customers, Solution solution, String title) {
        this.customers = customers;
        this.title = title;
        this.solution = new Solution(solution);
        this.chosenTournee = solution.getTournees().get(0);
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
        drawDetails(borderPane, pane);
        drawCustomers(pane);


        borderPane.setCenter(pane);
        root.getChildren().add(borderPane);

        Scene scene = new Scene(root, 950, 500);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();

    }

    public void drawCustomers(Pane pane) {
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
    }

    public void drawSolution(Pane root) {
        root.getChildren().clear();
        drawCustomers(root);
        int multiplier = 5;
        int ind = 0;
        Color cc = Color.DARKRED;
        if (solution != null) {
            //for (Tournee tournee : solution.getTournees()) {
            Tournee tournee = chosenTournee;
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
        //}
    }


    public void drawDetails(BorderPane borderPane, Pane pane) {
        BorderPane rigthBorderPane = new BorderPane();

        GridPane gridPane = new GridPane();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);

        //Setting the padding
        gridPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 50));

        //Setting the vertical and horizontal gaps between the columns
        gridPane.setVgap(20);
        gridPane.setHgap(20);

        //Setting the Grid alignment
        gridPane.setAlignment(Pos.CENTER);

        ComboBox<String> tourneesCombo = new ComboBox<>();
        String[] tournees = new String[solution.tournees.size()];
        for (int i = 0; i < solution.tournees.size(); i++) {
            tournees[i] = "Tournée " + i;
        }

        tourneesCombo.valueProperty().addListener((ov, t, t1) -> {
            int index = Integer.valueOf(t1.replace("Tournée ", ""));
            chosenTournee = solution.tournees.get(index);
            tourneeChosed(chosenTournee, gridPane);
            drawSolution(pane);

        });

        tourneesCombo.getItems().addAll(tournees);
        //gridPane.add(tourneesCombo, 2, 0);

        tourneeChosed(solution.tournees.get(0), gridPane);

        rigthBorderPane.setCenter(gridPane);
        rigthBorderPane.setTop(tourneesCombo);
        borderPane.setRight(rigthBorderPane);
    }

    public void tourneeChosed(Tournee tournee, GridPane gridPane) {
        gridPane.getChildren().clear();
        gridPane.setAlignment(Pos.BASELINE_CENTER);
        Label clientId = new Label("ID Cient");
        Label openTime = new Label("Temps de début");
        Label closeTime = new Label("Temps d'échéance");
        gridPane.add(clientId, 0, 1);
        gridPane.add(openTime, 2, 1);
        gridPane.add(closeTime, 3, 1);
        for (int i = 0; i < tournee.customers.size() - 1; i++) {
            Customer c = tournee.customers.get(i);

            Label clientIdx = new Label(c.getId() + "");
            Label openTimxe = new Label(c.getStartTime() + "");
            Label closeTimex = new Label(c.getDueTime() + "");
            gridPane.add(clientIdx, 0, i + 2);
            gridPane.add(openTimxe, 2, i + 2);
            gridPane.add(closeTimex, 3, i + 2);
        }
    }


}
