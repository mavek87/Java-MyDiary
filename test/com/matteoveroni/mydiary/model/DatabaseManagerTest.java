package com.matteoveroni.mydiary.model;

import com.matteoveroni.mydiary.model.manager.database.DatabaseManager;
import java.io.Serializable;
import java.sql.Connection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Matteo Veroni
 */
public class DatabaseManagerTest {
    
    @Test
    public void testOpenSession() {
        System.out.println("openSession");
        DatabaseManager instance = new DatabaseManager();
        instance.openSession();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of write method, of class DatabaseManager.
     */
    @Test
    public void testWrite() {
        System.out.println("write");
        Object object = null;
        DatabaseManager instance = new DatabaseManager();
        instance.write(object);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class DatabaseManager.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        Object object = null;
        DatabaseManager instance = new DatabaseManager();
        instance.update(object);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of read method, of class DatabaseManager.
     */
    @Test
    public void testRead() {
        System.out.println("read");
        Class objectClass = null;
        Serializable serializable = null;
        DatabaseManager instance = new DatabaseManager();
        Object expResult = null;
        Object result = instance.read(objectClass, serializable);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of closeSession method, of class DatabaseManager.
     */
    @Test
    public void testCloseSession() {
        System.out.println("closeSession");
        DatabaseManager instance = new DatabaseManager();
        Connection expResult = null;
        Connection result = instance.closeSession();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dispose method, of class DatabaseManager.
     */
    @Test
    public void testDispose() {
        System.out.println("dispose");
        DatabaseManager instance = new DatabaseManager();
        instance.dispose();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
