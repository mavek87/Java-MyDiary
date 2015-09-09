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
public class DAOManager implements Disposable {

    private final SessionFactory sessionFactory;
    private final ServiceRegistry serviceRegistry;
    private Session session;
    private static DAOManager databaseManagerInstance;
    private static final Logger LOG = LoggerFactory.getLogger(DAOManager.class);

    private DAOManager() {
        Configuration configuration = new Configuration();
        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public enum Operations {

        SAVE, READ, DELETE, ALTER;
    }

    public enum ElementOnWhichOperate {

        FIRST, LAST, REQUESTED, PREVIOUS, NEXT;
    }

    public static DAOManager getInstance() {
        if (databaseManagerInstance == null) {
            databaseManagerInstance = new DAOManager();
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

    public void delete(Object object) {
        Session writeSession = null;
        Transaction transaction = null;
        try {
            writeSession = sessionFactory.getCurrentSession();
            transaction = writeSession.beginTransaction();
            writeSession.delete(object);
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

    public Object read(Class objectClass, Serializable serializable, ElementOnWhichOperate elementOnWhichOperate) {
        Session readSession = null;
        Transaction transaction = null;
        Object objectReaded = null;
        try {
            readSession = sessionFactory.getCurrentSession();
            transaction = readSession.beginTransaction();

            Criteria queryCriteria;
            switch (elementOnWhichOperate) {
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

    public List querySQL(String requestedQuery) {
        Session querySession = null;
        Transaction transaction = null;
        List queryResults = null;
        try {
            querySession = sessionFactory.getCurrentSession();
            transaction = querySession.beginTransaction();
            Query query = querySession.createSQLQuery(requestedQuery);
            queryResults = query.list();
            querySession.flush();
            transaction.commit();
        } catch (Exception ex) {
            handleExceptionDuringTransaction(ex, transaction);
        } finally {
            if (querySession != null && querySession.isOpen()) {
                querySession.close();
            }
        }
        return queryResults;
    }

//	IT DOESN'T WORKS NOW BUT IT COULD BE USEFULL TO IMPLEMENT IN THE FUTURE
//	public int getNumberOfElementsInATable(Class objectClass) {
//		int numberOfElementsInTheTable = 0;
//		try {
//			numberOfElementsInTheTable = (Integer) session.createCriteria(objectClass).setProjection(Projections.rowCount()).uniqueResult();
//		} catch (Exception ex) {
//		}
//		return numberOfElementsInTheTable;
//	}
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
