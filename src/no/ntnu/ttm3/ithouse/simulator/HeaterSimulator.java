package no.ntnu.ttm3.ithouse.simulator;

import no.ntnu.ttm3.ithouse.gui.HeaterSimulatorGUI;
import no.ntnu.ttm3.ithouse.sensor.Heater;
import hydna.ntnu.student.api.HydnaApi;
import hydna.ntnu.student.listener.api.HydnaListener;
import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;

@Component
public class HeaterSimulator {
	public static final String DOMAIN = "ithouse.hydna.net/";
	
	private HydnaApi hydnaSvc;
	private HydnaListener listener;
	public static String name = "heater1";
	private HeaterSimulatorGUI gui;
	private Heater heater;

	@Activate
	public void activate() {
		System.out.println("STARTED");
		
		this.heater = new Heater(name);
		
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
		hydnaSvc.connectChannel(DOMAIN + "register", "rwe");
		//hydnaSvc.emitSignal("SINGALS CLIENT");
		hydnaSvc.sendMessage("room1:"+name);
		
	}
	
	@Reference
	public void setHydnaApi(HydnaApi hydna) {
		System.out.println("SETTING SERVICSE");
		this.hydnaSvc = hydna;
		this.gui = new HeaterSimulatorGUI(name);
	}
	
	private void processMessage(String message){
		String[] strArray = {};
		strArray = message.split(":");
		if(strArray[1].equals("registered") && strArray[0].equals(name)){
			hydnaSvc.stayConnected(false);
			hydnaSvc.connectChannel(strArray[2], "rwe");
		}else if(strArray[0].equals("HeaterControl")){
			if(strArray[1].equals("heating")){
				this.heater.setHeating(true);
				this.heater.setStop(false);
				this.gui.heating();
			}else if(strArray[1].equals("stop")){
				this.heater.setHeating(false);
				this.heater.setStop(true);
				this.gui.stopHeating();
			}
		}
	}
}
