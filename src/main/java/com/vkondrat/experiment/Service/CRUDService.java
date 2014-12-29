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