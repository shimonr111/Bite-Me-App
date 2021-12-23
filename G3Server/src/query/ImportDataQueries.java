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
		boolean isImported=false;
		try {
		while(rs.next()) {
			isImported=Query.insertRowIntoRegistrationTable(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
					rs.getInt(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14),rs.getString(15),
					rs.getInt(16),rs.getDouble(17));
			if(isImported)
				break;
		}
		rs.close();
		if(!isImported) {
			BiteMeServerUI.console.add("Users Management Data imported.\n");
			getBranchManagers();
			BiteMeServerUI.console.add("Branch managers added\n");
			getCeoBiteMe();
			BiteMeServerUI.console.add("Ceo BiteMe added\n");
			getSuppliers();
			getCompanies();
			getHrManagers();
			getSupplierWorkers();
			
			
		}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	

	}
	
	/**
	 * get all the workers of the registered suppliers.
	 */
	public static void getSupplierWorkers() {
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
			Query.insertOneRowIntoSupplierWorkerTable(supplierWorker);
		}
	}
	/**
	 * get all the HR managers of the companies .
	 */
	public static void getHrManagers() {
		ArrayList<HrManager> hrManagers = new ArrayList<>();
		ArrayList<Login> hrLogin = new ArrayList<>();
		ResultSet rs;
		rs = Query.getRowsFromTableInDB("registration", "userType= 'hrmanager'");
		Query.deleteRowFromTableInDbByPrimaryKey("registration", "userType= 'hrmanager'");
		try {
			while(rs.next()) {
				CreditCard card= new CreditCard(rs.getString(10),rs.getString(11),rs.getString(12));
				Query.insertOneRowIntoCreditCardTable(card);
				hrManagers.add(new HrManager(rs.getString(2),ConfirmationStatus.valueOf(rs.getString(3
						)), rs.getString(4),rs.getString(5),Branch.valueOf(rs.getString(6)), 
						false, rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(15), BudgetType.MONTHLY,PositionType.HR,
						0));
				System.out.println(rs.getString(15));
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
			Query.insertOneRowIntoHrManagerTable(hr);
		}
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
			Query.insertOneRowIntoCompanyTable(company);
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
			Query.insertOneRowIntoSupplierTable(supplier);	
		}
	}
	
	/**
	 * get the ceo bite me and insert it into his table in the internal db.
	 */
	public static void getCeoBiteMe() {
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
			Query.inserOneRowIntoCeoBiteMeTable(ceo);
		}
		
		
	}
	
	/**
	 * get the branchmanagers after clicking on import button.
	 */
	public static void getBranchManagers(){
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
			Query.insertOneRowIntoBranchManagerTable(bm);
			
			
		}
	}
}
