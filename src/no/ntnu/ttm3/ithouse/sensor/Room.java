package no.ntnu.ttm3.ithouse.sensor;

public class Room {
	private TemperatureSensor tSensor;
	private SoundSensor sSensor;
	private String roomId;
	
	public Room(String id,TemperatureSensor tS,SoundSensor sS){
		this.roomId = id;
		this.tSensor = tS;
		this.sSensor = sS;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public TemperatureSensor gettSensor() {
		return tSensor;
	}

	public void settSensor(TemperatureSensor tSensor) {
		this.tSensor = tSensor;
	}

	public SoundSensor getsSensor() {
		return sSensor;
	}

	public void setsSensor(SoundSensor sSensor) {
		this.sSensor = sSensor;
	}
	
}
