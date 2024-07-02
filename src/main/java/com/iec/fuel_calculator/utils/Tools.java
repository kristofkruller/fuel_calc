package com.iec.fuel_calculator.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import com.iec.utils_lib.logger.MyLogger;

//import static java.lang.System.err;
//import static java.lang.System.out;

public class Tools {
	public static final List<String> CURRENCIES = Arrays.asList("EUR","USD","GBP","HUF");
	public static final List<String> DIST = Arrays.asList("KM","MILE");
	private static final Logger logger = MyLogger.getLogger(Tools.class.getName() + "consoleLogger");
	private static final Logger fileLogger = MyLogger.getFileLogger(Tools.class.getName() + "fileLogger");
	
	public static <T> void print(T in) {
//		out.println(in); // logger.info
		logger.info(in.toString());
	}
	
	public static <T> void print(T in, boolean isErrWithSep) {
		if (isErrWithSep) {
//			err.println(in);
			logger.warning(in.toString());
			logger.warning("-");
		} else {
//			err.println(in);
			logger.warning(in.toString());
		}
	}
	
	public static <T> void print (T in, Exception e) {
		logger.warning(in.toString());
		logger.warning("-");
		fileLogger.warning(e.getMessage() + "\n" + Arrays.deepToString(e.getStackTrace()));
	}
	
//	public static boolean checkString(String input) {
//		return input != null && !input.isBlank();
//	}
	
	public static String formatDouble(String input) {
		input = input.trim();
		if(input.contains(",")) input = input.replaceAll(",", ".");
		
		return input;
	}
	
	public static boolean fullCheck(String in) {
		List<String> fullCheck = new ArrayList<>(DIST);
		fullCheck.addAll(CURRENCIES);
		
		in = in.trim().toUpperCase();
		return fullCheck.contains(in);
	}
	
	public static boolean checkLenghtUnit(String in) {
		in = in.trim().toUpperCase();
		return DIST.contains(in);
	}
}
