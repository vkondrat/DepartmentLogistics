package com.vkondrat.experiment;

import com.vkondrat.experiment.entities.Employee;
import com.vkondrat.experiment.util.JPAUtil;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

public class App 
{
    public static void main( String[] args )throws SQLException {
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
    }


}
