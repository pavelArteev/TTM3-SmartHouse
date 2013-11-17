package no.ntnu.ttm3.ithouse.central;

import no.ntnu.ttm3.ithouse.sensor.Heater;
import no.ntnu.ttm3.ithouse.sensor.Room;

import com.hydna.Channel;

public class HeatService {
	private Room room;
	private Heater heater;
	private Channel serviceChannel;
	
	public HeatService(Room room, Heater heater){
		this.room = room;
		this.heater = heater;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Heater getHeater() {
		return heater;
	}

	public void setHeater(Heater heater) {
		this.heater = heater;
	}

	public Channel getServiceChannel() {
		return serviceChannel;
	}

	public void setServiceChannel(Channel serviceChannel) {
		this.serviceChannel = serviceChannel;
	}
	
	
}
