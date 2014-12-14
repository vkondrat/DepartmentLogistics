package com.vkondrat.experiment.util;

import com.vkondrat.experiment.entities.Employee;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Администратор on 27.11.2014.
 */
public class NewTest extends TestCase {
    public NewTest(String testName) {
        super(testName);
    }
    public static Test suite() {
        return new TestSuite(NewTest.class);
    }



    public void test1() {


        JPAUtil.setPersistentUnitName("test-persistence");
        EntityManager em = JPAUtil.getInstance().getEm();
        em.getTransaction().begin();
        String name = "Vasya";
        int age = 25;
        Employee employee = new Employee(name, age);
        em.persist(employee);
        em.getTransaction().commit();

        Employee vasya = em.find(Employee.class, 1);
        assertEquals(name, vasya.getName());
        assertEquals(age, vasya.getAge());
        System.out.println("End");

        em.close();
    }
}
