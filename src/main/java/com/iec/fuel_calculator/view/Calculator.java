package com.iec.fuel_calculator.view;

import com.iec.fuel_calculator.model.CalculatedData;
import com.iec.fuel_calculator.model.InputData;
import com.iec.fuel_calculator.utils.FileIO;
import com.iec.fuel_calculator.utils.Tools;
import com.iec.utils_lib.logger.MyLogger;

public class Calculator {
	private InputData data;

	public Calculator(InputData data) {
		this.data = data;
	}

	public void answer() {
		CalculatedData calcData = new CalculatedData();
		calcData.setCalcConsumption((data.getTripLength() / 100) * data.getConsumption());
		calcData.setCalcPrice(calcData.getCalcConsumption() * data.getFuelPrice());
		
		StringBuilder res = new StringBuilder();
		res.append("Várható fogyasztás: ");
		res.append(Double.toString(calcData.getCalcConsumption()) + " " + data.getConsumptionUnit() + "\n");
		res.append("Várható költség: ");
		res.append(Double.toString(calcData.getCalcPrice()) + " " + data.getCurrency() + "\n");
		
		Tools.print(res.toString());
		FileIO.appendResultToCSV(calcData, data.getCurrency(), data.getConsumptionUnit());
		MyLogger.close();
	}
}