package com.matteoveroni.mydiary.database;

import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import java.io.Serializable;
import java.sql.Connection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author Matteo Veroni
 */
public class DatabaseManager implements Disposable {

	private final SessionFactory sessionFactory;
	private final ServiceRegistry serviceRegistry;
	private Session session;
	private static DatabaseManager databaseManagerInstance;

	private DatabaseManager() {
		Configuration configuration = new Configuration();
		configuration.configure();
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}

	public enum dbOperation {

		SAVE, READ, DELETE, ALTER;
	}

	public static DatabaseManager getInstance() {
		if (databaseManagerInstance == null) {
			databaseManagerInstance = new DatabaseManager();
		}
		return databaseManagerInstance;
	}
    
	public void openSession() {
		session = sessionFactory.openSession();
	}

	public void write(Object object) {
		Session writeSession = null;
		Transaction transaction = null;
		try {
			writeSession = sessionFactory.getCurrentSession();
			transaction = writeSession.beginTransaction();
			writeSession.save(object);
			writeSession.flush();
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (writeSession != null && writeSession.isOpen()) {
				writeSession.close();
			}
		}
	}

	public void update(Object object) {
		Session writeSession = null;
		Transaction transaction = null;
		try {
			writeSession = sessionFactory.getCurrentSession();
			transaction = writeSession.beginTransaction();
			writeSession.update(object);
			writeSession.flush();
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (writeSession != null && writeSession.isOpen()) {
				writeSession.close();
			}
		}
	}

	public Object read(Class objectClass, Serializable serializable) {
		Session readSession = null;
		Transaction transaction = null;
		Object objectReaded = null;
		try {
			readSession = sessionFactory.getCurrentSession();
			transaction = readSession.beginTransaction();
			objectReaded = readSession.get(objectClass, serializable);
			readSession.flush();
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (readSession != null && readSession.isOpen()) {
				readSession.close();
			}
		}
		return objectReaded;
	}

	public Connection closeSession() {
		return session.close();
	}

	@Override
	public void dispose() {
		if (session != null && session.isOpen()) {
			session.close();
		}
		if (sessionFactory != null && !sessionFactory.isClosed()) {
			sessionFactory.close();
		}
	}
}
