package com.iec.fuel_calculator.main;

import com.iec.fuel_calculator.controller.Collector;
import com.iec.fuel_calculator.model.InputData;
import com.iec.fuel_calculator.view.Calculator;

public class Main 
{
	public static void main(String[] args) {
		Collector collect = new Collector();
		
		InputData data = collect.collectInfo();
		Calculator calculate = new Calculator(data);

		calculate.answer();
	}
}
