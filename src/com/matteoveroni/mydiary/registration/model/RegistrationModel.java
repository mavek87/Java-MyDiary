package com.matteoveroni.mydiary.registration.model;

import com.matteoveroni.mydiary.user.model.User;

/**
 *
 * @author Matteo Veroni
 */
public interface RegistrationModel {

    public void createNewUser(User user);

    public boolean isUserExistent(User user);
}
