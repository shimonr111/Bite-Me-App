package util;

import java.util.ArrayList;
import java.util.Arrays;

public class DataLists {
	private static ArrayList<String> northCitys = new ArrayList<String> (Arrays.asList("Acre", "Afula","Hadera", "Haifa" ,"Karmiel", "N/A"));
	private static ArrayList<String> centerCitys = new ArrayList<String> (Arrays.asList("Bat-Yam","Herzliya", "Tel-Aviv" ,"N/A"));
	private static ArrayList<String> southCitys = new ArrayList<String> (Arrays.asList("Ashdod", "Ashkelon", "Beersheba", "Dimona", "Eilat","N/A"));
	private static ArrayList<String> Streets = new ArrayList<String> (Arrays.asList("Street1", "Street2", "N/A"));
	private static ArrayList<String> phonePrefix = new ArrayList<String> (Arrays.asList("02","03","04","08","077","050","052","053","054","055","057","058"));
	
	public static ArrayList<String> getNorthCitys() {
		return northCitys;
	}
	public static ArrayList<String> getCenterCitys() {
		return centerCitys;
	}
	public static ArrayList<String> getSouthCitys() {
		return southCitys;
	}
	public static ArrayList<String> getStreets() {
		return Streets;
	}
	public static ArrayList<String> getPhonePrefix() {
		return phonePrefix;
	}
}
