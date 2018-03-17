package src;

import UI.MainInterface;
import algorithmes.Descente;
import algorithmes.Genetique;
import algorithmes.RecuitSimule;
import algorithmes.Tabou;
import objects.Graphe;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Anass on 2018-03-13.
 */
public class Main {
    static Graphe g;

    public static void main(String[] args) {
        g = new Graphe();

        JFrame frame = new JFrame();
        MainInterface ui = new MainInterface(g);

        frame.add(ui, BorderLayout.CENTER);


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setVisible(true);

        g.sommets = new DataLoader().readFile("medium.in");
        ui.updates(g);
        ui.startTimer();
//        new Descente(ui).calcule(g);
        new Tabou(ui).calcule(g);
//        new RecuitSimule(ui).calcule(g);
//        new Genetique(g,ui).calcule();
    }


}
