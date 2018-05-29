package Presentation;

import Beans.ChargeHoraire;
import Beans.Module;

import javax.swing.*;
import java.awt.*;

public class EditerChargeHoraire {

    private JFrame frame;
    private JSlider cours_slider;
    private JSlider duree_slider;
    private JSlider td_slider;
    private JSlider tp_slider;
    private JSlider projet_slider;
    private JLabel duree_cours;
    private JLabel duree_td;
    private JLabel duree_tp;
    private JLabel duree_projet;
    private JLabel duree_totale;
    private int max_charge_horaire;
    private Module module;
    private Controleur controleur;
    private double cours_updated;
    private double td_updated;
    private double tp_updated;
    private double p_updated;


    public EditerChargeHoraire(Module module, Controleur controleur) {
        this.module = module;
        this.controleur = controleur;
        max_charge_horaire = calculateTotal();
        initialize();
        installListeners();

    }


    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 405, 252);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel cours_holder = new JPanel();
        cours_holder.setLayout(null);
        frame.getContentPane().add(cours_holder);

        JLabel cours = new JLabel("Cours:");
        cours.setHorizontalAlignment(SwingConstants.LEFT);
        cours.setBounds(22, 11, 73, 14);
        cours_holder.add(cours);

        cours_slider = new JSlider();
        cours_slider.setValue((int) (((double) module.getPrevue().getNhC()) / 1.5) * 100 / calculateTotal());
        cours_slider.setBounds(105, 5, 200, 26);
        cours_holder.add(cours_slider);

        duree_cours = new JLabel("15h");
        duree_cours.setBounds(330, 11, 30, 14);
        cours_holder.add(duree_cours);

        JPanel td_holder = new JPanel();
        td_holder.setLayout(null);
        frame.getContentPane().add(td_holder);

        JLabel td = new JLabel("TD");
        td.setHorizontalAlignment(SwingConstants.LEFT);
        td.setBounds(22, 11, 73, 14);
        td_holder.add(td);

        td_slider = new JSlider();
        td_slider.setValue((int) (module.getPrevue().getNhTD()) * 100 / calculateTotal());
        td_slider.setBounds(105, 5, 200, 26);
        td_holder.add(td_slider);

        duree_td = new JLabel();
        duree_td.setBounds(330, 11, 30, 14);
        td_holder.add(duree_td);

        JPanel tp_holder = new JPanel();
        tp_holder.setLayout(null);
        frame.getContentPane().add(tp_holder);

        JLabel tp = new JLabel("TP:");
        tp.setHorizontalAlignment(SwingConstants.LEFT);
        tp.setBounds(22, 11, 73, 14);
        tp_holder.add(tp);

        tp_slider = new JSlider();
        tp_slider.setValue((int) (module.getPrevue().getNhTP() / .75) * 100 / calculateTotal());
        tp_slider.setBounds(105, 5, 200, 26);
        tp_holder.add(tp_slider);

        duree_tp = new JLabel();
        duree_tp.setBounds(330, 11, 30, 14);
        tp_holder.add(duree_tp);

        JPanel projet_holder = new JPanel();
        projet_holder.setLayout(null);
        frame.getContentPane().add(projet_holder);

        JLabel projet = new JLabel("Projet:");
        projet.setHorizontalAlignment(SwingConstants.LEFT);
        projet.setBounds(22, 11, 73, 14);
        projet_holder.add(projet);

        projet_slider = new JSlider();
        projet_slider.setForeground(Color.GRAY);
        projet_slider.setValue((int) (module.getPrevue().getNhP() / 1.5) * 100 / calculateTotal());
        projet_slider.setBounds(105, 5, 200, 26);
        projet_holder.add(projet_slider);

        duree_projet = new JLabel();
        duree_projet.setBounds(330, 11, 30, 14);
        projet_holder.add(duree_projet);

        JPanel duree_totale_holder = new JPanel();
        frame.getContentPane().add(duree_totale_holder);
        duree_totale_holder.setLayout(null);

        JLabel totale = new JLabel("Dur\u00E9e totale:");
        totale.setEnabled(false);
        totale.setBounds(22, 11, 73, 14);
        totale.setHorizontalAlignment(SwingConstants.LEFT);
        duree_totale_holder.add(totale);

        duree_slider = new JSlider();
        duree_slider.setValue(100);
        duree_slider.setEnabled(false);
        duree_slider.setBounds(105, 5, 200, 26);
        duree_totale_holder.add(duree_slider);

        duree_totale = new JLabel(calculate_current_durre() + "h/" + max_charge_horaire + "h");
        duree_totale.setEnabled(false);
        duree_totale.setBounds(330, 11, 73, 14);
        duree_totale_holder.add(duree_totale);

        JPanel buttons_holder = new JPanel();
        frame.getContentPane().add(buttons_holder);
        buttons_holder.setLayout(null);

        JButton valider = new JButton("Valider");
        valider.setBounds(290, 11, 82, 23);
        buttons_holder.add(valider);

        JButton annuler = new JButton("Annuler");
        annuler.setBounds(201, 11, 79, 23);
        buttons_holder.add(annuler);

        annuler.addActionListener(e -> frame.dispose());
        valider.addActionListener(e -> updateChargeHoraire());

        duree_cours.setText((int) (module.getPrevue().getNhC() / 1.5) + "h");
        duree_td.setText((int) (module.getPrevue().getNhTD()) + "h");
        duree_tp.setText((int) (module.getPrevue().getNhTP() / .75) + "h");
        duree_projet.setText((int) (module.getPrevue().getNhP() / 1.5) + "h");


        cours_updated = module.getPrevue().getNhC() / 1.5;
        td_updated = module.getPrevue().getNhTD();
        tp_updated = module.getPrevue().getNhTP() / .75;
        p_updated = module.getPrevue().getNhP();

        frame.setVisible(true);
    }

    public void installListeners() {
        cours_slider.addChangeListener(e -> {
            // TODO Auto-generated method stub
            duree_slider.setValue(total_slider_duree());
            duree_cours.setText((int) (((double) (cours_slider.getValue() * max_charge_horaire)) / 100 / 1.5) + "h");
            duree_totale.setText(calculate_current_durre() + "h/" + max_charge_horaire + "h");
            cours_updated = ((double) (cours_slider.getValue() * max_charge_horaire)) / 100;
        });
        td_slider.addChangeListener(e -> {
            // TODO Auto-generated method stub
            duree_slider.setValue(total_slider_duree());
            duree_td.setText((int) ((td_slider.getValue() * max_charge_horaire) / 100) + "h");
            duree_totale.setText(calculate_current_durre() + "h/" + max_charge_horaire + "h");
            td_updated = (td_slider.getValue() * max_charge_horaire) / 100;
        });
        tp_slider.addChangeListener(e -> {
            // TODO Auto-generated method stub
            duree_slider.setValue(total_slider_duree());
            duree_tp.setText((int) ((tp_slider.getValue() * max_charge_horaire) / 100 / .75) + "h");
            duree_totale.setText(calculate_current_durre() + "h/" + max_charge_horaire + "h");
            tp_updated = (tp_slider.getValue() * max_charge_horaire) / 100;
        });
        projet_slider.addChangeListener(e -> {
            // TODO Auto-generated method stub
            duree_slider.setValue(total_slider_duree());
            duree_projet.setText((int) ((projet_slider.getValue() * max_charge_horaire) / 100 / 1.5) + "h");
            duree_totale.setText(calculate_current_durre() + "h/" + max_charge_horaire + "h");
            p_updated = (projet_slider.getValue() * max_charge_horaire) / 100;
        });
    }

    public int calculate_current_durre() {
        double total = 0;
        total += (double) ((projet_slider.getValue() * max_charge_horaire)) / 100 / 1.5;
        total += (double) ((tp_slider.getValue() * max_charge_horaire)) / 100 / .75;
        total += (double) ((td_slider.getValue() * max_charge_horaire)) / 100;
        total += (double) ((cours_slider.getValue() * max_charge_horaire)) / 100 / 1.5;
        return (int) total;
    }

    public int total_slider_duree() {
        if (calculate_current_durre() > calculateTotal())
            return 100;
        return calculate_current_durre() * 100 / calculateTotal();
    }

    public int calculateTotal() {
        ChargeHoraire c = module.getPrevue();
        double total = 0;
        total += c.getNhTD();
        total += c.getNhTP() / .75;
        total += c.getNhC() / 1.5;
        total += c.getNhP() / 1.5;
        return (int) total;
    }

    public void updateChargeHoraire() {
        module.getPrevue().setNhC((int) cours_updated);
        module.getPrevue().setNhP((int) p_updated);
        module.getPrevue().setNhTD((int) td_updated);
        module.getPrevue().setNhTP((int) tp_updated);

        controleur.saveModuleChargeHoraire(module);
    }
}