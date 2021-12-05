package analyze;
/**
 * @author Alexander, Martinov
 *
 *         Interface description: 
 *         This is an interface which is used to implement the listeners(observers) design pattern
 *         
 * @version 05/12/2021
 */
public interface AnalyzeListener {
	//from here it's all client listener methods
	default void clientIpConfirmed() {
		return;
	}
	default void clientIpNotConfirmed() {
		return;
	}
	
	//from here it's all server listener methods
	default void serverConfirmIp(String string) {
		return;
	}
}
