import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class Main {

	public static final String[] headers = { "show_id", "type", "title", "director", "cast", "country", "date_added",
			"release_year", "rating", "duration", "listed_in", "description" };

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String[]> data = readAllDataAtOnce(
				"C:\\Users\\HP\\eclipse-workspace\\Netflix-Data-Reader\\input\\netflix_titles.csv");
		List<String[]> resultdata = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		String choice = "";
		do {
			System.out.println(
					"\nEnter Choice: \n 1. TV Show \n​ 2. listed_in: Horror Movies \n 3. type: Movie where country: India​ \n​ 4. Take the input of date range and aggregate data based on field date_added​ \n 5. END");
			choice = sc.next();

			if (choice.equals("END"))
				break;

			System.out.println("Top N Records: ");
			int n = sc.nextInt();

			switch (choice) {
			case "1":

				resultdata = filterDataByType(data);
				printData(resultdata, n);
				break;

			case "2":
				resultdata = filterDataByListedIn(data);
				printData(resultdata, n);
				break;

			case "3":
				resultdata = filterDataByCountry(data);
				printData(resultdata, n);
				break;

			case "4":
				System.out.println("Enter start date (dd-MMM-yy): ");
				String startDate = sc.next();

				System.out.println("Enter end date (dd-MMM-yy): ");
				String endDate = sc.next();

				resultdata = filterDataByDateAdded(data, "01-Jan-19", "31-Dec-22");
				printData(resultdata, n);
				break;

			default:
				System.out.println("not found");
				break;

			}
		} while (!choice.equals("END"));

		sc.close();

	}

	// Java code to illustrate reading a
	// all data at once
	public static List<String[]> readAllDataAtOnce(String file) {
		List<String[]> allData = new ArrayList<>();
		long startTime = System.currentTimeMillis();
		try {
			// Create an object of file reader
			// class with CSV file as a parameter.
			FileReader filereader = new FileReader(file);

			// create csvReader object and skip first Line
			CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
			allData = csvReader.readAll();

		} catch (Exception e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("CSV Parsing Time : " + (endTime - startTime) + " ms");

		return allData;
	}

	public static List<String[]> filterDataByType(List<String[]> data) {
		long startTime = System.currentTimeMillis();
		List<String[]> resultData = new ArrayList<>();

		for (String[] row : data) {
			if ("TV Show".equals(row[1])) {
				resultData.add(row);
			}
		}

		long endTime = System.currentTimeMillis();
		System.out.println("Filtering Time : " + (endTime - startTime) + " ms");
		return resultData;
	}

	public static List<String[]> filterDataByListedIn(List<String[]> data) {
		long startTime = System.currentTimeMillis();
		List<String[]> resultData = new ArrayList<>();

		for (String[] row : data) {
			if (row[10].contains("Horror Movies")) {
				resultData.add(row);
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Filtering Time : " + (endTime - startTime) + " ms");
		return resultData;
	}

	public static List<String[]> filterDataByCountry(List<String[]> data) {
		long startTime = System.currentTimeMillis();
		List<String[]> resultData = new ArrayList<>();

		for (String[] row : data) {
			if (row[5].contains("India")) {
				resultData.add(row);
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Filtering Time : " + (endTime - startTime) + " ms");
		return resultData;
	}

	public static List<String[]> filterDataByDateAdded(List<String[]> data, String startDate, String endDate) {
		long startTime = System.currentTimeMillis();
		List<String[]> resultData = new ArrayList<>();

		Date startDateObj = null;
		Date endDateObj = null;

		try {
			startDateObj = new SimpleDateFormat("dd-MMM-yy").parse(startDate);
			endDateObj = new SimpleDateFormat("dd-MMM-yy").parse(endDate);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (startDateObj != null && endDateObj != null) {
			for (String[] row : data) {
				Date dateAddedObj = null;
				try {
					dateAddedObj = new SimpleDateFormat("dd-MMM-yy").parse(row[6]);

				} catch (ParseException e) {
					e.printStackTrace();
				}

				if (dateAddedObj != null && dateAddedObj.after(startDateObj) && dateAddedObj.before(endDateObj)) {
					resultData.add(row);
				}
			}
		}

		long endTime = System.currentTimeMillis();
		System.out.println("Filtering Time : " + (endTime - startTime) + " ms");
		return resultData;
	}

	public static void printData(List<String[]> data) {

		// print Data
		for (String[] row : data) {
			for (String cell : row) {
				System.out.print(cell + ",");
			}
			System.out.println();
		}

	}

	public static void printData(List<String[]> data, int n) {

		// print Data
		int i = 0;
		for (String[] row : data) {

			if (i == n) {
				break;
			}

			for (String cell : row) {
				System.out.print(cell + ",");
			}

			System.out.println();

			i++;

		}

	}

}
