package no.ntnu.ttm3.ithouse.sensor;

public class Heater {
	private String heaterId;
	private boolean heating,stop;

	public String getHeaterId() {
		return heaterId;
	}

	public void setHeaterId(String heaterId) {
		this.heaterId = heaterId;
	}

	public boolean isHeating() {
		return heating;
	}

	public void setHeating(boolean heating) {
		this.heating = heating;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}
	
	public Heater(String id){
		this.heaterId = id;
		this.heating = false;
		this.stop = true;
	}
}
