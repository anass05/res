package Presentation;

import Beans.Chapitre;
import Beans.Module;
import Beans.Planning;
import Beans.Seance;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class SuiviPlanning extends JFrame {

    private JLabel total;
    private JProgressBar coursProgress;
    private JProgressBar tdProgress;
    private JProgressBar tpProgress;
    private JProgressBar projetProgress;
    private JPanel panel;
    private JLabel dispo;
    private JComboCheckBox boxi;
    private Module module;
    private DefaultTableModel model;
    private float TDLeft, TPLeft, CLeft, PLeft;
    private float TDCurrent, TPCurrent, CCurrent, PCurrent;
    private float totalLeft;
    private Controleur controleur;

    public SuiviPlanning(Module module, Controleur controleur) {
        super("Suivi du planning du module " + module.getLibele());
        this.module = module;
        this.controleur = controleur;
        TDCurrent = module.getPrevue().getNhC();
        PCurrent = module.getPrevue().getNhP();
        CCurrent = module.getPrevue().getNhC();
        TPCurrent = module.getPrevue().getNhTP();
        TDLeft = TDCurrent;
        TPLeft = TPCurrent;
        CLeft = CCurrent;
        PLeft = PCurrent;
        totalLeft = module.getReelle().total();
        initialize();
    }

    private void initialize() {
        setBounds(100, 100, 653, 406);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
        JPanel tableHolder = new JPanel();


        Object[] weeks = new Object[14];
        for (int i = 0; i < 14; i++)
            weeks[i] = "sem" + (i + 1);


        String column[] = {"N°Semaine", "Contenu"};
        tableHolder.setLayout(new BoxLayout(tableHolder, BoxLayout.X_AXIS));

        model = new DefaultTableModel(populateTable(), column);
        JTable table = new JTable(model);

        table.setRowHeight(25);
        table.getColumnModel().getColumn(1).setPreferredWidth(220);
        JScrollPane sp = new JScrollPane(table);
        tableHolder.add(sp);
        getContentPane().add(tableHolder);

        //DefaultTableModel model = (DefaultTableModel) table.getModel();
        ArrayList<String> items = new ArrayList<>();
        if (module.getReelle().getNhP() > 0)
            items.add("Projet");
        if (module.getReelle().getNhTD() > 0)
            items.add("TD");
        if (module.getCahierCharges().getNbTP() > 0)
            items.add("TP");


        boxi = new JComboCheckBox(getItems(), false);


        JPanel panel_01x = new JPanel();
        panel_01x.setBorder(new EmptyBorder(0, 2, 0, 20));

        panel_01x.setLayout(new BoxLayout(panel_01x, BoxLayout.X_AXIS));
        panel_01x.add(boxi);


        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                boxi.reset();
                JOptionPane.showMessageDialog(null, panel_01x, "Quoi enseigner cette séance?", JOptionPane.QUESTION_MESSAGE);
                String str = null;
                if (boxi.getSelectedItemss() != null) {
                    int multiplicateur = boxi.getSelectedItemss().size();
                    for (String ss : boxi.getSelectedItemss()) {
                        str = str + ss + ", ";
                        if (!ss.equals("TD") && !ss.equals("TP") && !ss.equals("Projet"))
                            items.remove(ss);

                        if (ss.equals("TD"))
                            TDLeft = TDLeft - 4 / multiplicateur;
                        else if (ss.equals("TP"))
                            TPLeft = TPLeft - 4 / multiplicateur;
                        else if (ss.equals("Projet"))
                            PLeft = PLeft - 4 / multiplicateur;
                        else
                            CLeft = CLeft - 4 / multiplicateur;
                    }
                    updateView();

                    if (str.length() > 1) {
                        str = str.replace("null", "");
                        model.setValueAt(str.substring(0, str.length() - 2), table.getSelectedRow(), 1);
                    }
                    boxi.populate(items, false);
                }
            }
        });
        JPanel panel_1 = new JPanel();
        GridLayout gl_panel_1 = new GridLayout(0, 2);
        gl_panel_1.setVgap(10);
        gl_panel_1.setHgap(5);
        panel_1.setLayout(gl_panel_1);

        JLabel j1 = new JLabel("Module: ");
        j1.setFont(new

                Font("Tahoma", Font.PLAIN, 13));
        j1.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel j2 = new JLabel(module.getLibele());
        j2.setFont(new

                Font("Tahoma", Font.PLAIN, 13));
        JLabel j3 = new JLabel("heures: ");
        j3.setFont(new

                Font("Tahoma", Font.PLAIN, 13));
        j3.setHorizontalAlignment(SwingConstants.RIGHT);
        total = new

                JLabel(module.getReelle().

                total() + "h");
        total.setFont(new

                Font("Tahoma", Font.PLAIN, 13));
        JLabel j5 = new JLabel("disponibles: ");
        j5.setFont(new

                Font("Tahoma", Font.PLAIN, 13));
        j5.setHorizontalAlignment(SwingConstants.RIGHT);
        dispo = new

                JLabel(totalLeft + "h");
        dispo.setFont(new

                Font("Tahoma", Font.PLAIN, 13));

        panel_1.add(j1);
        panel_1.add(j2);
        panel_1.add(j3);
        panel_1.add(total);
        panel_1.add(j5);
        panel_1.add(dispo);


        JLabel lblCours = new JLabel("cours:  ");
        lblCours.setFont(new

                Font("Tahoma", Font.PLAIN, 13));
        lblCours.setHorizontalAlignment(SwingConstants.TRAILING);
        panel_1.add(lblCours);

        JPanel panel_01 = new JPanel();
        panel_01.setBorder(new

                EmptyBorder(0, 2, 0, 20));
        panel_1.add(panel_01);
        panel_01.setLayout(new

                BoxLayout(panel_01, BoxLayout.X_AXIS));

        coursProgress = new

                JProgressBar();
        coursProgress.setForeground(new

                Color(255, 153, 102));
        coursProgress.setValue((int) (CLeft * 100 / CCurrent));
        panel_01.add(coursProgress);


        JLabel lblNewLabel_1 = new JLabel("TD:  ");
        lblNewLabel_1.setFont(new

                Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
        panel_1.add(lblNewLabel_1);

        JPanel panel_11 = new JPanel();
        panel_11.setBorder(new

                EmptyBorder(0, 2, 0, 20));
        panel_1.add(panel_11);
        panel_11.setLayout(new

                BoxLayout(panel_11, BoxLayout.X_AXIS));

        tdProgress = new

                JProgressBar();
        tdProgress.setForeground(new

                Color(255, 153, 102));
        tdProgress.setValue((int) (TDLeft * 100 / TDCurrent));
        panel_11.add(tdProgress);


        JLabel lblTp = new JLabel("TP:  ");
        lblTp.setFont(new

                Font("Tahoma", Font.PLAIN, 13));
        lblTp.setHorizontalAlignment(SwingConstants.TRAILING);
        panel_1.add(lblTp);

        panel = new

                JPanel();
        panel.setBorder(new

                EmptyBorder(0, 2, 0, 20));
        panel_1.add(panel);
        panel.setLayout(new

                BoxLayout(panel, BoxLayout.X_AXIS));

        tpProgress = new

                JProgressBar();
        tpProgress.setForeground(new

                Color(255, 153, 102));
        tpProgress.setValue((int) (TPLeft * 100 / TPCurrent));
        panel.add(tpProgress);


        JLabel lblProjet = new JLabel("Projet:  ");
        lblProjet.setFont(new

                Font("Tahoma", Font.PLAIN, 13));
        lblProjet.setHorizontalAlignment(SwingConstants.TRAILING);
        panel_1.add(lblProjet);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new

                EmptyBorder(0, 2, 0, 20));
        panel_1.add(panel_2);
        panel_2.setLayout(new

                BoxLayout(panel_2, BoxLayout.X_AXIS));

        projetProgress = new

                JProgressBar();
        projetProgress.setForeground(new

                Color(255, 153, 102));
        projetProgress.setValue((int) (PLeft * 100 / PCurrent));
        panel_2.add(projetProgress);


        JPanel rightPannel = new JPanel();
        rightPannel.setBorder(new

                EmptyBorder(10, 10, 10, 10));
        rightPannel.setLayout(new

                BoxLayout(rightPannel, BoxLayout.Y_AXIS));
        rightPannel.add(panel_1);
        JPanel pan2 = new JPanel();
        pan2.setBorder(new EmptyBorder(10, 0, 0, 0));

        JButton valider = new JButton("Valider");
        JButton annuler = new JButton("Annuler");

        valider.addActionListener(e -> assigneSeasncesToChapitres());
        pan2.add(valider);
        pan2.add(annuler);

        rightPannel.add(pan2);

        getContentPane().add(rightPannel);

        setLocationRelativeTo(null);

        initRemainigTime();

        setVisible(true);

    }

    public String[][] populateTable() {
        String data[][] = new String[14][2];
        //System.out.println(module.getPlan().getSeances().get(1));
        for (int i = 0; i < 14; i++) {
            String courses = "";
            Seance ss = null;
            for (Seance s : module.getPlan().getSeances())
                if (s.getSemaine() == i + 1)
                    ss = s;
            if (ss != null)
                for (Chapitre p : module.getPlan().getSeanceAt(i + 1).getChapitres()) {
                    courses += p.getTitre() + ", ";
                }
            if (courses.length() > 1)
                courses = courses.substring(0, courses.length() - 2);
            data[i] = new String[]{"Sem " + (i + 1), courses};
        }
        return data;
    }

    public void updateView() {
        totalLeft = TDLeft + TPLeft + CLeft + PLeft;
        dispo.setText(totalLeft + "h");
        coursProgress.setValue((int) (CLeft * 100 / CCurrent));
        tdProgress.setValue((int) (TDLeft * 100 / TDCurrent));
        tpProgress.setValue((int) (TPLeft * 100 / TPCurrent));
        projetProgress.setValue((int) (PLeft * 100 / PCurrent));
    }


    public ArrayList<String> getItems() {
        ArrayList<Chapitre> items = module.getCahierCharges().getChapitres();
        ArrayList<String> itemsToReturn = new ArrayList<>();
        for (Chapitre c : items) {
            if (c.getSeance() == null)
                itemsToReturn.add(c.getTitre());
        }
        return itemsToReturn;
    }

    public void initRemainigTime() {
        for (Seance s : module.getPlan().getSeances()) {
            int multiplicateur = s.getChapitres().size();
            for (Chapitre c : s.getChapitres()) {
                String ss = c.getTitre();
                if (ss.equals("TD"))
                    TDLeft = TDLeft - 4 / multiplicateur;
                else if (ss.equals("TP"))
                    TPLeft = TPLeft - 4 / multiplicateur;
                else if (ss.equals("Projet"))
                    PLeft = PLeft - 4 / multiplicateur;
                else {
                    CLeft = CLeft - 4 / multiplicateur;
                }
            }
        }
        updateView();
    }

    public void assigneSeasncesToChapitres() {
        for (int count = 0; count < model.getRowCount(); count++) {
            String[] ss = model.getValueAt(count, 1).toString().split(", ");
            for (String d : ss) {
                if (!d.equals("") && !d.equals("TP") && !d.equals("TD") && !d.equals("Projet")) {
                    module.getCahierCharges().getChapitre(d).setSeance(module.getPlan().getSeanceAt(count + 1));
                }
                else if (d.equals("TP") || d.equals("TD") || d.equals("Projet")) {
                    module.getPlan().getSeanceAt(count + 1).addChapitre(new Chapitre(d));
                }
            }
        }
        controleur.suiviPlanning(module);
    }
}
