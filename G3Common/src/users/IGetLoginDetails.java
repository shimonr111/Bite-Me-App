package users;

import communication.Message;

public interface IGetLoginDetails {

	Login getLogin();

	boolean isLoginDataValidFromUser(Login login);
	
	void  userReturnDataFromDB(Message message);

}
