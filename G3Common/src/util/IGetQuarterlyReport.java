package util;

import communication.Message;

public interface IGetQuarterlyReport {
	public String setHomeBranchForQuarterlyReport();
	
	public void createReportInDb(Message message);
	
	public void setMessageToUser(String message);
	
	public void savePdfFile(String date);
}
