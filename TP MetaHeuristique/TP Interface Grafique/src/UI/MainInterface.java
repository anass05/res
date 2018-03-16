package UI;

import objects.Graphe;
import objects.Sommet;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Anass on 2018-03-15.
 */
public class MainInterface extends JPanel {
    private Graphe graphe;
    private Graphe dots;
    private String[] messages;

    public MainInterface(Graphe graphe) {
        this.graphe = new Graphe();
        this.dots = new Graphe();
        this.graphe.sommets = new ArrayList<>(graphe.sommets);
        this.dots.sommets = new ArrayList<>(graphe.sommets);
        messages = new String[5];
    }

    public void updates(Graphe graphe) {
        this.graphe.sommets = new ArrayList<>(graphe.sommets);
        this.dots.sommets = new ArrayList<>(graphe.sommets);
        repaint();
    }

    public void updates(ArrayList<Sommet> sommets) {
        this.graphe.sommets = new ArrayList<>(sommets);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.white);
        if (graphe.sommets.size() > 1) {
            //s.getX() * 600/MAX_X
            int multiplier = 5;
            g.setColor(new Color(90, 90, 90));
            for (int i = 0; i < graphe.sommets.size() - 1; i++) {
                Sommet s = graphe.sommets.get(i);
                Sommet e = graphe.sommets.get(i + 1);
                g.drawLine(s.getX() * multiplier, s.getY() * multiplier, e.getX() * multiplier, e.getY() * multiplier);
            }
            Sommet e = graphe.sommets.get(0);
            Sommet s = graphe.sommets.get(graphe.sommets.size() - 1);
            //g.drawLine(s.getX() * multiplier, s.getY() * multiplier, e.getX() * multiplier, e.getY() * multiplier);
        }

        if (dots.sommets.size() > 0) {
            //s.getX() * 600/MAX_X
            int multiplier = 5;
            int radius = 8;
            for (int i = 0; i < dots.sommets.size() - 1; i++) {
                Sommet s = dots.sommets.get(i);
                g.setColor(new Color(190, 190, 190));
                g.fillOval(s.getX() * multiplier - radius, s.getY() * multiplier - radius, radius * 2, radius * 2);
                g.setColor(new Color(90, 90, 90));
                g.drawOval(s.getX() * multiplier - radius, s.getY() * multiplier - radius, radius * 2, radius * 2);
            }
            Sommet s = dots.sommets.get(graphe.sommets.size() - 1);
            g.setColor(new Color(190, 190, 190));
            g.fillOval(s.getX() * multiplier - radius, s.getY() * multiplier - radius, radius * 2, radius * 2);
            g.setColor(new Color(90, 90, 90));
            g.drawOval(s.getX() * multiplier - radius, s.getY() * multiplier - radius, radius * 2, radius * 2);
        }
        try {
            for (int i = 0; i < 5; i++) {
                if (messages[i] != null)
                    g.drawString(messages[i], 10, 20 * (i + 1));
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }

    }

    public void updated(String[] message) {
        this.messages = message;
    }
}
