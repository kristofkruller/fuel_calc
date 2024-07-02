package com.iec.fuel_calculator.model;

import com.iec.fuel_calculator.utils.Tools;

//import com.iec.fuel_calculator.utils.Tools;

public class InputData {
	private double tripLength;
	private String lengthUnit;
	private double consumption;
	private String consumptionUnit;
	private String currency;
	private double fuelPrice;

	public InputData(double tripLength, String lengthUnit, double consumption, String consumptionUnit, String currency,
			double fuelPrice) {
		this.tripLength = tripLength;
		this.lengthUnit = lengthUnit;
		this.consumption = consumption;
		this.consumptionUnit = consumptionUnit;
		this.currency = currency;
		this.fuelPrice = fuelPrice;
	}

	public double getTripLength() {
		return tripLength;
	}

	public String getLengthUnit() {
		return lengthUnit;
	}

	public double getConsumption() {
		return consumption;
	}

	public String getConsumptionUnit() {
		return consumptionUnit;
	}

	public String getCurrency() {
		return currency;
	}

	public double getFuelPrice() {
		return fuelPrice;
	}

	// HF -> a summarize megvalósítani, úgy hogy megfelelően tudjon az adatokkal
	// dolgozni. -> Calculator
//	public void summarize () {
//		StringBuilder build = new StringBuilder();
//		String [] args = {Double.toString(tripLength), lengthUnit, Double.toString(consumption), consumptionUnit, Double.toString(fuelPrice), currency};
//		build.append("Az általad megadott adatok:");
//		for (int i = 0; i < args.length; i++) {
//			if (i != args.length - 1 && i != 0) {
//				if (i%2 != 0) {
//					build.append(", ");
//				}
//			}
//		}
//		Tools.print(build);
//	}
	public void summarize() {
		StringBuilder build = new StringBuilder();
		String[] args = { Double.toString(tripLength), lengthUnit, Double.toString(consumption), consumptionUnit,
				Double.toString(fuelPrice), currency };
		build.append("Az általad megadott adatok: ");
		for (int i = 0; i < args.length; i++) {
			build.append(args[i]);
			if (i != args.length - 1) {
				if (i%2 != 0) {
					build.append(", ");
				}

			}
		}
		Tools.print(build.toString());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InputData [tripLength=").append(tripLength).append(", ");
		if (lengthUnit != null) {
			builder.append("lengthUnit=").append(lengthUnit).append(", ");
		}
		builder.append("consumption=").append(consumption).append(", ");
		if (consumptionUnit != null) {
			builder.append("consumptionUnit=").append(consumptionUnit).append(", ");
		}
		if (currency != null) {
			builder.append("currency=").append(currency).append(", ");
		}
		builder.append("fuelPrice=").append(fuelPrice).append("]");
		return builder.toString();
	}
}
