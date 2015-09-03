package com.matteoveroni.mydiary.login.model.hibernate;

import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.login.model.LoginModel;
import com.matteoveroni.mydiary.user.UserData;

/**
 *
 * @author Matteo Veroni
 */
public class HibernateLoginModel implements LoginModel {

    private final DAO databaseManager = DAO.getInstance();

    @Override
    public UserData getFirstUser() {
        return (UserData) databaseManager.read(UserData.class, null, DAO.ElementOnWhichOperate.FIRST);
    }

    public UserData getUser(String searchedUsername) {
        
        String nameOfTheUserTable = "ApplicationUser";
        
        String query = "SELECT * FROM " + nameOfTheUserTable + 
                       " WHERE USERNAME=\'" + searchedUsername +"\';";
        
        return (UserData) databaseManager.query(query);
    }
}
