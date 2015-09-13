package com.matteoveroni.mydiary.registration.model;

import com.matteoveroni.mydiary.user.model.bean.UserData;

/**
 *
 * @author Matteo Veroni
 */
public interface RegistrationModel {

    public void createNewUser(UserData user);

    public boolean isUserExistent(UserData user);
}
