package com.vkondrat.experiment.service;

import com.google.gson.Gson;

import com.vkondrat.experiment.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class  CRUDService<T> {

    public void addEntity(String json, Class<T> clazz) {
        T entity = getEntityFromJson(json, clazz);
        updateDB(entity);
    }

    public void deleteEntity(int entityId, Class<T> clazz) {
        EntityManager em = JPAUtil.getInstance().getEm();
        em.getTransaction().begin();
        T entity = em.find(clazz, entityId);
        em.remove(entity);
        em.getTransaction().commit();
        em.close();
    }

    public T findEntity(int entityId, Class<T> clazz) {
        try {
            EntityManager em = JPAUtil.getInstance().getEm();
            em.getTransaction().begin();
            T entity = em.find(clazz, entityId);
            return entity;
        } catch (NoResultException e) {
            return null;
        }
    }


    public T getEntityFromJson(String json, Class<T> clazz) {
        Gson gson = new Gson();
        T entity = gson.fromJson(json, clazz);
        return entity;
    }

    public void updateDB(T entity) {
        EntityManager em = JPAUtil.getInstance().getEm();

        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
        em.close();
    }

}
  /*  public T getRelatedEntitiesById(int id, Class<T> clazz) {
        Gson gson = new Gson();
        EntityManager em = JPAUtil.getInstance().getEm();
        T entity = findEntity(id, clazz);
        em.close();
        return entity;
        return gson.toJson(department.getEmployeeList().toArray()); <- problem here
    }



    public void updateFromJSON(String jsonString, int id, Class<T> clazz ){
        Gson gson = new Gson();
        T entity = gson.fromJson(jsonString, clazz);
        entity.setId(id);  <- problem here
        updateDB(entity);
    }

    public T findEntity(int entityId)
    {
        */
/*If works try this as well
        public T loadById(int id, Class<T> clazz) {
        EntityManager em = JPAUtil.getInstance().getEm();
        return em.find(clazz, id);
        }*//*


        try {
            EntityManager em = JPAUtil.getInstance().getEm();
            em.getTransaction().begin();
            String qlString = "SELECT p FROM T p WHERE p.id = ?1";
            TypedQuery<T> query = em.createQuery(qlString, T.class);
            query.setParameter(1, id);
            T entity = (T) query.getSingleResult();
            em.close();
            return entity;
        } catch (NoResultException e) {
            return null;
        }
    }

    public <T extends Common> void updateEntity(String json, Class<T extends Common> clazz)
    {
        T entity = getEntityFromJson(json, clazz); //new CRUDService<T>().

        if ((Integer)entity.getId() != null) {
            updateDB(entity);
        } else if (employeeCanBeCreated(entity)) {
            new CRUDService<Employee>().addEntity(json, T.class);
        }
    }

    private <T extends Common> boolean entityCanBeCreated(T entity) {
        return entity.getName() != null;
    }

    public List<T> findAllEntities()
    {
        T entity;
        EntityManager em = JPAUtil.getInstance().getEm();
        em.getTransaction().begin();
        String qlString = "SELECT p FROM entity.getClass() p";
        TypedQuery<T> query = em.createQuery(qlString, entity.class);
        List<T> listT = (List<T>) query.getResultList();
        em.close();
        return listT;
    }*/
