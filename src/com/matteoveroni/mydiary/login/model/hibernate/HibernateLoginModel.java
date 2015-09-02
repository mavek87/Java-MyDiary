package com.matteoveroni.mydiary.login.model.hibernate;

import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.login.model.LoginModel;
import com.matteoveroni.mydiary.user.ApplicationUser;

/**
 *
 * @author Matteo Veroni
 */
public class HibernateLoginModel implements LoginModel {

	private final DAO databaseManager = DAO.getInstance();

	@Override
	public ApplicationUser getFirstUser() {
		return (ApplicationUser) databaseManager.read(ApplicationUser.class, null, DAO.ElementOnWhichOperate.FIRST);
	}
}
