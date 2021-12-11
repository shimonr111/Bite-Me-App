package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Ori, Malka.
 *  Class description:
 *  This class helps us to handle time and date (DB<->GUI).
 * @version 11/12/2021
 */
public class DateTimeHandler {

	/**
	 * 
	 * @param date: Expected date in DD/MM/YYYY format, received from the JavaFX
	 *              date picker.
	 * @param time:
	 * @return Date: - MySql Format DateTime variable.
	 */
	public static Date buildMySqlDateTimeFormatFromTextFields(String date, String time) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		//split the date
		String[] dateArray = date.split("/");
		int years = Integer.parseInt(dateArray[2]);
		int months = Integer.parseInt(dateArray[1]);
		int days = Integer.parseInt(dateArray[0]);
		
		//split the time
		String[] timeArray = date.split(":");
		int hours = Integer.parseInt(timeArray[0]);
		int minutes = Integer.parseInt(timeArray[1]);
		int seconds = 0;
		
		//set the new date
		Date d = new Date (years - 1900, months - 1, days);
		d.setHours(hours);
		d.setMinutes(minutes);
		d.setSeconds(seconds);
		
		return d;
	}

	/**
	 * @param date: MySql DateTime format variable.
	 * @return String: returns the date in string in MySql DateTime format.
	 */
	public static String convertMySqlDateTimeFormatToString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return dateFormat.format(date);
	}

	/**
	 * @return String: returns the date in string in MySql DateTime format.
	 */
	public static String getTimeNowInMySqlDateTimeFormat_String() {
		return convertMySqlDateTimeFormatToString(getTimeNowInMySqlDateTimeFormat_Date());
	}

	/**
	 * @return Date: returns the date now.
	 */
	public static Date getTimeNowInMySqlDateTimeFormat_Date() {
		return new Date();
	}

}
