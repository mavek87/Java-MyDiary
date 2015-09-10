package com.matteoveroni.mydiary.registration.model;

import com.matteoveroni.mydiary.user.model.bean.User;

/**
 *
 * @author Matteo Veroni
 */
public interface RegistrationModel {

    public void createNewUser(User user);

    public boolean isUserExistent(User user);
}
