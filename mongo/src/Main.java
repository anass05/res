import Presentation.Controleur;

import java.util.Random;

/**
 * Created by Anass on 2018-05-09.
 */
public class Main {
    public static void main(String[] args) {
        new Controleur();
     }
}

/*
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class Main extends JFrame {

    public Main() {
        super("Selection Model Test");
        setSize(450, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        TableModel tm = new AbstractTableModel() {
            public int getRowCount() {
                return 10;
            }

            public int getColumnCount() {
                return 10;
            }

            public Object getValueAt(int r, int c) {
                return "0";
            }
        };

        final JTable jt = new JTable(tm);

        JScrollPane jsp = new JScrollPane(jt);
        getContentPane().add(jsp, BorderLayout.CENTER);

        JPanel controlPanel, buttonPanel, columnPanel, rowPanel;

        buttonPanel = new JPanel();
        final JCheckBox cellBox, columnBox, rowBox;
        cellBox = new JCheckBox("Cells", jt.getCellSelectionEnabled());
        columnBox = new JCheckBox("Columns", jt.getColumnSelectionAllowed());
        rowBox = new JCheckBox("Rows", jt.getRowSelectionAllowed());

        cellBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                jt.setCellSelectionEnabled(cellBox.isSelected());
                columnBox.setSelected(jt.getColumnSelectionAllowed());
                rowBox.setSelected(jt.getRowSelectionAllowed());
            }
        });

        columnBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                jt.setColumnSelectionAllowed(columnBox.isSelected());
                cellBox.setSelected(jt.getCellSelectionEnabled());
            }
        });

        rowBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                jt.setRowSelectionAllowed(rowBox.isSelected());
                cellBox.setSelected(jt.getCellSelectionEnabled());
            }
        });

        buttonPanel.add(new JLabel("Selections allowed:"));
        buttonPanel.add(cellBox);
        buttonPanel.add(columnBox);
        buttonPanel.add(rowBox);

        columnPanel = new JPanel();
        ListSelectionModel csm = jt.getColumnModel().getSelectionModel();
        JLabel columnCounter = new JLabel("Selected Column Indices:");
        csm.addListSelectionListener(new SelectionDebugger(columnCounter, csm));
        columnPanel.add(new JLabel("Selected columns:"));
        columnPanel.add(columnCounter);

        rowPanel = new JPanel();
        ListSelectionModel rsm = jt.getSelectionModel();
        JLabel rowCounter = new JLabel("Selected Row Indices:");
        rsm.addListSelectionListener(new SelectionDebugger(rowCounter, rsm));
        rowPanel.add(new JLabel("Selected rows:"));
        rowPanel.add(rowCounter);

        controlPanel = new JPanel(new GridLayout(0, 1));
        controlPanel.add(buttonPanel);
        controlPanel.add(columnPanel);
        controlPanel.add(rowPanel);

        getContentPane().add(controlPanel, BorderLayout.SOUTH);
    }

    public static void main(String args[]) {
        Main se = new Main();
        se.setVisible(true);
    }

    public class SelectionDebugger implements ListSelectionListener {
        JLabel debugger;

        ListSelectionModel model;

        public SelectionDebugger(JLabel target, ListSelectionModel lsm) {
            debugger = target;
            model = lsm;
        }

        public void valueChanged(ListSelectionEvent lse) {
            if (!lse.getValueIsAdjusting()) {
                StringBuffer buf = new StringBuffer();
                int[] selection = getSelectedIndices(model.getMinSelectionIndex(), model
                        .getMaxSelectionIndex());
                if (selection.length == 0) {
                    buf.append("none");
                } else {
                    for (int i = 0; i < selection.length - 1; i++) {
                        buf.append(selection[i]);
                        buf.append(", ");
                    }
                    buf.append(selection[selection.length - 1]);
                }
                debugger.setText(buf.toString());
            }
        }

        protected int[] getSelectedIndices(int start, int stop) {
            if ((start == -1) || (stop == -1)) {
                return new int[0];
            }

            int guesses[] = new int[stop - start + 1];
            int index = 0;
            for (int i = start; i <= stop; i++) {
                if (model.isSelectedIndex(i)) {
                    guesses[index++] = i;
                }
            }

            int realthing[] = new int[index];
            System.arraycopy(guesses, 0, realthing, 0, index);
            return realthing;
        }
    }
}
*/