package no.ntnu.ttm3.ithouse.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import no.ntnu.ttm3.ithouse.simulator.RoomSimulator;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoomSimulatorGUI extends JFrame {

	private String roomId;
	private RoomSimulator simulator;
	private JPanel contentPane;
	private JTextField tCurrentValue;
	private JTextField sCurrentValue;
	private JLabel temperatureThresholdValue;
	private JLabel soundThresholdValue;

	/**
	 * Create the frame.
	 */
	public RoomSimulatorGUI(RoomSimulator simulator) {
		this.roomId = simulator.name;
		this.simulator = simulator;
		initGUI();
	}

	private void initGUI(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel roomName = new JLabel("Room Name");
		roomName.setBounds(158, 25, 97, 16);
		contentPane.add(roomName);
		
		JLabel lblNewLabel = new JLabel("Temperature Sensor:");
		lblNewLabel.setBounds(21, 108, 136, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Sound Sensor:");
		lblNewLabel_1.setBounds(21, 152, 127, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Current Value");
		lblNewLabel_2.setBounds(158, 66, 97, 16);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Threshold");
		lblNewLabel_3.setBounds(296, 66, 77, 16);
		contentPane.add(lblNewLabel_3);
		
		temperatureThresholdValue = new JLabel("22.0");
		temperatureThresholdValue.setBounds(296, 108, 61, 16);
		contentPane.add(temperatureThresholdValue);
		
		soundThresholdValue = new JLabel("1.0");
		soundThresholdValue.setBounds(296, 152, 61, 16);
		contentPane.add(soundThresholdValue);
		
		tCurrentValue = new JTextField();
		tCurrentValue.setBounds(158, 102, 87, 28);
		contentPane.add(tCurrentValue);
		tCurrentValue.setColumns(10);
		
		sCurrentValue = new JTextField();
		sCurrentValue.setBounds(158, 146, 87, 28);
		contentPane.add(sCurrentValue);
		sCurrentValue.setColumns(10);
		
		JButton updateButton = new JButton("Update Sensor");
		updateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				postSensorData(tCurrentValue.getText(),sCurrentValue.getText());
			}
		});
		updateButton.setBounds(152, 210, 117, 29);
		contentPane.add(updateButton);
	}
	
	public void postSensorData(String temperatureData, String soundData){
		this.simulator.sendSensorData(this.roomId, "TemperatureSensor", temperatureData);
		this.simulator.sendSensorData(this.roomId, "SoundSensor", temperatureData);
	}
	
	public void setThresholdDisplay(String sensorType,String thresholdValue){
		if(sensorType.equals("TemperatureThreshold")){
			this.temperatureThresholdValue.setText(thresholdValue);
		}else if(sensorType.equals("SoundThreshold")){
			this.soundThresholdValue.setText(thresholdValue);
		}
	}
}
