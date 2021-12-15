package orders;
import java.io.Serializable;

/**
 * 
 * @author Ori, Malka.
 *  Class description:
 *  This calss describes the Item Entity.
 * @version 11/12/2021
 */
public class Item implements Serializable {
	/**
	 * Class members:
	 */
	
	/**
	 * The supplier user id.
	 */
	public String SupplierUserId; 
	
	/**
	 * The item name, Unique for each supplier.
	 */
	public String ItemName; 
	
	/**
	 * The item category.
	 */
	public ItemCategory category;
	
	/**
	 * The item size.
	 */
	public ItemSize size;
	
	/**
	 * The item picture path (internal).
	 */
	public String picturePath;
	
	/**
	 * The item price.
	 */
	public double price;
	
	/**
	 * The item comment, used only to save info for a particular order item (changing).
	 * ~~Need to be not more then 45 characters~~
	 */
	public String comment;


	
	/**
	 * Constructors:
	 */
	
	/**
	 * Item regular constructor, not initiliazing comment.
	 * @param supplierUserId
	 * @param itemName
	 * @param category
	 * @param size
	 * @param picturePath
	 * @param price
	 */
	public Item(String supplierUserId, String itemName, ItemCategory category, ItemSize size, String picturePath,
			double price) {
		super();
		SupplierUserId = supplierUserId;
		ItemName = itemName;
		this.category = category;
		this.size = size;
		this.picturePath = picturePath;
		this.price = price;
	}

	/**
	 * Copy constructor
	 * 
	 * @param item
	 */
	public Item(Item item) {
		this.SupplierUserId = item.getSupplierUserId();
		this.ItemName = item.getItemName();
		this.category = item.getCategory();
		this.size = item.getSize();
		this.picturePath = item.getPicturePath();
		this.price = item.getPrice();
		this.comment = null;
	}
	
	/**
	 * Class Methods:
	 */
	
	/**
	 * Getters and Setters:
	 */
	public String getSupplierUserId() {
		return SupplierUserId;
	}

	public void setSupplierUserId(String supplierUserId) {
		SupplierUserId = supplierUserId;
	}

	public String getItemName() {
		return ItemName;
	}

	public void setItemName(String itemName) {
		ItemName = itemName;
	}

	public ItemCategory getCategory() {
		return category;
	}

	public void setCategory(ItemCategory category) {
		this.category = category;
	}

	public ItemSize getSize() {
		return size;
	}

	public void setSize(ItemSize size) {
		this.size = size;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}


	@Override
	public String toString() {
		return "Item [SupplierUserId=" + SupplierUserId + ", ItemName=" + ItemName + ", category=" + category
				+ ", size=" + size + ", picturePath=" + picturePath + ", price=" + price + ", comment=" + comment + "]";
	}
	
	
}
