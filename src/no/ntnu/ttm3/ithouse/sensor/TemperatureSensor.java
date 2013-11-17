package no.ntnu.ttm3.ithouse.sensor;

public class TemperatureSensor {
	private double currentValue;
	private double thresholdValue;
	
	public TemperatureSensor(double cValue, double tValue){
		this.currentValue = cValue;
		this.thresholdValue = tValue;
	}

	public double getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(double currentValue) {
		this.currentValue = currentValue;
	}

	public double getThresholdValue() {
		return thresholdValue;
	}

	public void setThresholdValue(double thresholdValue) {
		this.thresholdValue = thresholdValue;
	}
}
