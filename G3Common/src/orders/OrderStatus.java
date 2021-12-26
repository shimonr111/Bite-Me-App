package orders;

/**
 * 
 * @author Ori, Malka.
 *  Enum description:
 *  This Enum is used to describe the order status.
 *  @version 11/12/2021
 */
public enum OrderStatus {
	PENDING_APPROVAL {
		@Override
		public String toString() {
			return "Pending Approval";
		}
	},
	APPROVED {
		@Override
		public String toString() {
			return "Approved";
		}
	},
	UN_APPROVED {
		@Override
		public String toString() {
			return "Un-Approved";
		}
	},
	COMPLETED{
		@Override
		public String toString() {
			return "Completed";
		}
	}

}
