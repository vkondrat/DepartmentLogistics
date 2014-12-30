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

    public void saveFromJson(String json, Class<T> clazz) {
        Gson gson = new Gson();
        T entity = gson.fromJson(json, clazz);
        
        EntityManager em = JPAUtil.getInstance().getEm();
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
        em.close();

        //System.out.println(entity.toString());
        // return gson.toJson(entity);
    }
    //  new CRUDService<Employee>().saveFromJson(jsonString, Employee.class);
    //  new CRUDService<Department>().saveFromJson(jsonString, Department.class);
    //  new CRUDService<Project>().saveFromJson(jsonString, Project.class);


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