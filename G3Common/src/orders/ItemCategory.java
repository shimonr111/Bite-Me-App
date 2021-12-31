package orders;

/**
 * 
 * @author Ori, Malka.
 * 
 *  Enum description:
 *  This Enum is used to describe the item categories.
 *  
 *  @version 11/12/2021
 */
public enum ItemCategory {
	
	SALAD {
		@Override
		public String toString() {
			return "Salad";
		}
	},
	FIRST {
		@Override
		public String toString() {
			return "First";
		}
	},
	MAIN {
		@Override
		public String toString() {
			return "Main";
		}
	},
	DESSERT {
		@Override
		public String toString() {
			return "Dessert";
		}
	},
	DRINK {
		@Override
		public String toString() {
			return "Drink";
		}
	}

}
