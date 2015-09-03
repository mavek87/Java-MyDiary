package com.matteoveroni.mydiary.login.model;

import com.matteoveroni.mydiary.user.model.hibernate.HibernateUser;

/**
 *
 * @author Matteo Veroni
 */
public interface LoginModel {

	public HibernateUser getFirstUser();

}
