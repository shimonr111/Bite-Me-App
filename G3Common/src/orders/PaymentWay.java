package orders;
/**
 * Class description: 
 * This is an Enum for 
 * the way we pay in the system
 * used for picking the fields for 
 * the combo box in the payment configuration
 * screen in the order process.
 */

/**
 * 
 * @author Lior, Guzovsky.
 * @author Shimon, Rubin.
 * 
 * @version 17/12/2021
 */
public enum PaymentWay{

	CASH {
		@Override
		public String toString() {
			return "Cash";
		}
	},
	CREDIT_CARD {
		@Override
		public String toString() {
			return "Credit card";
		}
	},
	ACCOUNT_BALANCE {
		@Override
		public String toString() {
			return "Account balance";
		}
	},
	EMPLOYEE_BUDGET {
		@Override
		public String toString() {
			return "Employee budget";
		}
	}
}
