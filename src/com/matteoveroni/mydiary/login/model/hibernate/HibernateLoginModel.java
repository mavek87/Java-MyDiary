package com.matteoveroni.mydiary.login.model.hibernate;

import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.login.model.LoginModel;
import com.matteoveroni.mydiary.user.model.hibernate.HibernateUser;

/**
 *
 * @author Matteo Veroni
 */
public class HibernateLoginModel implements LoginModel {

    private final DAO databaseManager = DAO.getInstance();

    @Override
    public HibernateUser getFirstUser() {
        return (HibernateUser) databaseManager.read(HibernateUser.class, null, DAO.ElementOnWhichOperate.FIRST);
    }

    public HibernateUser getUser(String searchedUsername) {
        
        String nameOfTheUserTable = "ApplicationUser";
        
        String query = "SELECT * FROM " + nameOfTheUserTable + 
                       " WHERE USERNAME=\'" + searchedUsername +"\';";
        
        return (HibernateUser) databaseManager.query(query);
    }
}
