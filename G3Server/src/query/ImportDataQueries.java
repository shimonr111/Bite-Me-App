package query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bitemeserver.BiteMeServerUI;
import users.Branch;
import users.BranchManager;
import users.BudgetType;
import users.CeoBiteMe;
import users.Company;
import users.ConfirmationStatus;
import users.CreditCard;
import users.HrManager;
import users.Login;
import users.PositionType;
import users.Supplier;
import users.SupplierWorker;
import users.WorkerPosition;

/**
 * /**
 * @author  Mousa, Srour
 * Class description: 
 * In this class we have all the methods that belong to
 * the import action .
 * 
 * @version 22/12/2021
 *
 */
public class ImportDataQueries {
	
	/**
	 * we create a new connection , to the external DB , 
	 * we get all the table from external DB , and put it into our DB.
	 * we get the branch managers and the ceo which are confirmed already and need no registration.
	 * 
	 */
	public static void getDataFromExternalDB() {
		
		ResultSet rs = Query.getExternalDB();
		boolean isImportedIntoRegistrationTable=false;
		boolean isImportedIntoLoginTable = false;
		boolean isImported =false, toConsole=false;
		try {
		while(rs.next()) {
			if(rs.getString(1).equals("user")) {
				ResultSet rs2 = Query.getColumnWithConditionFromTableInDB("login","userID","userID='"+rs.getString(2)+"'");
				if(rs2.isBeforeFirst())
					isImportedIntoLoginTable=true;
			}
			if(!isImportedIntoLoginTable) {
				isImportedIntoRegistrationTable=Query.insertRowIntoRegistrationTable(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
						rs.getInt(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14),rs.getString(15),
						rs.getInt(16),rs.getDouble(17));
				if(isImportedIntoRegistrationTable&& !toConsole) {
					if(!BiteMeServerUI.console.contains("Data Already Imported.\n"))
						BiteMeServerUI.console.add("Data Already Imported.\n");
						toConsole=true;
				}
			}
			
//			if(isImportedIntoRegistrationTable)
//				isImported = true;
			isImportedIntoLoginTable=false;
		}
		rs.close();
		if(!isImported) {
			BiteMeServerUI.console.add("Users Management Data imported.\n");
			if(getBranchManagers())
				BiteMeServerUI.console.add("Branch managers added\n");
			if(getCeoBiteMe())
				BiteMeServerUI.console.add("Ceo BiteMe added\n");
			getSuppliers();
			getCompanies();
			if(getHrManagers())
				BiteMeServerUI.console.add("Hr Managers added\n");
			if(getSupplierWorkers())
				BiteMeServerUI.console.add("Supplier Workers added\n");
			
			
		}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	

	}
	
	/**
	 * get all the workers of the registered suppliers.
	 */
	public static boolean getSupplierWorkers() {
		ArrayList<SupplierWorker> workers = new ArrayList<>();
		ArrayList<Login> workersLogin = new ArrayList<>();
		ResultSet rs;
		rs = Query.getRowsFromTableInDB("registration", "userType= 'supplierworker'");
		Query.deleteRowFromTableInDbByPrimaryKey("registration", "userType= 'supplierworker'");
		try {
			while(rs.next()) {
				Supplier supplier = new Supplier(rs.getString(10),null,Branch.NORTH,null,null,0,ConfirmationStatus.PENDING_APPROVAL);
				workers.add(new SupplierWorker(rs.getString(2),ConfirmationStatus.valueOf(rs.getString(3)), rs.getString(4),rs.getString(5),Branch.valueOf(rs.getString(6)), 
						false, rs.getString(8),rs.getString(9),supplier,WorkerPosition.valueOf(rs.getString(11))));
				workersLogin.add(new Login(rs.getString(13),rs.getString(14)));
			}
			rs.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		for(int i=0;i<workers.size();i++) {
			Login login = workersLogin.get(i);
			SupplierWorker supplierWorker = workers.get(i);
			Query.insertOneRowIntoLoginTable(login.getUserName(), login.getPassword(), supplierWorker.getUserId(), "supplierworker");
			if(Query.insertOneRowIntoSupplierWorkerTable(supplierWorker))
				return false;
		}
		return true;
	}
	/**
	 * get all the HR managers of the companies .
	 */
	public static boolean getHrManagers() {
		ArrayList<HrManager> hrManagers = new ArrayList<>();
		ArrayList<Login> hrLogin = new ArrayList<>();
		ResultSet rs;
		rs = Query.getRowsFromTableInDB("registration", "userType= 'hrmanager'");
		Query.deleteRowFromTableInDbByPrimaryKey("registration", "userType= 'hrmanager'");
		try {
			while(rs.next()) {
				Company company = LoginQueries.getCompany(rs.getString(15));
				hrManagers.add(new HrManager(rs.getString(2),ConfirmationStatus.valueOf(rs.getString(3)), rs.getString(4),rs.getString(5),Branch.valueOf(rs.getString(6)), 
						false, rs.getString(8),rs.getString(9),company));
				hrLogin.add(new Login(rs.getString(13),rs.getString(14)));
			}
			rs.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		for(int i=0;i<hrManagers.size();i++) {
			Login login = hrLogin.get(i);
			HrManager hr = hrManagers.get(i);
			Query.insertOneRowIntoLoginTable(login.getUserName(), login.getPassword(), hr.getUserId(), "hrmanager");
			if(Query.insertOneRowIntoHrManagerTable(hr))
				return false;
		}
		return true;
	}
	
	/**
	 * get all the companies from the registration table , and move them into the company table on DB.
	 */
	public static void getCompanies() {
		ArrayList<Company> companiesList = new ArrayList<>();
		ResultSet rs;
		rs = Query.getRowsFromTableInDB("registration", "userType= 'company'");
		Query.deleteRowFromTableInDbByPrimaryKey("registration", "userType= 'company'");
		try {
			while(rs.next()) {

				companiesList.add(new Company(rs.getString(4),ConfirmationStatus.valueOf(rs.getString(3)),rs.getString(5),rs.getString(8),Integer.parseInt(rs.getString(2))));
			}
			rs.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		for(int i=0;i<companiesList.size();i++) {
			Company company = companiesList.get(i);
			if(Query.insertOneRowIntoCompanyTable(company))
				break;
		}
		
	}
	
	/**
	 * get all the suppliers from the registration table , and move them into the supplier table on DB.
	 */
	public static void getSuppliers() {
		ArrayList<Supplier> suppliersList= new ArrayList<>();
		ResultSet rs;
		rs = Query.getRowsFromTableInDB("registration", "userType= 'supplier'");
		Query.deleteRowFromTableInDbByPrimaryKey("registration", "userType= 'supplier'");
		try {
		while(rs.next()) {
			suppliersList.add(new Supplier(rs.getString(2),rs.getString(4),Branch.valueOf(rs.getString(6)),rs.getString(8),rs.getString(9),rs.getDouble(17),ConfirmationStatus.valueOf(rs.getString(3))));
		}
		rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		for(int i=0;i<suppliersList.size();i++) {
			Supplier supplier = suppliersList.get(i);
			if(Query.insertOneRowIntoSupplierTable(supplier))
				break;
		}
	}
	
	/**
	 * get the ceo bite me and insert it into his table in the internal db.
	 */
	public static boolean getCeoBiteMe() {
		CeoBiteMe ceo=null;
		Login ceoLogin=null;
		ResultSet rs = Query.getRowsFromTableInDB("registration", "userType= 'ceobiteme'");
		Query.deleteRowFromTableInDbByPrimaryKey("registration", "userType= 'ceobiteme'");
		try {
			if(rs.next()) {
				ceo = new CeoBiteMe (rs.getString(2),ConfirmationStatus.valueOf(rs.getString(3)), rs.getString(4),rs.getString(5),Branch.valueOf(rs.getString(6)), 
						false, rs.getString(8),rs.getString(9));
				ceoLogin = new Login(rs.getString(13),rs.getString(14));
				
				}
				rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		if(ceo!= null) {
			Query.insertOneRowIntoLoginTable(ceoLogin.getUserName(), ceoLogin.getPassword(), ceo.getUserId(), "ceobiteme");
			if(Query.inserOneRowIntoCeoBiteMeTable(ceo))
				return false;
		}
		return true;		
	}
	
	/**
	 * get the branchmanagers after clicking on import button.
	 */
	public static boolean getBranchManagers(){
		ArrayList<BranchManager> branchManagersList= new ArrayList<>();
		ArrayList<Login> branchManagersLoginList = new ArrayList<>();
		ResultSet rs;
		rs = Query.getRowsFromTableInDB("registration", "userType= 'branchmanager'");
		Query.deleteRowFromTableInDbByPrimaryKey("registration", "userType= 'branchmanager'");
		try {
		while(rs.next()) {
			branchManagersList.add(new BranchManager(rs.getString(2),ConfirmationStatus.valueOf(rs.getString(3)), rs.getString(4),rs.getString(5),Branch.valueOf(rs.getString(6)), 
					false, rs.getString(8),rs.getString(9)));
			branchManagersLoginList.add(new Login(rs.getString(13),rs.getString(14)));
		}
		rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		for(int i=0;i<branchManagersList.size();i++) {
			Login login = branchManagersLoginList.get(i);
			BranchManager bm = branchManagersList.get(i);
			Query.insertOneRowIntoLoginTable(login.getUserName(), login.getPassword(), bm.getUserId(), "branchmanager");
			if(Query.insertOneRowIntoBranchManagerTable(bm))
				return false;
			
			
		}
		return true;
	}
}
