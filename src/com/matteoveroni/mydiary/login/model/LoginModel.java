package com.matteoveroni.mydiary.login.model;

import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.user.model.bean.UserData;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class LoginModel {

	private final DAO databaseManager = DAO.getInstance();
	private final String NAME_OF_THE_USER_TABLE = "USERS";

	private static final Logger LOG = LoggerFactory.getLogger(LoginModel.class);

	public UserData searchUser(String searchedUsername) {
		List<UserData> usersRetrieved;
		UserData searchedUser = null;
		try {
			String QUERY = "select * from " + NAME_OF_THE_USER_TABLE + " where USERNAME=\'" + searchedUsername + "\'";
			LOG.debug("---> QUERY -> " + QUERY);
			usersRetrieved = databaseManager.querySQL(QUERY, UserData.class);
			if (usersRetrieved.size() == 1) {
				searchedUser = usersRetrieved.get(0);
			} else {
				throw new Exception("Database invalid - multiple users with same username!");
			}
		} catch (Exception ex) {
			LOG.error("---> " + ex.getMessage());
		}
		return searchedUser;
	}
}
