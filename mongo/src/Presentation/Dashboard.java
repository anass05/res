package Presentation;

import Beans.Module;
import Beans.Personne;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Dashboard {

    private JFrame frame;
    private ArrayList<Module> modules;
    private Controleur controleur;
    private Personne p;

    public Dashboard(ArrayList<Module> modules, Controleur controleur, Personne p) {
        this.modules = modules;
        this.controleur = controleur;
        this.p = p;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Dashboard");
        frame.setBounds(100, 100, 526, 392);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        frame.getContentPane().add(panel);
        panel.setLayout(new GridLayout(2, 2, 20, 20));

        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).getPlan() != null) {
                ModuleElement moduleElement = new ModuleElement(modules.get(i));
                panel.add(moduleElement);
                int finalI1 = i;
                moduleElement.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        controleur.ouvrirSuiviPlanning(modules.get(finalI1));
                    }
                });
            }else{
                ModuleElementNoPlanning moduleElement_3 = new ModuleElementNoPlanning(modules.get(i));
                panel.add(moduleElement_3 );
                int finalI = i;
                moduleElement_3 .addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        controleur.ouvrirDefinirPlanning(modules.get(finalI));
                    }
                });
            }
        }

        JPanel navigationPanel = new JPanel();
        navigationPanel.setBorder(new EmptyBorder(10, 20, 0, 15));
        frame.getContentPane().add(navigationPanel, BorderLayout.NORTH);
        navigationPanel.setLayout(new GridLayout(1, 2, 10, 10));

        JLabel lblNom = new JLabel(p.getNom()+" "+p.getPrenom());
        lblNom.setHorizontalAlignment(SwingConstants.LEFT);
        navigationPanel.add(lblNom);

        JPanel panel_1 = new JPanel();
        navigationPanel.add(panel_1);
        panel_1.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

        JButton btnDeconnexion = new JButton("deconnexion");
        btnDeconnexion.setHorizontalAlignment(SwingConstants.RIGHT);
        panel_1.add(btnDeconnexion);

        frame.setVisible(true);
    }

}
