package orders;

import util.constans;

/**
 * 
 * @author Ori, Malka.
 *  Class description: 
 *  This Abstract class is a base class
 *  for supply method.
 * @version 11/12/2021
 */
public class DeliverySupplyMethod extends AbatractSupplyMethod {
	/**
	 * Class members:
	 */

	/**
	 * The delivery type.
	 */
	public DeliveryType deliveryType;

	/*
	 * The receiver address.
	 */
	public String reciverAddress;

	/*
	 * The delivery fee.
	 */
	public double deliveryFee;

	/**
	 * ~For future use~ the number of participants in a multiple-participants
	 * delivery.
	 */
	private int numberOfParticipants = 1;

	/**
	 * Constructors:
	 */

	/**
	 * Constructor for regular delivery/robotic
	 * @param supplyId
	 * @param orderNumber
	 * @param receiverFirstName
	 * @param receiverLastName
	 * @param receiverPhoneNumber
	 * @param deliveryType
	 * @param reciverAddress
	 * @param deliveryFee
	 */
	public DeliverySupplyMethod(int supplyId, int orderNumber, String receiverFirstName, String receiverLastName,
			String receiverPhoneNumber, DeliveryType deliveryType, String reciverAddress, double deliveryFee) {
		super(supplyId, orderNumber, receiverFirstName, receiverLastName, receiverPhoneNumber);
		this.deliveryType = deliveryType;
		this.reciverAddress = reciverAddress;
		this.deliveryFee = calculateDeliveryFee();
	}
	
	/**
	 * Constructor for multiple participants delivery
	 * @param supplyId
	 * @param orderNumber
	 * @param receiverFirstName
	 * @param receiverLastName
	 * @param receiverPhoneNumber
	 * @param deliveryType
	 * @param reciverAddress
	 * @param numberOfParticipants
	 * @param deliveryFee
	 */
	public DeliverySupplyMethod(int supplyId, int orderNumber, String receiverFirstName, String receiverLastName,
			String receiverPhoneNumber, DeliveryType deliveryType, String reciverAddress, int numberOfParticipants,
			double deliveryFee) {
		super(supplyId, orderNumber, receiverFirstName, receiverLastName, receiverPhoneNumber);
		this.deliveryType = deliveryType;
		this.reciverAddress = reciverAddress;
		this.numberOfParticipants = numberOfParticipants;
		this.deliveryFee = calculateDeliveryFee();
	}
	
	
	/*
	 * Methods:
	 */
	
	/**
	 * This methods determine the delivery fee.
	 * @return double: the delivery fee
	 */
	private double calculateDeliveryFee() {
		double fee = 0.0;
		switch (deliveryType) {
		case REGULAR:
			fee = constans.REGULAR_DELIVERY_FEE_IN_NIS;
			break;
		case MULTI:
			double multiDiscount = numberOfParticipants * 5;
			fee = constans.REGULAR_DELIVERY_FEE_IN_NIS - multiDiscount;
			if (fee < constans.MINIMUM_MULTI_DELIVERY_FEE_IN_NIS)
				fee = constans.MINIMUM_MULTI_DELIVERY_FEE_IN_NIS;
			break;
		case ROBOTIC:
			fee = constans.ROBOTIC_DELIVERY_FEE_IN_NIS;
			break;
		default:
			break;
		}
		return fee;
	}

}
