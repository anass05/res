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
    static String algor, dataset;
    static Graphe g;

    public static void main(String[] args) {
        g = new Graphe();

        JFrame frame = new JFrame();
        MainInterface ui = new MainInterface(g);

        JPanel panel0;
        JComboBox combo0;
        JComboBox combo1;
        JTextField text0;
        JTextField text1;
        JButton but0;

        panel0 = new JPanel();
        GridBagLayout gbpanel0 = new GridBagLayout();
        GridBagConstraints gbcpanel0 = new GridBagConstraints();
        panel0.setLayout(gbpanel0);

        String[] datacombo0 = {"Choisir un algorithme", "Descente", "Tabou", "Recuit Simulé"};
        combo0 = new JComboBox(datacombo0);
        gbcpanel0.gridx = 2;
        gbcpanel0.gridy = 4;
        gbcpanel0.gridwidth = 4;
        gbcpanel0.gridheight = 2;
        gbcpanel0.fill = GridBagConstraints.BOTH;
        gbcpanel0.weightx = 1;
        gbcpanel0.weighty = 0;
        gbcpanel0.anchor = GridBagConstraints.NORTH;
        gbpanel0.setConstraints(combo0, gbcpanel0);
        panel0.add(combo0);
        combo0.addActionListener(e -> {
            JComboBox<String> combo = (JComboBox<String>) e.getSource();
            algor = (String) combo.getSelectedItem();
        });

        String[] datacombo1 = {"jeu de données", "small", "medium", "big"};
        combo1 = new JComboBox(datacombo1);
        gbcpanel0.gridx = 2;
        gbcpanel0.gridy = 6;
        gbcpanel0.gridwidth = 4;
        gbcpanel0.gridheight = 2;
        gbcpanel0.fill = GridBagConstraints.BOTH;
        gbcpanel0.weightx = 1;
        gbcpanel0.weighty = 0;
        gbcpanel0.anchor = GridBagConstraints.NORTH;
        gbpanel0.setConstraints(combo1, gbcpanel0);
        panel0.add(combo1);
        combo1.addActionListener(e -> {
            JComboBox<String> combo = (JComboBox<String>) e.getSource();
            dataset = (String) combo.getSelectedItem();
        });


        text0 = new JTextField();
        text0.setEnabled(false);
        gbcpanel0.gridx = 2;
        gbcpanel0.gridy = 2;
        gbcpanel0.gridwidth = 4;
        gbcpanel0.gridheight = 2;
        gbcpanel0.fill = GridBagConstraints.BOTH;
        gbcpanel0.weightx = 1;
        gbcpanel0.weighty = 0;
        gbcpanel0.anchor = GridBagConstraints.NORTH;
        gbpanel0.setConstraints(text0, gbcpanel0);
        panel0.add(text0);

        text1 = new JTextField();
        text1.setEnabled(false);
        gbcpanel0.gridx = 2;
        gbcpanel0.gridy = 0;
        gbcpanel0.gridwidth = 4;
        gbcpanel0.gridheight = 2;
        gbcpanel0.fill = GridBagConstraints.BOTH;
        gbcpanel0.weightx = 1;
        gbcpanel0.weighty = 0;
        gbcpanel0.anchor = GridBagConstraints.NORTH;
        gbpanel0.setConstraints(text1, gbcpanel0);
        panel0.add(text1);


        but0 = new JButton("Go!");
        gbcpanel0.gridx = 2;
        gbcpanel0.gridy = 8;
        gbcpanel0.gridwidth = 4;
        gbcpanel0.gridheight = 2;
        gbcpanel0.fill = GridBagConstraints.BOTH;
        gbcpanel0.weightx = 1;
        gbcpanel0.weighty = 0;
        gbcpanel0.anchor = GridBagConstraints.NORTH;
        gbpanel0.setConstraints(but0, gbcpanel0);
        panel0.add(but0);


        BorderLayout borderLayout = new BorderLayout();
        frame.setLayout(borderLayout);
        frame.add(ui, BorderLayout.CENTER);
//        frame.add(panel0, BorderLayout.EAST);


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setVisible(true);

        but0.addActionListener(e -> fun(ui));

        g.sommets = new DataLoader().readFile("medium.in");
        ui.updates(g);
//        g.arrange(ui);
//        new Descente(ui).calcule(g);
        new Tabou(ui).calcule(g);
//        new RecuitSimule(ui).calcule(g);
//        new Genetique(g,ui).calcule();
    }

    public static void fun(MainInterface ui) {
        if (dataset.equals("small"))
            g.sommets = new DataLoader().readFile("small.in");
        if (dataset.equals("medium"))
            g.sommets = new DataLoader().readFile("medium.in");
        if (dataset.equals("large"))
            g.sommets = new DataLoader().readFile("large.in");

        if (algor.equals("Descente"))
            new Descente(ui).calcule(g);
        if (algor.equals("Tabou"))
            new Tabou(ui).calcule(g);
        if (algor.equals("Recuit Simulé"))
            new RecuitSimule(ui).calcule(g);
    }


}
