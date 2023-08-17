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

If user wants to find a specific date based on deaths, recoveries, or cases, run method
CovidDataDaily in covidData. 

CovidDataDaily iterates through all json values in json file. After each
date iteration, add 1 to hashmap MapToFindDate key. 
Depending on user input for type, 
if json equals to either cases, deaths, or recoveries, get data from json file, add to hashmap value.
Once finished this hashmap will hold all data of one type(Confirmed cases, deaths, recoveries) for every date.
Once hashmap is filled, use method getDate to calculate date to find.
If user input is greater than or equal to hashmap value, get hashmap key which holds number of iterations.
Then add number of iterations to 2020-01-23, which is the start of the data collection,
to find the date which user input crossed, using calander class.
*/
import java.io.*;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.HashMap;

public class covidData extends covidApp {

	// Handles country and user defined cases/deaths/recoveries, uses date
	public static boolean CovidData(String countryName, String date) throws Exception {
		MapForData.clear();
		boolean validCountryName = false;

		try {

			// Create a URL instance

			String firstPartURL = "https://api.covid19api.com/total/country/";
			String theURL = firstPartURL + countryName;
			URL url = new URL(theURL);

			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine = br.readLine();
			JSONParser parser = new JSONParser();
			JSONArray arr = (JSONArray) parser.parse(inputLine);

			String dateToFind = date + "T00:00:00Z";

			// Loop through each item
			for (Object i : arr) {
				JSONObject data1 = (JSONObject) i;

				if (dateToFind.equals((String) data1.get("Date"))) {

					Long con = (Long) data1.get("Confirmed");
					Long deaths = (Long) data1.get("Deaths");
					Long recovered = (Long) data1.get("Recovered");

					MapForData.put("total cases", con);
					MapForData.put("total deaths ", deaths);
					// Only add recovered if it is not equal to zero
					if (recovered != 0) {
						MapForData.put("total recoveries ", recovered);
					}
				}
			}
			validCountryName = true;

		} catch (Exception ex) {
			return validCountryName = false;
		}
		return validCountryName;

	}

	// Handles country and user defined cases/deaths/recoveries, does not use date,
	// when found data, insert into number of loops into hashmap
	public static boolean CovidDataDaily(String countryName, String type) throws Exception {

		boolean validCountryName = false;
		JSONParser parser = new JSONParser();

		try {

			// Create a URL instance
			String firstPartURL = "https://api.covid19api.com/total/country/";
			String theURL = firstPartURL + countryName;
			URL url = new URL(theURL);

			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputLine = br.readLine();
			JSONArray arr = (JSONArray) parser.parse(inputLine);

			// Set number of loop to 0
			Integer date = 0;

			// if user entered cases
			if (type.equals("cases")) {
				// Loop through each item
				for (Object i : arr) {
					JSONObject data1 = (JSONObject) i;
					// if json equals confirmed, get confirmed
					if (arr.equals("Confirmed"))
						;

					Long con = (Long) data1.get("Confirmed");

					// set hashmap as number of loops, cases
					MapToFindDate.put(date, con);

					// add loop counter
					date++;
				}
			}

			// if user entered deaths
			else if (type.equals("deaths")) {
				// Loop through each item
				for (Object i : arr) {
					JSONObject data1 = (JSONObject) i;

					// if json equals deaths, get confirmed
					if (arr.equals("Deaths"))
						;

					// set hashmap as number of loops, deaths
					Long deaths = (Long) data1.get("Deaths");
					MapToFindDate.put(date, deaths);

					// add loop counter
					date++;
				}

			}
			// if user entered recoveries
			else if (type.equals("recoveries")) {
				// Loop through each item
				for (Object i : arr) {
					JSONObject data1 = (JSONObject) i;
					// if json equals recovered, get confirmed
					if (arr.equals("Recovered"))
						;

					Long recovered = (Long) data1.get("Recovered");

					// set hashmap as number of loops, recovered
					MapToFindDate.put(date, recovered);

					// add loop counter
					date++;
				}
			}

			validCountryName = true;

		} catch (

		Exception ex) {
			return validCountryName = false;
		}
		return validCountryName;

	}

	public static HashMap<String, Long> getCovidDataNow() {
		return MapForData;
	}

	public static HashMap<Integer, Long> getDateNow() {
		return MapToFindDate;
	}
}
