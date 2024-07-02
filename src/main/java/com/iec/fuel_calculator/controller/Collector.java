package com.iec.fuel_calculator.controller;

import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import com.iec.fuel_calculator.model.Car;
import com.iec.fuel_calculator.model.InputData;
import com.iec.fuel_calculator.utils.FileIO;
import com.iec.fuel_calculator.utils.Tools;
import com.iec.utils_lib.input.StringFormatter;

public class Collector {
	private Scanner scan;
	private List<Car> cars;
	
	
	public Collector() {
		scan = new Scanner(System.in);

		// I/O File.separator "src" + File.separator + "main" ... 
		String filePath = Paths.get("src","main","java","com","iec","fuel_calculator", "utils", "european_cars_avg_consumption.csv").toString();
		cars = FileIO.readCarData(filePath);
//		Tools.print("Az autók listája: " + cars);
	}
	
	public InputData collectInfo() {
		Car userCar = getUserCar();
		String lengthUnit = "KM";
		double consumption = (userCar != null) ? userCar.getAvgConsumption() : getValidatedDoubleInput("Mennyi a fogyasztásod /100km?");
		if (askForConversion()) {
			consumption = convertLitersToGallon(consumption);
			lengthUnit = "MILE";
			Tools.print("A fogyasztás gallon/100mile-ben: " + consumption);
		}
		
//		String lengthUnit = getValidatedStringInput("Miben méred a távolságot? (km/mile)", Tools::checkLenghtUnit);
//		double consumption = getValidatedDoubleInput("Mennyi a fogyasztásod /" + lengthUnit + "?");
		double tripLength = getValidatedDoubleInput("Milyen hosszú útra mennél?");
		String currency = getValidatedCurrency(lengthUnit);
		double fuelPrice = getValidatedDoubleInput("Mi az aktuális benzinár " + currency + "-ban/-ben?");

		InputData data = new InputData(tripLength, lengthUnit, consumption, lengthUnit.equals("mile") ? "GAL" : "L",
				currency, fuelPrice);

//		data.summarize();
		scan.close();
		return data;
	}

	private Car getUserCar() {
		while (true) {
			Tools.print("Add meg az autód márkáját és típusát (pl. Audi A3)");
			String input = scan.nextLine().trim();
			for (Car car : cars) {
				if (input.equalsIgnoreCase(car.getBrand() + " " + car.getModel())) {
					Tools.print("Talált autó: " + car);
					return car;
				}
			}
			Tools.print("Az autó nem található, megpróbálod megadni ismét?");
			String response = scan.nextLine().trim();
			if (response.equalsIgnoreCase("nem")) {
				break;
			}
		}
		return null;
	}
	
	private boolean askForConversion() {
		Tools.print("Szeretnéd átváltani a fogyasztási adatokat gallon/100mile-ra? (igen/nem)");
		String response = scan.nextLine().trim();
		return response.equalsIgnoreCase("igen");
	}
	
	private double convertLitersToGallon(double lPer100) {
		// 1 liter / 100km -> 235.214 MPG 
		return 235.214 / lPer100;
	}
	
	private double getValidatedDoubleInput(String question) {
		while (true) {
			Tools.print(question);
			String input = scan.nextLine();

			if (StringFormatter.isStringValid(input)) {
				try {
					double res = Double.parseDouble(Tools.formatDouble(input));
					if (res > 0) {
						return res;
					} else {
						Tools.print("Kérlek 0-nál nagyobb számot adj meg!", false);
					}
				} catch (NumberFormatException e) {
					Tools.print("Kérlek érvényes számot adj meg!", e);
				}
			} else {
				Tools.print("Kérlek adj meg egy számot válaszként!", false);
			}
		}
	}

	private String getValidatedCurrency(String lengthUnit) {
		while (true) {
			Tools.print("Melyik pénznemet használod az alábbiak közül?");
			for (int i = 0; i < Tools.CURRENCIES.size(); i++) {
				Tools.print((i + 1) + ": " + Tools.CURRENCIES.get(i));
			}

			String input = scan.nextLine();
			if (StringFormatter.isStringValid(input)) {
				try {
					int index = Integer.parseInt(input.trim());
					if (index > 0 || index <= Tools.CURRENCIES.size()) {
						String currency = Tools.CURRENCIES.get(index - 1);
						if (Tools.fullCheck(currency) && validateCurrencyAndUnit(currency, lengthUnit)) {
							return currency;
						}
					} else {
						Tools.print("Kérlek [1-4] számot adj meg!", false);
					}
				} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
					Tools.print("Kérlek érvényes számot adj meg a listából!", false);
				}
			} else {
				Tools.print("Kérlek válassz a listából!", false);
			}
		}
	}

	private String getValidatedStringInput(String question, Validator validator /*, checker, validator metódus*/) {
		while (true) {
			Tools.print(question);
			String input = scan.nextLine();

			if (validator.validate(input)) {
				return input.trim().toUpperCase();
			} else {
				Tools.print("Kérlek érvényes értéket adj meg!", false);
			}
		}
	}

	private boolean validateCurrencyAndUnit(String currency, String lengthUnit) {
		if ((currency.equals("USD") && lengthUnit.equals("KM"))
				|| (!currency.equals("USD") && lengthUnit.equals("MILE"))) {
			Tools.print("Korábban " + lengthUnit + "-t adtál meg, biztos hogy " + currency + "-vel akarsz fizetni?");
			Tools.print("1: igen");
			Tools.print("2: nem");
			String res = scan.nextLine();
			return "1".equals(res.trim());
		}

		return true;
	}
	
	/*
	 * Ezzel az annotációval jelezzük a compliernek hogy egy funkcionális interface-t hozunk létre. 
	 * Kizárólag egy absztrakt metódust tartalmazhat.
	 * Lambda (->) vagy metódus referenciákkal (::) használható. 
	*/
	@FunctionalInterface
	public interface Validator {
		boolean validate(String in); // checkUnit
	}
}
