package com.vkondrat.experiment;

import com.vkondrat.experiment.entities.Employee;

import com.vkondrat.experiment.util.JPAUtil;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.persistence.EntityManager;

/**
 * Unit experiment for simple App.
 */
public class AppTest  extends TestCase {

    public AppTest(String testName) {
        super(testName);
    }
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    public void testSaveAndLoad() {

     /*   EntityManager em = JPAUtil.getInstance().getEm();
        em.getTransaction().begin();
        String name = "Vasya";
        int age = 25;
        Employee employee = new Employee(name, age);
        em.persist(employee);
        em.getTransaction().commit();

        Employee vasya = em.find(Employee.class, 1);
        assertEquals(name, vasya.getName());
        assertEquals(age, vasya.getAge());
        System.out.println("END!");
        em.close();*/
        System.out.println("test");

    }
    public void testApp()
    {
        assertTrue( true );
    }
}





