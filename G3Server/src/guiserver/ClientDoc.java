package guiserver;

/**
 * Class description: 
 * This class will be user for the observable list to handle the clients.
 */

/**
 * 
 * @author Lior, Guzovsky.
 * 
 * @version 14/12/2021
 */
public class ClientDoc{
	
	
	/**
     * This is the getters and setters
     * 
     * @return String
     */
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * String of ip address clients
	 */
	private String ipAddress;
	
	/**
	 * String of host name clients
	 */
	private String hostName;
	
	/**
	 * String of status clients
	 */
	private String status;
	
	/**
	 * This is the constructor of the class
	 * 
	 * @param ipAddress
	 * @param hostName
	 * @param status
	 */
	public ClientDoc(String ipAddress,String hostName,String status) {
		this.ipAddress=ipAddress;
		this.hostName=hostName;
		this.status=status;
	}
	
	/**
	 * This method check if client ip and host name is correct
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ClientDoc) {
			return (ipAddress.equals(((ClientDoc) obj).getIpAddress())) && (hostName.equals(((ClientDoc) obj).getHostName()));
		}
		return false;
	}

}
