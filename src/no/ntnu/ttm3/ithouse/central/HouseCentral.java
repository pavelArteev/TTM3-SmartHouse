package no.ntnu.ttm3.ithouse.central;

import java.util.ArrayList;
import java.util.Iterator;

import no.ntnu.ttm3.ithouse.gui.HouseManagerGUI;
import no.ntnu.ttm3.ithouse.sensor.Heater;
import no.ntnu.ttm3.ithouse.sensor.Room;
import no.ntnu.ttm3.ithouse.sensor.SoundSensor;
import no.ntnu.ttm3.ithouse.sensor.TemperatureSensor;

import com.hydna.Channel;
import com.hydna.ChannelError;
import com.hydna.ChannelEvent;
import com.hydna.ChannelMode;

import hydna.ntnu.student.api.HydnaApi;
import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;

@Component
public class HouseCentral {
	private HydnaApi hydnaSvc;

	public static final String DOMAIN = "ithouse.hydna.net/";
	public ArrayList<Channel> channelList = new ArrayList<Channel>();
	public ArrayList<HeatService> heatServiceList = new ArrayList<HeatService>();
	private Channel registerationChannel;
	private Channel sensorDataChannel;
	private Channel adminChannel;
	private HouseManagerGUI gui;
	
	
	private double defalutTemperatureThreshold = 22.0;
	private double defalutSoundThreshold = 1.0;

	@Activate
	public void activate() throws ChannelError, InterruptedException{
		System.out.println("STARTED");
		ChannelEvent event;
		
		registerationChannel = new Channel();
		registerationChannel.connect(DOMAIN + "register", ChannelMode.READWRITE);
		
		sensorDataChannel = new Channel();
		sensorDataChannel.connect(DOMAIN + "sensorData", ChannelMode.READWRITE);
		
		adminChannel = new Channel();
		adminChannel.connect(DOMAIN + "adminControll", ChannelMode.READWRITE);
		
		channelList.add(registerationChannel);
		channelList.add(sensorDataChannel);
		channelList.add(adminChannel);
		
	    while(true){
	    	
	    	Iterator<Channel> it = channelList.iterator();
	    	
		    while(it.hasNext()){
		    	Channel tempChannel =it.next();	
		    	if(tempChannel.hasEvents()){
		    		
		    		event = tempChannel.nextEvent();
	    			String[] message = event.getString().split(":");
		    		System.out.println(event.getString());
		    		
		    		if(tempChannel.getPath().endsWith("register")){//register channel message to register service
		    			
		    			//register message format <room number>:<heater id>
		    			
		    			Room room  = new Room(message[1],
		    									new TemperatureSensor(defalutTemperatureThreshold,defalutTemperatureThreshold),
		    									new SoundSensor(defalutSoundThreshold,defalutSoundThreshold));
		    			Heater newHeater = new Heater(message[2]);
		    			
			    		HeatService newService = new HeatService(room,newHeater);
			    		Channel newChannel = new Channel();
			    		newChannel.connect(DOMAIN + message[2], ChannelMode.READWRITE);
			    		newService.setServiceChannel(newChannel);
			    		
			    		heatServiceList.add(newService);
			    		
			    		registerFeedback(newService);
			    		
		    		}else if(tempChannel.getPath().endsWith("sensorData")){//handler heat sensor data message
		    			
		    			//sensor data message format <room id>:<sensor type>:<current value>
		    			
		    			Iterator<HeatService> heats = heatServiceList.iterator();
		    			
		    			while(heats.hasNext()){
		    				HeatService tempService = heats.next();
		    				
		    				if(tempService.getRoom().getRoomId().equals(message[1])){
		    					resloveCommand(tempService,message[2],Double.parseDouble(message[3]));
		    				}
		    			}
		    			
		    		}
		    		/*
		    		 * adminControll channel to change the sensor threshold
		    		  
		    		 else if(tempChannel.getPath().endsWith("adminControll")){// configure central administrator settings
		    			
		    			//configure message format <setting type>:<settings object>:<setting value>
		    			
		    			Iterator<HeatService> heats = heatServiceList.iterator();
		    			
		    			while(heats.hasNext()){
		    				HeatService tempService = heats.next();
		    				
		    				if(message[2].equals("ALL") || tempService.getRoom().getRoomId().equals(message[2]))
		    				{
			    				settingCommand(tempService,message[1],message[3]);
		    				}
		    			}
		    			
		    		}
		    		*/
		    		
		    	}
		    }
	    }
	}
	
	@Reference
	public void setHydnaApi(HydnaApi hydna) {
		System.out.println("SETTING SERVICSE");
		this.hydnaSvc = hydna;
		this.gui = new HouseManagerGUI(this);
	}
	
	private void registerFeedback(HeatService service){
		try {
			registerationChannel.send(service.getHeater().getHeaterId() + ":registered:" + service.getServiceChannel().getPath());
			this.gui.addNewService(service.getRoom().getRoomId(), service.getHeater().getHeaterId());
		} catch (ChannelError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void resloveCommand(HeatService service, String sensorType, double current){
		if(sensorType.equals("SoundSensor")){
			
			service.getRoom().getsSensor().setCurrentValue(current);
			double sThreshold = service.getRoom().getsSensor().getThresholdValue();
			
			if(sThreshold > current){
				try {
					service.getServiceChannel().send("HeaterControll:stop");
				} catch (ChannelError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else if(sensorType.equals("TemperatureSensor")){
			
			service.getRoom().gettSensor().setCurrentValue(current);
			double sThreshold = service.getRoom().getsSensor().getThresholdValue();
			double tThreshold = service.getRoom().gettSensor().getThresholdValue();
			
			if(service.getRoom().getsSensor().getCurrentValue() > sThreshold){
				if(tThreshold > current){
					try {
						service.getServiceChannel().send("HeaterControl:heating");
					} catch (ChannelError e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					try {
						service.getServiceChannel().send("HeaterControl:stop");
					} catch (ChannelError e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private void settingCommand(HeatService service,String sensorType, String threshold){
		try {
			adminChannel.send(service.getRoom().getRoomId() + ":" + sensorType + ":" + threshold);
		} catch (ChannelError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void boardcastSettings(String temperatureThreshold, String soundThreshold){
		Iterator<HeatService> heats = heatServiceList.iterator();
		
		while(heats.hasNext()){
			HeatService tempService = heats.next();
			
			settingCommand(tempService,"TemperatureThreshold",temperatureThreshold);
			settingCommand(tempService,"SoundThreshold",soundThreshold);
			
		}
	}
}
