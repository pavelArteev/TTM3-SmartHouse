package no.ntnu.ttm3.ithouse.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import no.ntnu.ttm3.ithouse.central.HouseCentral;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JTable;

import java.awt.GridLayout;

public class HouseManagerGUI extends JFrame {

	private JPanel contentPane;
	private HouseCentral centralService;
	private JTable table;
	private JTextField soundValue;
	private JTextField temperatureValue;
	private JTable roomServiceTable;
	private DefaultTableModel tableModel;

	/**
	 * Create the frame.
	 */
	public HouseManagerGUI(HouseCentral service) {
		
		this.centralService = service;
		
		initGUI();
		
	}
	
	private void initGUI(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel_2 = new JLabel("IT House Central Administrator");
		lblNewLabel_2.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		panel.add(lblNewLabel_2);
		
		table = new JTable();
		panel.add(table);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JLabel lblHouseDesiredSound = new JLabel("House Desired Sound :");
		lblHouseDesiredSound.setBounds(6, 45, 195, 16);
		panel_1.add(lblHouseDesiredSound);
		
		JButton settingBtn = new JButton("Boardcast House Settings ");
		settingBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				postSettings(temperatureValue.getText(),soundValue.getText());
			}
		});
		settingBtn.setBounds(213, 198, 208, 29);
		panel_1.add(settingBtn);
		
		soundValue = new JTextField();
		soundValue.setColumns(10);
		soundValue.setBounds(213, 39, 208, 28);
		panel_1.add(soundValue);
		
		temperatureValue = new JTextField();
		temperatureValue.setColumns(10);
		temperatureValue.setBounds(213, 6, 207, 28);
		panel_1.add(temperatureValue);
		
		JLabel lblHouseDesiredTemperature = new JLabel("House Desired Temperature :");
		lblHouseDesiredTemperature.setBounds(6, 12, 195, 16);
		panel_1.add(lblHouseDesiredTemperature);
		
		this.tableModel = new DefaultTableModel();
		roomServiceTable = new JTable(tableModel);
		tableModel.addColumn("Room No");
		tableModel.addColumn("Heater No");
		roomServiceTable.setBounds(16, 73, 400, 113);
		panel_1.add(roomServiceTable);
	}
	
	public void postSettings(String temperatureThreshold, String soundThreshold){
		this.centralService.boardcastSettings(temperatureThreshold, soundThreshold);
	}
	
	public void addNewService(String roomId, String heaterId){
		this.tableModel.addRow(new Object[]{roomId,heaterId});
	}
}
