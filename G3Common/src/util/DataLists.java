package util;

import java.util.ArrayList;
import java.util.Arrays;

public class DataLists {
	private static ArrayList<String> northCitys = new ArrayList<String> (Arrays.asList("Acre", "Afula","Hadera", "Haifa" ,"Karmiel", "N/A"));
	private static ArrayList<String> centerCitys = new ArrayList<String> (Arrays.asList("Bat-Yam","Herzliya", "Tel-Aviv" ,"N/A"));
	private static ArrayList<String> southCitys = new ArrayList<String> (Arrays.asList("Ashdod", "Ashkelon", "Beersheba", "Dimona", "Eilat","N/A"));
	private static ArrayList<String> Streets = new ArrayList<String> (Arrays.asList("Netiv Hen", "HaHistadrut", "Trumpeldor", "Rambam", "Yad-Labanim", "HaZait", "N/A"));
	private static ArrayList<String> phonePrefix = new ArrayList<String> (Arrays.asList("02","03","04","08","077","050","052","053","054","055","057","058"));
	private static String defaultSaladPicturePath = "File:///C:/G3BiteMe/Design/ItemsImageBank/defaultSalad.jpg";
	private static String defaultDessertPicturePath = "File:///C:/G3BiteMe/Design/ItemsImageBank/defaultDessert.jpg";
	private static String defaultDrinkPicturePath = "File:///C:/G3BiteMe/Design/ItemsImageBank/defaultDrink.jpg";
	private static String defaultFirstPicturePath = "File:///C:/G3BiteMe/Design/ItemsImageBank/defaultFirst.jpg";
	private static String defaulMainPicturePath = "File:///C:/G3BiteMe/Design/ItemsImageBank/defaultMain.jpg";
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
	public static String getDefaultSaladPicturePath() {
		return defaultSaladPicturePath;
	}
	public static String getDefaultDessertPicturePath() {
		return defaultDessertPicturePath;
	}
	public static String getDefaultDrinkPicturePath() {
		return defaultDrinkPicturePath;
	}
	public static String getDefaultFirstPicturePath() {
		return defaultFirstPicturePath;
	}
	public static String getDefaultMainPicturePath() {
		return defaulMainPicturePath;
	}
}
