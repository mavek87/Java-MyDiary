package com.matteoveroni.mydiary.model.manager.database;

import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import java.io.Serializable;
import java.sql.Connection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

    public enum dbOperation {

        SAVE, READ, DELETE, ALTER;
    }

    public DatabaseManager() {
        Configuration configuration = new Configuration();
        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    /*public static void main(String[] args) {
     Student student1 = new Student();
     student1.setName("nome1");
     student1.setId(2);
     student1.setDateBirth(GregorianCalendar.getInstance().getTime());

     Student student2 = new Student();
     student2.setName("nome2");
     student2.setId(3);
     student2.setDateBirth(GregorianCalendar.getInstance().getTime());

     sessionFactory = createSessionFactory();

     Session session = sessionFactory.openSession();

     alterDB(session, student1, "save");
     alterDB(session, student2, "save");

     session.close();
		
     //System.out.println(studenteLetto.getName());
     sessionFactory.close();

     }*/
    public void openSession() {
        session = sessionFactory.openSession();
    }

    public void write(Object object) {
        Session writeSession = sessionFactory.openSession();
        try {
            writeSession.beginTransaction();
            writeSession.save(object);
            writeSession.getTransaction().commit();
        } catch (Exception ex) {
            writeSession.getTransaction().rollback();
        }
        writeSession.close();
    }

    public void update(Object object) {
        Session writeSession = sessionFactory.openSession();
        try {
            writeSession.beginTransaction();
            writeSession.update(object);
            writeSession.getTransaction().commit();
        } catch (Exception ex) {
            writeSession.getTransaction().rollback();
        }
        writeSession.close();
    }

    public Object read(Class objectClass, Serializable serializable) {
        Session readSession = sessionFactory.openSession();
        Object objectReaded = null;
        try {
            readSession.beginTransaction();
            objectReaded = readSession.get(objectClass, serializable);
            readSession.getTransaction().commit();
        } catch (Exception ex) {
            readSession.getTransaction().rollback();
        }
        readSession.close();
        return objectReaded;
    }

    public Connection closeSession() {
        return session.close();
    }

    @Override
    public void dispose() {
        if (session.isOpen()) {
            session.close();
        }
        if (!sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
