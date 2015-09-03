package com.matteoveroni.mydiary.login.model;

import com.matteoveroni.mydiary.user.model.User;

/**
 *
 * @author Matteo Veroni
 */
public interface LoginModel {

	public User getUser(String username);

}
