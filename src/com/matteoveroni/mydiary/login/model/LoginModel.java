package com.matteoveroni.mydiary.login.model;

import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.user.model.bean.UserData;

/**
 *
 * @author Matteo Veroni
 */
public class LoginModel{

    private final DAO DatabaseManager = DAO.getInstance();
    private final String NAME_OF_THE_USER_TABLE = "USERDATA";

    public UserData searchUser(String searchedUsername) {
        UserData userRetrieved = null;
        try {

//            userRetrieved = (UserData) DatabaseManager.read(UserData.class, searchedUsername, DAO.ElementsOnWhichOperate.REQUESTED);
//
            String query = "select * from " + NAME_OF_THE_USER_TABLE + " where USERNAME=\'" + searchedUsername + "\'";
            userRetrieved = (UserData) DatabaseManager.querySQL(query);
        } catch (Exception ex) {
        }
        return userRetrieved;
    }
}
