package com.iec.fuel_calculator.utils;

import java.io.BufferedReader;
//java.io -> java.nio
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import com.iec.fuel_calculator.model.CalculatedData;
import com.iec.fuel_calculator.model.Car;

public class FileIO {
	public static void appendResultToCSV(CalculatedData data, String consumptionUnit, String currency) {
		String calcConsumption = Double.toString(data.getCalcConsumption());
		String calcPrice = Double.toString(data.getCalcPrice());

		Path outputPath = Paths.get("output");
		if (!Files.exists(outputPath)) {
			try {
				Files.createDirectory(outputPath);
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}

		Path filePath = outputPath.resolve("result.csv");
		boolean fileExists = Files.exists(filePath);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toString(), true));
				@SuppressWarnings("deprecation")
				CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

			if (!fileExists) {
				csvPrinter.printRecord("Total consumption", "Consumption Unit", "Full Price", "Currency");
			}

			csvPrinter.printRecord(calcConsumption, consumptionUnit, calcPrice, currency);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// HashSet optimálisabb lenne rá
	public static List<Car> readCarData(String filePath) {
		List<Car> cars = new ArrayList<>();
		Path path = Paths.get(filePath);

		if (!Files.exists(path)) {
			System.err.println("File not found" + filePath);
			return cars;
		}

//		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) { // BufferedReader extends Reader ->
//																					// Reader implements Readable,
//																					// Closeable -> Closeable extends
//																					// AutoCloseable
//			String line;
//			// fontos, addig fut ahány sorunk van a fileban
//			while ((line = br.readLine()) != null) {
//				String[] values = line.split(",");
//				if (values.length == 3 && !values[0].equalsIgnoreCase("brand")) {
//					cars.add(new Car(values[0], values[1], Double.parseDouble(values[2])));
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		try (BufferedReader reader = Files.newBufferedReader(path);
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())){
			for (CSVRecord csvRecord : csvParser) {
				String brand = csvRecord.get("brand");
				String model = csvRecord.get("model");
				double avgConsumption = Double.parseDouble(csvRecord.get("avg_consumption"));
				
				cars.add(new Car(brand, model, avgConsumption));
			}
		} catch (FileNotFoundException e) {
			Tools.print("File nem található", e); // Sys exit!
		} catch (IOException e) {
			Tools.print("File művelet meghíusult", e);
		}
		
		return cars;
	}

}
