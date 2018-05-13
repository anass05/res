package Presentation;

import Beans.Module;

import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

public class ModuleElement extends JPanel {
	private JLabel module;
	private JLabel prevu;
	public double r;

	/**
	 * Create the panel.
	 */
	public ModuleElement(Module m) {
		r = Math.random();
		
		setBorder(new LineBorder(new Color(64, 64, 64), 1, true));
		setBackground(Color.WHITE);
		setLayout(new GridLayout(4, 2, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255,171,145));
		panel_1.setBorder(new EmptyBorder(0, 9, 0, 0));
		add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		module = new JLabel(m.getLibele());
		module.setFont(new Font("Tahoma", Font.BOLD, 14));
		module.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(module);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255,171,145));
		add(panel);
		
		JLabel lblNewLabel = new JLabel("Pr\u00E9vu:  ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblNewLabel);
		
		prevu = new JLabel("Cours");
		add(prevu);
		
		JLabel lblNewLabel_2 = new JLabel("R\u00E9lis\u00E9:  ");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblNewLabel_2);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(0, 2, 0, 20));
		panel_2.setBackground(Color.WHITE);
		add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setForeground(new Color(255, 153, 102));
		progressBar.setValue(15);
		panel_2.add(progressBar);
		
		JLabel lblNewLabel_4 = new JLabel("Retard:  ");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblNewLabel_4);
		
		JLabel retard = new JLabel("0 heures");
		add(retard);

	}

}
