package com.matteoveroni.mydiary.login.model;

import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.user.model.bean.UserData;
import com.matteoveroni.mydiary.utilities.formatters.ExceptionsFormatter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class LoginModel {
	private final DAO databaseManager = DAO.getInstance();
	private final String USERS_TABLE = "USERS";

	private static final Logger LOG = LoggerFactory.getLogger(LoginModel.class);

	public UserData searchUser(String searchedUsername) {
		List<UserData> usersRetrieved;
		UserData searchedUser = null;
		try {
			String QUERY_THAT_SEARCH_USERNAME = "select * from " + USERS_TABLE + " where USERNAME=\'" + searchedUsername + "\'";
			LOG.debug(" ---> QUERY that search username -> " + QUERY_THAT_SEARCH_USERNAME);
			usersRetrieved = databaseManager.querySQL(QUERY_THAT_SEARCH_USERNAME, UserData.class);
			if (usersRetrieved.size() == 1) {
				searchedUser = usersRetrieved.get(0);
			}
		} catch (Exception ex) {
			LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
		}
		return searchedUser;
	}
}
