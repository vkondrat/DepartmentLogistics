package com.vkondrat.experiment;

import com.vkondrat.experiment.entities.Employee;

import com.vkondrat.experiment.util.JPAUtil;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


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

        EntityManager em = JPAUtil.getInstance().getEm();

        em.getTransaction().begin();
        em.createQuery("DELETE FROM Employee").executeUpdate();
        em.persist(new Employee("Vasya", 25));
        em.persist(new Employee("Petro", 30));
        em.getTransaction().commit();

        Query query = em.createQuery("SELECT e FROM Employee e");
        List<Employee> list = (List<Employee>) query.getResultList();
        for (Employee employee : list) {
            System.out.println(employee.getName());
        }


        em.close();
        System.out.println("END");


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
        em.close();
        System.out.println("test"); */

    }
    public void testApp()
    {
        assertTrue( true );
    }
}





