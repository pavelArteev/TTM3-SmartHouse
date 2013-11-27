package no.ntnu.ttm3.ithouse.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class HeaterSimulatorGUI extends JFrame {

	private String heaterId;
	private JPanel contentPane;
	private JLabel statusDisplayer;
	private JLabel hearterName;

	/**
	 * Create the frame.
	 */
	public HeaterSimulatorGUI(String heaterId) {
		this.heaterId = heaterId;
		initGUI();
	}
	
	private void initGUI(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Heater Status:");
		lblNewLabel.setBounds(81, 102, 102, 16);
		contentPane.add(lblNewLabel);
		
		statusDisplayer = new JLabel("On");
		statusDisplayer.setBounds(216, 102, 61, 16);
		contentPane.add(statusDisplayer);
		
		hearterName = new JLabel(heaterId);
		hearterName.setBounds(179, 32, 98, 16);
		contentPane.add(hearterName);
	}
	
	public void heating(){
		statusDisplayer.setText("Heating...");
	}
	
	public void stopHeating(){
		statusDisplayer.setText("Stop");
	}
}
