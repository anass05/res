package Presentation;

import Beans.Module;

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

    public Dashboard(ArrayList<Module> modules,Controleur controleur) {
        this.modules = modules;
        this.controleur = controleur;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Dashboard");
        frame.setBounds(100, 100, 526, 392);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

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
        frame.setVisible(true);
    }

}
