package com.matteoveroni.mydiary.login.model;

import com.matteoveroni.mydiary.user.model.bean.UserData;

/**
 *
 * @author Matteo Veroni
 */
public interface LoginModel {

	public UserData searchUser(String username);

}
