package util;

import java.io.File;

import communication.Message;

public interface IGetQuarterlyReport {
	public String setHomeBranchForQuarterlyReport();
	
	public void createReportInDb(Message message);
	
	public void setMessageToUser(String message);
	
	public File savePdfFile(String date);
	
	public String getPdfDate();
}
