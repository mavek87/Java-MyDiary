package com.matteoveroni.mydiary.login.model;

import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.user.model.bean.UserData;
import com.matteoveroni.mydiary.utilities.cryptography.Cryptographer;
import com.matteoveroni.mydiary.utilities.formatters.ExceptionsFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class LoginModel {

    private final Cryptographer cryptographer = new Cryptographer();
    private final DAO databaseManager = DAO.getInstance();
    private final String USERS_TABLE = "USERS";

    private static final Logger LOG = LoggerFactory.getLogger(LoginModel.class);

    public UserData searchUserWithUsernameAndPassword(String username, String password) {
        UserData user = null;
        try {
            String QUERY_THAT_SEARCH_USERNAME_AND_PASSWORD = ""
                + "SELECT * FROM " + USERS_TABLE + " "
                + "WHERE username=\'" + username + "\' "
                + "AND password=\'" + cryptographer.sha2_256(password) + "\' "
                + "FETCH FIRST ROW ONLY";
            LOG.debug("QUERY that search user with a username and password -> " + QUERY_THAT_SEARCH_USERNAME_AND_PASSWORD);
            user = (UserData) databaseManager.querySQL(QUERY_THAT_SEARCH_USERNAME_AND_PASSWORD, UserData.class).get(0);
        } catch (IndexOutOfBoundsException ex1) {
            LOG.debug("Any user with this credentials finded by this query!");
        } catch (Exception ex2) {
            LOG.error(ExceptionsFormatter.toString(ex2));
        }
        return user;
    }
}
