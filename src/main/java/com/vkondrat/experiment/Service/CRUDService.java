package com.vkondrat.experiment.service;

/**
 * Created by vkondrat on 12/18/14.
 */
import com.google.gson.Gson;
import com.vkondrat.experiment.entities.Department;
import com.vkondrat.experiment.entities.Employee;
import com.vkondrat.experiment.entities.Project;
import com.vkondrat.experiment.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 */
public class  CRUDService<T> {

    public String saveFromJson(String json, Class<T> clazz) {
        Gson gson = new Gson();
        T entity = gson.fromJson(json, clazz);

        EntityManager em = JPAUtil.getInstance().getEm();

        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
        Query query = em.createQuery("SELECT e FROM Employee e");
        List<Employee> list = (List<Employee>) query.getResultList();
        for (Employee employee : list) {
            System.out.println(employee.getName());
        }
        Query query1 = em.createQuery("SELECT d FROM Department d");
        List<Department> list1 = (List<Department>) query1.getResultList();
        for (Department department : list1) {
            System.out.println(department.getName());
        }
        Query query2 = em.createQuery("SELECT p FROM Project p");
        List<Project> list2 = (List<Project>) query2.getResultList();
        for (Project project : list2) {
            System.out.println(project.getName());
        }
        em.close();
        System.out.println(entity.toString());
        return gson.toJson(entity);
    }

    public T loadById(int id, Class<T> clazz) {
        EntityManager em = JPAUtil.getInstance().getEm();
        return em.find(clazz, id);
    }

    public void update(T entity) {
        EntityManager em = JPAUtil.getInstance().getEm();

        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
        em.close();
    }

}