package guiserver;

import users.User;

/**
 * @author  Lior, Guzovsky.
 * Class description: 
 * This class will be user for the observable list to handle the clients.
 * 
 * @version 14/12/2021
 */
public class ClientDoc {
	
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

	private String ipAddress;
	private String hostName;
	private String status;
	
	public ClientDoc(String ipAddress,String hostName,String status) {
		this.ipAddress=ipAddress;
		this.hostName=hostName;
		this.status=status;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ClientDoc) {
			return (ipAddress.equals(((ClientDoc) obj).getIpAddress())) && (hostName.equals(((ClientDoc) obj).getHostName()));
		}
		return false;
	}

}
