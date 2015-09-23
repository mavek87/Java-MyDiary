package com.matteoveroni.mydiary.registration.model;

import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.user.model.bean.UserData;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class RegistrationModel {

	private final DAO databaseManager = DAO.getInstance();

	private final String NAME_OF_THE_USER_TABLE = "USERS";
	private static final Logger LOG = LoggerFactory.getLogger(RegistrationModel.class);

	public boolean createNewUser(UserData user) {
		try {
			if (!isUserExistent(user)) {
				databaseManager.write(user);
				return true;
			}
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
		}
		return false;
	}

	public boolean isUserExistent(UserData user) {
		List<UserData> usersRetrieved;
		try {
			String QUERY = "select * from " + NAME_OF_THE_USER_TABLE + " where USERNAME=\'" + user.getUsername() + "\'";
			LOG.debug("QUERY -> " + QUERY);
			usersRetrieved = databaseManager.querySQL(QUERY, UserData.class);
			if (usersRetrieved.isEmpty()) {
				LOG.debug("User with this username doesn\'t exist yet");
				return false;
			}
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
		}
		LOG.debug("User with this username exists yet");
		return true;
	}
}
