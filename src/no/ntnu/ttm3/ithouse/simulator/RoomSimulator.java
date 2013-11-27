package no.ntnu.ttm3.ithouse.simulator;

import java.util.Timer;

import com.hydna.Channel;
import com.hydna.ChannelError;
import com.hydna.ChannelMode;

import no.ntnu.ttm3.ithouse.gui.RoomSimulatorGUI;
import no.ntnu.ttm3.ithouse.sensor.Room;
import no.ntnu.ttm3.ithouse.sensor.SoundSensor;
import no.ntnu.ttm3.ithouse.sensor.TemperatureSensor;
import hydna.ntnu.student.api.HydnaApi;
import hydna.ntnu.student.listener.api.HydnaListener;
import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;

@Component
public class RoomSimulator {
	public static final String DOMAIN = "ithouse.hydna.net/";
	
	private HydnaApi hydnaSvc;
	private HydnaListener listener;
	public String name = "Room1";
	private RoomSimulatorGUI gui;
	private Room room;
	private Channel sensorDataChannel;

	@Activate
	public void activate() throws ChannelError, InterruptedException {
		System.out.println("STARTED");
		
		this.room = new Room(name,
						new TemperatureSensor(22.0,22.0),
						new SoundSensor(1.0,1.0));
		
		this.listener = new HydnaListener() {
			
			@Override
			public void systemMessage(String msg) {
				System.out.println("got sysmsg: "+msg);
			}
			
			@Override
			public void signalRecieved(String msg) {
				System.out.println("got signal: "+msg);
			}
			
			@Override
			public void messageRecieved(String msg) {
				processMessage(msg);
				System.out.println("got msg: "+msg);
			}
		};
		hydnaSvc.registerListener(this.listener);
		hydnaSvc.connectChannel(DOMAIN + "adminControll", "rwe");
		//hydnaSvc.emitSignal("SINGALS CLIENT");
		
	}
	
	@Reference
	public void setHydnaApi(HydnaApi hydna) {
		System.out.println("SETTING SERVICSE");
		this.hydnaSvc = hydna;
		this.gui = new RoomSimulatorGUI(this);
	}
	
	private void processMessage(String message){
		String[] strArray = {};
		strArray = message.split(":");
		if(strArray[0].equals(name)){
			if(strArray[1].equals("TemperatureThreshold")){
				this.room.gettSensor().setThresholdValue(Double.parseDouble(strArray[2]));
				this.gui.setThresholdDisplay(strArray[1], strArray[2]);
			}else if(strArray[1].equals("SoundThreshold")){
				this.room.getsSensor().setThresholdValue(Double.parseDouble(strArray[2]));
				this.gui.setThresholdDisplay(strArray[1], strArray[2]);
			}
		}
	}
	
	public void sendSensorData(String roomId,String sensorType,String current){
		hydnaSvc.stayConnected(false);
		hydnaSvc.connectChannel(DOMAIN + "sensorData", "rwe");
		hydnaSvc.sendMessage(roomId + ":" + sensorType + ":" + current);
		if(sensorType.equals("TemperatureSensor")){
			this.room.gettSensor().setCurrentValue(Double.parseDouble(current));
		}else if(sensorType.equals("SoundSensor"))
		{
			this.room.getsSensor().setCurrentValue(Double.parseDouble(current));
		}
	}
}
