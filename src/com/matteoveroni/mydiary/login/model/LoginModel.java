package com.matteoveroni.mydiary.login.model;

import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.user.model.bean.UserData;
import java.util.List;

/**
 *
 * @author Matteo Veroni
 */
public class LoginModel {

	private final DAO DatabaseManager = DAO.getInstance();
	private final String NAME_OF_THE_USER_TABLE = "USERS";

	public UserData searchUser(String searchedUsername) {
		List<UserData> usersRetrieved;
		UserData searchedUser = null;
		try {
			//            userRetrieved = (UserData) DatabaseManager.read(UserData.class, searchedUsername, DAO.ElementsOnWhichOperate.REQUESTED);

			String QUERY = "select * from " + NAME_OF_THE_USER_TABLE + " where USERNAME=\'" + searchedUsername + "\'";

			usersRetrieved = DatabaseManager.querySQL(QUERY);

			if (usersRetrieved.size() > 1) {
				throw new Exception("Database invalid - multiple users with same username!");
			} else {
				searchedUser = usersRetrieved.get(0);
			}
		} catch (Exception ex) {
		}
		return searchedUser;
	}
}
