package serveranalyze;

import java.util.Date;
import java.util.TimerTask;

/**
 * @author Mousa, Srour.
 * Class description: 
 * 
 * This is the scheduler class , it runs immediately after the server connection to DB.
 * we get the date that of connection , every 10 minutes we check the 4 cases below 
 * and we call relevant methods.
 * 
 * @version 23/12/2021
 */
public class Scheduler extends TimerTask {
	
	Date current = new Date();
	@Override
	public void run() {
		Date newDate = new Date();
		
		if(current.getDay()!= newDate.getDay()) {
			System.out.println("New Day");
			//TBD : Add actions after one day.
		}
		if(current.getMonth() != newDate.getMonth()) {
			System.out.println("New Month");
			//TBD : Add actions after one month.
		}
		
		if(current.getDay() > newDate.getDay()) {
			System.out.println("New Week");
			//TBD : Add actions after one week.
		}
		
		if((current.getMonth() /3 ) + 1 !=  (newDate.getMonth()/3) + 1) {
			System.out.println("New Quarater");
			//TBD : Add actions after one quarter.
		}
		
		current=newDate;
		
	}

}
