package com.iec.fuel_calculator.model;

public class Car {
	private String brand;
	private String model;
	private double avgConsumption;
	public Car(String brand, String model, double avgConsumption) {
		this.brand = brand;
		this.model = model;
		this.avgConsumption = avgConsumption;
	}
	public String getBrand() {
		return brand;
	}
	public String getModel() {
		return model;
	}
	public double getAvgConsumption() {
		return avgConsumption;
	}
	@Override
	public String toString() {
		return brand + ", Típus: " + model + ", Átlagos fogyasztás (/100km): " + avgConsumption;
	}
}
