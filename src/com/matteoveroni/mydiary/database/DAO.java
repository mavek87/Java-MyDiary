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
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class DAO implements Disposable {

	private final SessionFactory sessionFactory;
	private final ServiceRegistry serviceRegistry;
	private Session session;
	private static DAO databaseManagerInstance;
	private static final Logger LOG = LoggerFactory.getLogger(DAO.class);

	private DAO() {
		Configuration configuration = new Configuration();
		configuration.configure();
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}

	public enum Operations {

		SAVE, READ, DELETE, ALTER;
	}

	public enum ElementsOnWhichOperate {

		FIRST, LAST, REQUESTED, PREVIOUS, NEXT;
	}

	public static DAO getInstance() {
		if (databaseManagerInstance == null) {
			databaseManagerInstance = new DAO();
		}
		return databaseManagerInstance;
	}

	public Session openSession() {
		return sessionFactory.openSession();
	}

	public void closeSession(Session session) {
		if (session != null && session.isOpen()) {
			session.close();
		}
	}

	public void write(Object object) {
		Session writeSession = null;
		try {
			writeSession = sessionFactory.getCurrentSession();
			writeSession.beginTransaction();
			writeSession.save(object);
			writeSession.flush();
			writeSession.getTransaction().commit();
		} catch (Exception ex) {
			handleExceptionDuringTransaction(ex, writeSession.getTransaction());
		} finally {
			if (writeSession != null && writeSession.isOpen()) {
				writeSession.close();
			}
		}
	}

	public void update(Object object) {
		Session writeSession = null;
		try {
			writeSession = sessionFactory.getCurrentSession();
			writeSession.beginTransaction();
			writeSession.update(object);
			writeSession.flush();
			writeSession.getTransaction().commit();
		} catch (Exception ex) {
			handleExceptionDuringTransaction(ex, writeSession.getTransaction());
		} finally {
			if (writeSession != null && writeSession.isOpen()) {
				writeSession.close();
			}
		}
	}

	public void delete(Object object) {
		Session writeSession = null;
		try {
			writeSession = sessionFactory.getCurrentSession();
			writeSession.beginTransaction();
			writeSession.delete(object);
			writeSession.flush();
			writeSession.getTransaction().commit();
		} catch (Exception ex) {
			handleExceptionDuringTransaction(ex, writeSession.getTransaction());
		} finally {
			if (writeSession != null && writeSession.isOpen()) {
				writeSession.close();
			}
		}
	}

	public Object read(Class objectClass, Serializable serializable, ElementsOnWhichOperate elementsOnWhichOperate) {
		Session readSession = null;
		Object objectReaded = null;
		try {
			readSession = sessionFactory.getCurrentSession();
			readSession.beginTransaction();

			Criteria queryCriteria;
			switch (elementsOnWhichOperate) {
				case FIRST:
					queryCriteria = readSession.createCriteria(objectClass)
						.setFirstResult(0)
						.setMaxResults(1);
					objectReaded = queryCriteria.list().get(0);
					break;
				case LAST:
					queryCriteria = readSession.createCriteria(objectClass)
						.addOrder(Order.desc("id"))
						.setFirstResult(0)
						.setMaxResults(1);
					objectReaded = queryCriteria.list().get(0);
					break;
				case REQUESTED:
					objectReaded = readSession.get(objectClass, serializable);
					break;
				case PREVIOUS:
					queryCriteria = readSession.createCriteria(objectClass)
						.addOrder(Order.desc("id"))
						.add(Restrictions.lt("id", serializable))
						.setFirstResult(0)
						.setMaxResults(1);
					objectReaded = queryCriteria.list().get(0);
					break;
				case NEXT:
					queryCriteria = readSession.createCriteria(objectClass)
						.add(Restrictions.gt("id", serializable))
						.setFirstResult(0)
						.setMaxResults(1);
					objectReaded = queryCriteria.list().get(0);
					break;
			}
			readSession.flush();
			readSession.getTransaction().commit();
		} catch (Exception ex) {
			handleExceptionDuringTransaction(ex, readSession.getTransaction());
		} finally {
			if (readSession != null && readSession.isOpen()) {
				readSession.close();
			}
		}
		return objectReaded;
	}

	public List readAll(Class objectClass) {
		Session readSession = null;
		List readedElements = new ArrayList<>();
		try {
			readSession = sessionFactory.getCurrentSession();
			readSession.beginTransaction();
			Criteria queryCriteria = readSession.createCriteria(objectClass);
			readedElements = queryCriteria.list();
			readSession.flush();
			readSession.getTransaction().commit();
		} catch (Exception ex) {
			handleExceptionDuringTransaction(ex, readSession.getTransaction());
		} finally {
			if (readSession != null && readSession.isOpen()) {
				readSession.close();
			}
		}
		return readedElements;
	}

	public List querySQL(String requestedQuery) {
		Session querySession = null;
		List queryResults = null;
		try {
			querySession = sessionFactory.getCurrentSession();
			querySession.beginTransaction();
			Query query = querySession.createSQLQuery(requestedQuery);
			queryResults = query.list();
			querySession.flush();
			querySession.getTransaction().commit();
		} catch (Exception ex) {
			handleExceptionDuringTransaction(ex, querySession.getTransaction());
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
		LOG.error("An exception occurred! Last transaction was rolled back! Exception --> " + ex);
	}
}
