package com.matteoveroni.mydiary.login.model.bean;

import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.login.model.LoginModel;
import com.matteoveroni.mydiary.user.model.bean.User;
import com.matteoveroni.mydiary.user.model.bean.HibernateUserBean;

/**
 *
 * @author Matteo Veroni
 */
public class HibernateLoginModel implements LoginModel {

    private final DAO DatabaseManager = DAO.getInstance();
    private final String NAME_OF_THE_USER_TABLE = "USERDATA";

    @Override
    public User getUser(String searchedUsername) {
        User userRetrieved = null;
        try {

            userRetrieved = (User) DatabaseManager.read(HibernateUserBean.class, searchedUsername, DAO.ElementsOnWhichOperate.REQUESTED);
//
//            String query = "select * from " + NAME_OF_THE_USER_TABLE + " where USERNAME=\'" + searchedUsername + "\'";
//            userRetrieved = (User) DatabaseManager.querySQL(query);
        } catch (Exception ex) {
        }
        return userRetrieved;
    }
}