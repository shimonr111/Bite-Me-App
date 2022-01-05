package testpdfquarter;

import java.io.IOException;

import clientanalyze.AnalyzeMessageFromServer;
import communication.Message;
import controllers_gui.ViewSystemReportsScreenController;
import util.IGetQuarterlyReport;
import util.SupplierByReport;

public class ViewSystemReportsScreenController_Test {
	private ViewSystemReportsScreenController viewSystemReportsScreenController;
    public class StubGetQuarterlyReport implements IGetQuarterlyReport{
    	private String branchName;
    	private String returnMessage;
    	private SupplierByReport[][] returnedReportFromDb;
    	
		public SupplierByReport[][] getReturnedReportFromDb() {
			return returnedReportFromDb;
		}

		public void setReturnedReportFromDb(SupplierByReport[][] returnedReportFromDb) {
			this.returnedReportFromDb = returnedReportFromDb;
		}

		public void setReturnMessage(String returnMessage) {
			this.returnMessage = returnMessage;
		}

		public String getReturnMessage() {
			return returnMessage;
		}

		public String getBranchName() {
			return branchName;
		}

		public void setBranchName(String branchName) {
			this.branchName = branchName;
		}

		@Override
		public String setHomeBranchForQuarterlyReport() {
			return branchName;
		}

		@Override
		public void createReportInDb(Message message) {
			message.setObject(returnedReportFromDb);
			try {
				AnalyzeMessageFromServer.analyzeMessageFromServer(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void setMessageToUser(String message) {
			returnMessage=message;
		}

		@Override
		public void savePdfFile(String date) {
			returnMessage="Report Generated";
			
		}
    }
}
