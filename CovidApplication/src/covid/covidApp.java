package covid;



/*
Johnathan Chi
Student ID: 202467624
CS 211
12/6/2022
Worldwide COVID app that returns confirmed cases, deaths or recoveries

User must either input date to find data.
Or can input number of confirmed cases, deaths, recoveries to find date.

Depending on input, program will select between two methods to obtain data.

If user gives date to program, it will use the method CovidData in class covidData.
If user wants to compare two countries, the method CovidData will be run twice.

CovidData iterates through json file until user specified date is reached.
Once reached, get confirmed cases, deaths, and recoveries. Then, add to hashmap MapForData.

If user wants to find a specific date based on deaths, or cases, run method
CovidDataDaily in covidData. 

CovidDataDaily iterates through all json values in json file. After each
date iteration, add 1 to hashmap MapToFindDate key. 
Depending on user input for type, 
if json equals to either cases, deaths, or recoveries, get data from json file, add to hashmap MapToFindDate value.
Once finished this hashmap will hold all data of one type(Confirmed cases, deaths) for every date.
Once hashmap is filled, use method getDate to calculate date to find.
If user input is greater than or equal to hashmap value, get hashmap key which holds number of iterations.
Then add number of iterations to 2020-01-23, which is the start of the data collection,
to find the date which user input crossed, using calander class.
*/

import java.util.*;
import java.util.Map.Entry; // import the HashMap class
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class covidApp {

	public static HashMap<String, Long> MapForData = new HashMap<String, Long>();
	public static HashMap<Integer, Long> MapToFindDate = new HashMap<Integer, Long>();
	static Scanner consol = new Scanner(System.in);
	static String cases1;
	static int dateToFind;

	public static void main(String[] args) throws Exception {
		// intro sentences
		System.out.println("Welcome to Indvidual Project - Fall 2022");
		System.out.println("Enter countries lowercase and with - for spaces. EX: south-africa");
		System.out.println(
				"This program does not work for the following countries: [British Virgin Islands, US Virgin Islands, "
						+ "netherlands-antilles, Mayotte, guadaloupe\nNorthern Mariana Islands, Christmas Island, Guernsey, Saint Barthélemy, Faroe Islands, Falkland Islands, Pitcairn Islands, Hong Kong]");
		System.out.println(
				"\nSouth Korea is: (korea-south), North Korea is: (korea-north), Vatican City is: (holy-see-vatican-city-state)");
		System.out.println();

		inputCountryName();

	}

	// Handles countries
	public static void inputCountryName() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// create instance of the Calendar class and set the date to the given date
		Calendar cal = Calendar.getInstance();

		try {

			System.out.println("How many countries to analyze? (1/2?): ");
			int numOfCountries = consol.nextInt();

			// if num of countries is 1
			if (numOfCountries == 1) {

				System.out.println(
						"Do you want to: \n1) Display COVID data for a specific date? \n2) Find date of confirmed cases, deaths, or recoveries? ");
				int type = consol.nextInt();

				// if user wants to display data for specific date for a country
				if (type == 1) {
					System.out.println("What country to analyze?");
					String country = consol.next();

					System.out.println("What date to analyze?(Enter Format: 0000-00-00)");
					String date = consol.next();
					System.out.println("(Fetching data...May take a while, please wait)");
					covidData.CovidData(country, date);
					System.out.print(country + ": ");
					getCovidData();

				}

				// if user wants to find a specific date for data
				else if (type == 2) {
					System.out.print("Find date for number of \nCases \nDeaths \nRecoveries: ");
					String typeTwo = consol.next();
					typeTwo.toLowerCase();

					// if user wants to find cases
					if (typeTwo.equals("cases")) {
						System.out.println("How many total cases are you looking for?");
						String cases = consol.next();
						cases1 = cases;
					}
					// if user wants to find deaths
					else if (typeTwo.equals("deaths")) {
						System.out.println("How many total deaths are you looking for?");
						String cases = consol.next();
						cases1 = cases;

						// if user wants to find recoveries
					} else if (typeTwo.equals("recoveries")) {
						System.out.println("How many total recoveries are you looking for?");
						String cases = consol.next();
						cases1 = cases;

					} else {
						System.out.println("Not valid input");
						System.exit(0);
					}

					System.out.println("What country to analyze?");
					String country = consol.next();
					System.out.println("(Fetching data...May take a while, please wait)");
					covidData.CovidDataDaily(country, typeTwo);
					getDate(cases1);

					// parse first date, then add number of days from hashmap
					cal.setTime(sdf.parse("2020-01-23"));
					cal.add(Calendar.DAY_OF_MONTH, dateToFind);
					String dateAfter = sdf.format(cal.getTime());

					System.out.println(cases1 + " confirmed total " + typeTwo + " crossed on " + dateAfter);
					System.out.println(
							"[If crossed on current date, likely exceeds actual number of cases - hashmap out of bounds!]");
				}

			}
			// if num of countries is 2, analyze two countries
			else if (numOfCountries == 2)

			{
				System.out.println("What is the first country to analyze?");
				String country = consol.next();

				System.out.println("What is the second country to analyze?");
				String country2 = consol.next();

				System.out.println("What date to analyze?(Enter Format: 0000-00-00)");
				String date = consol.next();
				System.out.println("(Fetching data...May take a while, please wait)");

				covidData.CovidData(country, date);
				System.out.print(country + ": ");
				getCovidData();
				covidData.CovidData(country2, date);
				System.out.print(country2 + ": ");
				getCovidData();

			}
		} catch (Exception ex) {
			System.out.println("Invalid input");

		}
	}

	// prints out hashmap for country data
	public static void getCovidData() throws Exception {
		MapForData = covidData.getCovidDataNow();
		System.out.println(MapForData);

	}

	// Gets data from hashmap to find correct date for data entered by user
	public static void getDate(String cases) throws Exception {

		// parses cases into int
		int caseDate = Integer.parseInt(cases1);

		MapToFindDate = covidData.getDateNow();
		// Iterate through hashmap, once found where value entered by user is greater
		// than data in hashmap, dateToFind equals hashmap key(number of loops)
		for (Entry<Integer, Long> ent : MapToFindDate.entrySet()) {
			if (caseDate >= ent.getValue()) {

				dateToFind = ent.getKey();
			}
		}
	}
}