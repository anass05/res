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

public class ModuleElementNoPlanning extends JPanel {
	private JLabel module;
	private Module m;


	public ModuleElementNoPlanning(Module m) {
		this.m = m;
		setBorder(new LineBorder(new Color(64, 64, 64), 1, true));
		setBackground(Color.WHITE);
		setLayout(new GridLayout(4, 2, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(239,154,154));
		panel_1.setBorder(new EmptyBorder(0, 9, 0, 0));
		add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		module = new JLabel(m.getLibele());
		module.setFont(new Font("Tahoma", Font.BOLD, 14));
		module.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(module);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(239,154,154));
		add(panel);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(255, 255, 255));
		add(panel_3);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(255, 255, 255));
		add(panel_4);
		
		JLabel lblNewLabel = new JLabel("Planning ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		add(lblNewLabel);
		
		JLabel lblNonDfini = new JLabel("non d\u00E9fini");
		lblNonDfini.setFont(new Font("Tahoma", Font.PLAIN, 18));
		add(lblNonDfini);

	}

}
