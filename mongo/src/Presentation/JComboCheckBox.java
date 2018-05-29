package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author Paris
 */

public class JComboCheckBox extends JComboBox implements ActionListener {
    private static final long serialVersionUID = -3388586151789454096L;
    private ComboCheckRenderer renderer;


    public JComboCheckBox(final Iterable<String> list, final boolean state) {
        super.addActionListener(this);
        populate(list, state);
        setRenderer(renderer = new ComboCheckRenderer());

    }

    @Override
    public void addActionListener(final ActionListener l) {
        super.removeActionListener(this);
        super.addActionListener(l);
        super.addActionListener(this);
    }

    public void actionPerformed(final ActionEvent e) {
        if (e.getModifiers() == 0) {
            return;
        }
        final JComboBox cb = (JComboBox) e.getSource();
        final StatefulItem store = (StatefulItem) cb.getSelectedItem();
        try {
            ComboCheckRenderer ccr = (ComboCheckRenderer) cb.getRenderer();
            ccr.checkBox.setSelected(store.state = !store.state);
            getSelectedItems();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setText(final String label) {
        renderer.setText(label);
    }

    public void reset() {
        for (int i = 0; i < getItemCount(); i++) {
            final StatefulItem item = (StatefulItem) getItemAt(i);
            item.state = false;
        }
        setText("");
    }

    public String[] getSelectedItems() {
        final ArrayList<String> items = new ArrayList<String>();
        String ss = "";
        for (int i = 0; i < getItemCount(); i++) {
            final StatefulItem item = (StatefulItem) getItemAt(i);
            if (item.state) {
                items.add(item.id);
                ss = ss + item.id + ", ";
            }
        }
        if (ss.length() > 1)
            setText(ss.substring(0, ss.length() - 2));
        final String[] list = new String[items.size()];
        items.toArray(list);
        return list;
    }

    public ArrayList<String> getSelectedItemss() {
        ArrayList<String> items = new ArrayList<String>();
        for (int i = 0; i < getItemCount(); i++) {
            final StatefulItem item = (StatefulItem) getItemAt(i);
            if (item.state) {
                items.add(item.id);
            }
        }
        return items;
    }

    public void populate(final Iterable<String> list, final boolean state) {
        removeAllItems();
        for (final String item : list) {
            addItem(new StatefulItem(item, state));
        }
    }

    class ComboCheckRenderer implements ListCellRenderer {
        final JLabel none;
        final JCheckBox checkBox;

        public ComboCheckRenderer() {
            none = new JLabel();
            checkBox = new JCheckBox();
        }

        public void setText(final String label) {
            none.setText(label);
        }

        public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
            if (index == -1) {
                return none;
            }
            StatefulItem store = (StatefulItem) value;
            checkBox.setText(store.id);
            checkBox.setSelected(store.state);
            return checkBox;
        }
    }

    class StatefulItem {
        public String id;
        public boolean state;

        public StatefulItem(final String id, final boolean state) {
            this.id = id;
            this.state = state;
        }

        @Override
        public String toString() {
            return id;
        }
    }
}