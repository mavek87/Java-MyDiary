package com.matteoveroni.mydiary.database;

import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class DatabaseManager implements Disposable {

	private final SessionFactory sessionFactory;
	private final ServiceRegistry serviceRegistry;
	private Session session;
	private static DatabaseManager databaseManagerInstance;
	private static final Logger LOG = LoggerFactory.getLogger(DatabaseManager.class);

	private DatabaseManager() {
		Configuration configuration = new Configuration();
		configuration.configure();
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}

	public enum Operations {

		SAVE, READ, DELETE, ALTER;
	}

	public enum ElementOnWhichOperate {

		FIRST, LAST;
	}

	public static DatabaseManager getInstance() {
		if (databaseManagerInstance == null) {
			databaseManagerInstance = new DatabaseManager();
		}
		return databaseManagerInstance;
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
			handleExceptionDuringTransaction(ex, transaction);
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
			handleExceptionDuringTransaction(ex, transaction);
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
			handleExceptionDuringTransaction(ex, transaction);
		} finally {
			if (readSession != null && readSession.isOpen()) {
				readSession.close();
			}
		}
		return objectReaded;
	}

	public Object read(Class objectClass, ElementOnWhichOperate elementOnWhichOperate) {
		Session readSession = null;
		Transaction transaction = null;
		Object readedObject = null;
		try {
			readSession = sessionFactory.getCurrentSession();
			transaction = readSession.beginTransaction();
			
			Criteria queryCriteria = readSession.createCriteria(objectClass);
			switch (elementOnWhichOperate) {
				case FIRST:
					queryCriteria.setFirstResult(0);
					queryCriteria.setMaxResults(1);
					break;
				case LAST:
					queryCriteria.addOrder(Order.desc("id"));
					queryCriteria.setFirstResult(0);
					queryCriteria.setMaxResults(1);
					break;
			}
			readedObject = queryCriteria.list().get(0);

			readSession.flush();
			transaction.commit();
		} catch (Exception ex) {
			handleExceptionDuringTransaction(ex, transaction);
		} finally {
			if (readSession != null && readSession.isOpen()) {
				readSession.close();
			}
		}
		return readedObject;
	}

	public List readAll(Class objectClass) {
		Session readSession = null;
		Transaction transaction = null;
		List readedElements = new ArrayList<>();
		try {
			readSession = sessionFactory.getCurrentSession();
			transaction = readSession.beginTransaction();
			Criteria queryCriteria = readSession.createCriteria(objectClass);
			readedElements = queryCriteria.list();
			readSession.flush();
			transaction.commit();
		} catch (Exception ex) {
			handleExceptionDuringTransaction(ex, transaction);
		} finally {
			if (readSession != null && readSession.isOpen()) {
				readSession.close();
			}
		}
		return readedElements;
	}

	public List query(String requestedQuery) {
		Session querySession = null;
		Transaction transaction = null;
		List queryResults = null;
		try {
			querySession = sessionFactory.getCurrentSession();
			transaction = querySession.beginTransaction();
			Query query = querySession.createQuery(requestedQuery);
			querySession.flush();
			transaction.commit();
			queryResults = query.list();
		} catch (Exception ex) {
			handleExceptionDuringTransaction(ex, transaction);
		} finally {
			if (querySession != null && querySession.isOpen()) {
				querySession.close();
			}
		}
		return queryResults;
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

	private void handleExceptionDuringTransaction(Exception ex, Transaction transactionWithException) {
		if (transactionWithException != null) {
			transactionWithException.rollback();
		}
		LOG.error("An exception occurred! Last transaction was rolled back! Exception: " + ex);
	}
}
