package com.matteoveroni.mydiary.registration.model;

import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.user.model.bean.UserData;
import com.matteoveroni.mydiary.utilities.cryptography.Cryptographer;
import com.matteoveroni.mydiary.utilities.formatters.ExceptionsFormatter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class RegistrationModel {

	private final DAO databaseManager = DAO.getInstance();
	private final Cryptographer cryptograher = new Cryptographer();
	private static final Logger LOG = LoggerFactory.getLogger(RegistrationModel.class);

	private final String NAME_OF_THE_USER_TABLE = "USERS";

	public boolean createNewUser(UserData user) {
		try {
			if (!isUserExistent(user)) {
				String passwordHash = cryptograher.sha2_256(user.getPassword());
				user.setPassword(passwordHash);
				databaseManager.write(user);
				LOG.info("USER CREATED!");
				LOG.info("username = " + user.getUsername());
				LOG.info("password = " + user.getPassword());
				LOG.info("firstname = " + user.getFirstName());
				LOG.info("lastname = " + user.getLastName());
				LOG.info("age = " + user.getAge());
				return true;
			}
		} catch (Exception ex) {
			LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
		}
		return false;
	}

	public boolean isUserExistent(UserData user) {
		List<UserData> usersRetrieved;
		try {
			String QUERY = "select * from " + NAME_OF_THE_USER_TABLE + " where USERNAME=\'" + user.getUsername() + "\'";
			LOG.debug(" ---> QUERY -> " + QUERY);
			usersRetrieved = databaseManager.querySQL(QUERY, UserData.class);
			if (usersRetrieved.isEmpty()) {
				LOG.debug(" ---> User with this username doesn\'t exist yet");
				return false;
			}
		} catch (Exception ex) {
			LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
		}
		LOG.debug(" ---> User with this username exists yet");
		return true;
	}
}
