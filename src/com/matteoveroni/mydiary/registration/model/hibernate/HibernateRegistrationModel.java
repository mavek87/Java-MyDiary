package com.matteoveroni.mydiary.registration.model.hibernate;

import com.matteoveroni.mydiary.database.DAOManager;
import com.matteoveroni.mydiary.registration.model.RegistrationModel;
import com.matteoveroni.mydiary.user.model.User;
import com.matteoveroni.mydiary.user.model.hibernate.PersistentHibernateUser;

/**
 *
 * @author Matteo Veroni
 */
public class HibernateRegistrationModel implements RegistrationModel {

    private final DAOManager databaseManager = DAOManager.getInstance();

    @Override
    public void createNewUser(User user) {
        databaseManager.write(user);
    }

    @Override
    public boolean isUserExistent(User user) {
        return (databaseManager.read(PersistentHibernateUser.class, null, DAOManager.ElementOnWhichOperate.REQUESTED) != null);
    }

}
