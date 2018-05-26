package sample;


import Algorithms.Descente;
import Algorithms.RecuitSimuleThread;
import Algorithms.SolutionInitial;
import Algorithms.TabouThread;
import Objects.Customer;
import Objects.Solution;
import Utils.FileIO;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class Main extends Application implements RecuitSimuleThread.RSTListener, TabouThread.TBListener {

    private ArrayList<Customer> customers;
    private ProgressBar rsProgress, tabProgress, desprogress;
    private Button rsRes, rsAff, tabouAff, tabouRes, resulSol, visuSol, desAff, desRes, demarrer;
    private Solution solution, solutionDes, solutionTab, solutionRC;
    private SolutionInitial solutionInitial;
    private TextField tabouMax, rsDec, rsMax;

    public static void main(String[] args) throws Exception {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        draw(stage);
        /*customers = new FileIO().readFile("c101.txt");
        SolutionInitial solutionInitial = new SolutionInitial(customers);
        Solution solution = solutionInitial.runSmall();
        new SketchTournee(customers, solution, "Vehicle routing with time windows before");*/
//        Solution solution1 = new Descente(solution).run();
//        Solution solution1 = new Tabou(solution,50000).run();
        //tabProgress = new ProgressBar(0);
//        TabouThread th = new TabouThread(solution, 50000, this);
        // th.start();
//        RecuitSimuleThread rt = new RecuitSimuleThread(solution, this);
//        rt.start();
        //Solution solution1 = rt.getMeilleur();
//        System.out.println("before desc " + solution.coutTotal());
        //System.out.println("after desc "+solution1.coutTotal());
        //new Sketch(customers,solution1,"Vehicle routing with time windows after");
        //Solution solution
    }

    @Override
    public void hasRSDone(Solution solution) {
        //showSketch(solution, "Recuit Simulé");
        solutionRC = new Solution(solution);
        Platform.runLater(() -> {
            rsRes.setDisable(false);
            rsAff.setDisable(false);
        });
    }

    @Override
    public void onRSUpdate(float percentage) {
        System.out.println(percentage);
        Platform.runLater(() -> rsProgress.setProgress(percentage));
    }


    @Override
    public void hasTBDone(Solution solution) {
        //showSketch(solution, "Tabou");
        solutionTab = new Solution(solution);
        Platform.runLater(() -> {
            tabProgress.setProgress(1);
            tabouAff.setDisable(false);
            tabouRes.setDisable(false);
        });
    }

    @Override
    public void onTBUpdate(float percentage) {
        System.out.println(percentage);
        Platform.runLater(() -> {
            tabProgress.setProgress(percentage);
        });
    }

    public void showSketch(Solution solution, String message) {
        System.out.println("after algo " + solution.coutTotal());
        Platform.runLater(() -> new Sketch(customers, solution, message));
    }

    public void draw(Stage primaryStage) {
        //Creating a Grid Pane
        VBox root = new VBox();
        GridPane gridPane = new GridPane();

        //Setting size for the pane
        gridPane.setMinSize(900, 300);
        gridPane.setPrefWidth(900);

        //Setting the padding
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        //Setting the vertical and horizontal gaps between the columns
        gridPane.setVgap(20);
        gridPane.setHgap(20);

        //Setting the Grid alignment
        gridPane.setAlignment(Pos.CENTER);


        Label lbl = new Label("Jeu de données");
        lbl.setFont(Font.font(18));
        gridPane.add(lbl, 0, 0);

        ComboBox<String> dataSetsCombo = new ComboBox<>();
        dataSetsCombo.getItems().addAll(loadFiles());
        dataSetsCombo.setEditable(false);
        dataSetsCombo.setPrefWidth(100);
        gridPane.add(dataSetsCombo, 5, 0);
        dataSetsCombo.valueProperty().addListener((ov, t, t1) -> {
            customers = new FileIO().readFile(t1);
            solutionInitial = new SolutionInitial(customers);
            resulSol.setDisable(false);
            solution = solutionInitial.runSmall();
            visuSol.setDisable(false);
            demarrer.setDisable(false);

        });
        /**
         *Solution initial
         * */
        Label lbl2 = new Label("Solution Initiale");
        lbl2.setFont(Font.font(18));
        gridPane.add(lbl2, 0, 1);

        resulSol = new Button("Résultats");
        visuSol = new Button("Visualiser");

        resulSol.setPrefWidth(100);
        visuSol.setPrefWidth(100);
        visuSol.setDisable(true);
        resulSol.setDisable(true);

        resulSol.setOnAction(event -> {

        });
        visuSol.setOnAction(event ->
                new Sketch(customers, solution, "Visualisation de la solution initial")
        );
        resulSol.setOnAction(event -> {
            new SketchTournee(customers, solution, "Résultats détaillés de la Solution initial");
        });
        gridPane.add(resulSol, 5, 1);
        gridPane.add(visuSol, 4, 1);
        /**
         *Descente
         * */

        Label lbdec = new Label("Méthode Déscente");
        lbdec.setFont(Font.font(18));

        desprogress = new ProgressBar(0);

        desAff = new Button("Visualiser");
        desRes = new Button("Résultats");

        desAff.setDisable(true);
        desRes.setDisable(true);
        desAff.setPrefWidth(100);
        desRes.setPrefWidth(100);
        desAff.setOnAction(event -> {
            showSketch(solutionDes, "Résultats de la méthode Déscente");
        });
        desRes.setOnAction(event -> {
            new SketchTournee(customers, solutionDes, "Résultats détaillés de la methode descente");
        });


        gridPane.add(lbdec, 0, 2);
        gridPane.add(desprogress, 1, 2);
        gridPane.add(desAff, 4, 2);
        gridPane.add(desRes, 5, 2);


        /**
         * Tabou
         * */

        Label lbTabou = new Label("Méthode Tabou");
        lbTabou.setFont(Font.font(18));

        tabProgress = new ProgressBar(0);

        tabouMax = new TextField();
        tabouMax.setPromptText("TABOU MAX");
        tabouMax.setText("" + 5000);

        tabouAff = new Button("Visualiser");
        tabouRes = new Button("Résultats");
        tabouAff.setPrefWidth(100);
        tabouRes.setPrefWidth(100);
        tabouAff.setDisable(true);
        tabouRes.setDisable(true);
        tabouAff.setOnAction(event -> showSketch(solutionTab, "Résultats de la méthode Tabou"));
        tabouRes.setOnAction(event -> {
            new SketchTournee(customers, solutionTab, "Résultats détaillés de la methode Tabout");
        });


        gridPane.add(lbTabou, 0, 3);
        gridPane.add(tabProgress, 1, 3);
        gridPane.add(tabouMax, 2, 3);
        gridPane.add(tabouAff, 4, 3);
        gridPane.add(tabouRes, 5, 3);

        /**
         * Recuit Simulé
         * */

        Label lbRS = new Label("Recuit Simulé");
        lbRS.setFont(Font.font(18));

        rsProgress = new ProgressBar(0);

        rsMax = new TextField();
        rsMax.setPromptText("MAX TEMP");
        rsMax.setText("" + 10000);

        rsDec = new TextField();
        rsDec.setPromptText("DECREASING VAL");
        rsDec.setText("" + 0.1);


        rsAff = new Button("Visualiser");
        rsRes = new Button("Résultats");
        rsAff.setPrefWidth(100);
        rsRes.setPrefWidth(100);
        rsAff.setDisable(true);
        rsRes.setDisable(true);

        rsAff.setOnAction(event -> {
            showSketch(solutionRC, "Résultats de la méthode Recuit Simulé");
        });
        rsRes.setOnAction(event -> {
            new SketchTournee(customers, solutionRC, "Résultats détaillés de la methode Recuit Simulé");
        });

        gridPane.add(lbRS, 0, 4);
        gridPane.add(rsProgress, 1, 4);
        gridPane.add(rsMax, 2, 4);
        gridPane.add(rsDec, 3, 4);
        gridPane.add(rsAff, 4, 4);
        gridPane.add(rsRes, 5, 4);

        /**
         * Algorithme Génétique
         * */

        Label lbG = new Label("Algorithme Génétique");
        lbG.setFont(Font.font(18));

        ProgressBar gProgress = new ProgressBar(0);

        TextField gMax = new TextField();
        gMax.setPromptText("MAX GEN");
        gMax.setText("" + 100);

        TextField gPop = new TextField();
        gPop.setPromptText("POPULATION");
        gPop.setText("" + 0.1);


        Button genAff = new Button("Visualiser");
        Button genRes = new Button("Résultats");
        genAff.setPrefWidth(100);
        genRes.setPrefWidth(100);


        gridPane.add(lbG, 0, 5);
        gridPane.add(gProgress, 1, 5);
        gridPane.add(gMax, 2, 5);
        gridPane.add(gPop, 3, 5);
        gridPane.add(genAff, 4, 5);
        gridPane.add(genRes, 5, 5);

        genAff.setDisable(true);
        genRes.setDisable(true);
        gPop.setDisable(true);
        lbG.setDisable(true);
        gMax.setDisable(true);
        gPop.setDisable(true);

        demarrer = new Button("Demarrer");
        Button quitter = new Button("Quitter");
        demarrer.setPrefWidth(100);
        quitter.setPrefWidth(100);
        demarrer.setDisable(true);
        demarrer.setOnAction(event -> {
            solutionDes = new Descente(solution).run();
            desprogress.setProgress(1);
            desAff.setDisable(false);
            desRes.setDisable(false);
            RecuitSimuleThread rt = new RecuitSimuleThread(solution, Integer.parseInt(rsMax.getText()), Double.parseDouble(rsDec.getText()), this);
            rt.start();
            TabouThread th = new TabouThread(solution, Integer.parseInt(tabouMax.getText()), this);
            th.start();
        });

        quitter.setOnAction(event -> {
            Platform.exit();
            System.exit(0);
        });
        gridPane.add(demarrer, 4, 6);
        gridPane.add(quitter, 5, 6);

        //Arranging all the nodes in the grid

        gridPane.prefWidthProperty().bind(root.widthProperty());
        root.setFillWidth(true);
        root.getChildren().addAll(gridPane);
        // Adding VBox to the scene
        Scene scene = new Scene(root, 900, 330);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public String[] loadFiles() {
        File folder = new File("file_in");
        File[] listOfFiles = folder.listFiles();
        String[] listOfFilesNames = new String[listOfFiles.length];

        for (int i = 0; i < listOfFiles.length; i++) {
            listOfFilesNames[i] = listOfFiles[i].getName();
        }
        return listOfFilesNames;
    }

}
