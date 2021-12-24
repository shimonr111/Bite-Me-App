package util;

import java.text.DateFormat;
import java.text.ParseException;
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
		Date d;
		try {
			d = dateFormat.parse(date + " " + time + ":00");
			return d;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	/**
	 * @param datetime: Expected datetime in yyyy/MM/dd HH:mm:ss format, received from mySql.
	 * @return Date: - MySql Format.
	 */
	public static Date buildMySqlDateTimeFormatFromDateTimeString(String datetime) {
		if(datetime != null) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d;
			try {
				d = dateFormat.parse(datetime);
				return d;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}

	/**
	 * @param date: MySql DateTime format variable.
	 * @return String: returns the date in string in MySql DateTime format.
	 */
	public static String convertMySqlDateTimeFormatToString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
