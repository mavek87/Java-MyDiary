package com.matteoveroni.mydiary.registration.model.bean;

import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.registration.model.RegistrationModel;
import com.matteoveroni.mydiary.user.model.bean.UserData;
import com.matteoveroni.mydiary.user.model.bean.UserData;

/**
 *
 * @author Matteo Veroni
 */
public class HibernateRegistrationModel implements RegistrationModel {

    private final DAO databaseManager = DAO.getInstance();

    @Override
    public void createNewUser(UserData user) {
        databaseManager.write(user);
    }

    @Override
    public boolean isUserExistent(UserData user) {
        return (databaseManager.read(UserData.class, null, DAO.ElementsOnWhichOperate.REQUESTED) != null);
    }

}
