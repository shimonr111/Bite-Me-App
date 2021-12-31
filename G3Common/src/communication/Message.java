package communication;

import java.io.Serializable;

/** 
 * 
 * @author Ori, Malka.
 * @author Lior, Guzovsky.
 * 
 * Class description: 
 * This class present the Home Screen and responsible for the functionality of this screen 
 * This class is used for sending and 
 * getting messages from and to 
 * the server.
 * 
 * @version 03/12/2021
 */
public class Message implements Serializable{
	
	/**
	 * Class members description:
	 */

	/**
	 * This is the serial version UID of the class Message 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is an enum that describes
	 * the operation we want to do in our system. 
	 * such as: 
	 * Login, Logout, Create Order, etc.
	 */
	private Task task;
	
	/**
	 * This is an Enum that returns
	 * the answer from the server.
	 * such as:
	 * CREATE_USER_PORTAL_FOR_CUSTOMER, etc.
	 */
	private Answer answer;

	/**
	 * This is the Object that is 
	 * sent to the server such as:
	 * Order,Login, etc.
	 */
	private Object object;
	
	/**
	 * Constructor of the class.
	 * 
	 * @param task
	 * @param answer
	 * @param object
	 */
	public Message(Task task, Answer answer, Object object) {
		super();
		this.task = task;
		this.answer = answer;
		this.object = object;
	}
	
	/**
	 * Another constructor of the class.
	 * 
	 * @param task
	 * @param object
	 */
	public Message(Task task, Object object) {
		super();
		this.task = task;
		this.object = object;
	}
	
	/**
	 * This section is for the 
	 * Setters and Getters of the 
	 * Class Customer
	 */
	
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	
}
