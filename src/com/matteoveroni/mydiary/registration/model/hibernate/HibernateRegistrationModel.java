package com.matteoveroni.mydiary.registration.model.hibernate;

import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.registration.model.RegistrationModel;
import com.matteoveroni.mydiary.user.model.hibernate.PersistentHibernateUser;
import com.matteoveroni.mydiary.user.model.User;

/**
 *
 * @author Matteo Veroni
 */
public class HibernateRegistrationModel implements RegistrationModel {

    private final DAO databaseManager = DAO.getInstance();

    @Override
    public void createNewUser(User user) {
        databaseManager.write(user);
    }

    @Override
    public boolean isUserExistent(User user) {
        return (databaseManager.read(PersistentHibernateUser.class, null, DAO.ElementOnWhichOperate.REQUESTED) != null);
    }

}
