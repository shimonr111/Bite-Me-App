package orders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 *  Class description:
 *  This class wraps the item, to add a picture.
 * 
 * @author Ori, Malka.
 *  
 * @version 26/12/2021
 */
public class ItemWithPicture{
	
	/**
	 * The itemWithPicture item
	 */
	private Item item; 
	
	/**
	 * The itemWithPicture picture
	 */
	private ImageView picture;
	
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
	 * The item price.
	 */
	public double price;
	
	/**
	 * The item picture path (internal).
	 */
	public String picturePath;
	
	public ItemWithPicture(Item item) {
		this.item = item;
		this.picture = new ImageView(new Image(item.getPicturePath(),64,64,false,true));
		this.ItemName = item.getItemName();
		this.category = item.getCategory();
		this.size = item.getSize();
		this.price = item.getPrice();
		this.picturePath = item.getPicturePath();
		
	}
	
	/**
	 * Getters and Setters:
	 */
	
	public Item getItem() {
		return item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	public ImageView getPicture() {
		return picture;
	}
	
	public void setPicture(ImageView picture) {
		this.picture = picture;
	}

	public String getItemName() {
		return ItemName;
	}

	public void setItemName(String itemName) {
		ItemName = itemName;
		item.setItemName(itemName);
	}

	public ItemCategory getCategory() {
		return category;
	}

	public void setCategory(ItemCategory category) {
		this.category = category;
		item.setCategory(category);
	}

	public ItemSize getSize() {
		return size;
	}

	public void setSize(ItemSize size) {
		this.size = size;
		item.setSize(size);
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
		item.setPrice(price);
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
		item.setPicturePath(picturePath);
	}
	
	@Override
	public String toString() {
		return item.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		ItemWithPicture o = (ItemWithPicture) obj;
		return item.equals(o.getItem());
		
	}

}
