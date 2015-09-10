package com.matteoveroni.mydiary.registration.model.bean;

import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.registration.model.RegistrationModel;
import com.matteoveroni.mydiary.user.model.bean.User;
import com.matteoveroni.mydiary.user.model.bean.HibernateUserBean;

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
        return (databaseManager.read(HibernateUserBean.class, null, DAO.ElementsOnWhichOperate.REQUESTED) != null);
    }

}
