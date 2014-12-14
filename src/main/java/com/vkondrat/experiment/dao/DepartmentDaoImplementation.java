package com.vkondrat.experiment.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.vkondrat.experiment.entities.Employee;
import com.vkondrat.experiment.entities.Department;
import com.vkondrat.experiment.entities.Project;
import com.vkondrat.experiment.util.JPAUtil;
import javax.persistence.Query;

/**
 * Created by vkondrat on 11/26/14.
 */
public class DepartmentDaoImplementation implements DepartmentDao {

    public void addEmployee(Employee employee){
        EntityManager em = beginTransaction();
        em.persist(employee);
        commitTransaction(em);
    }

    private void commitTransaction(EntityManager em) {
        em.getTransaction().commit();
        em.close();
    }

    private EntityManager beginTransaction() {
        EntityManager em = JPAUtil.getInstance().getEm();
        em.getTransaction().begin();
        return em;
    }

    public void updateEmployee(Employee employee) {
        EntityManager em = beginTransaction();
        em.merge(employee);
        em.getTransaction().commit();
    }

    public Employee getEmployee(Long employeeId){
        EntityManager em = JPAUtil.getInstance().getEm();
        return em.find(Employee.class, employeeId);

    }

    //TODO: implement
    public List<Employee> findAllEmployees() {
        EntityManager em = JPAUtil.getInstance().getEm();
        Query query = em.createQuery("SELECT e FROM Employee e");
        return (List<Employee>) query.getResultList();
    }

    public void addDepartment(Department department){
        EntityManager em = beginTransaction();
        em.persist(department);
        commitTransaction(em);
    }

    public void updateDepartment(Department department){
        EntityManager em = beginTransaction();
        em.merge(department);
        em.getTransaction().commit();

    }

    public Department getDepartment(Long departmentId) {
        EntityManager em = JPAUtil.getInstance().getEm();
        try {
            String qlString = "SELECT d FROM Department d WHERE d.id = ?1";
            TypedQuery<Department> query = em.createQuery(qlString, Department.class);
            query.setParameter(1, departmentId);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Department> findDepartment() {
        return null;
    }

    public void addProject(Project project) {
        EntityManager em = beginTransaction();
        em.persist(project);
        commitTransaction(em);
    }

    public void updateProject(Project project){
        EntityManager em = beginTransaction();
        em.merge(project);
        em.getTransaction().commit();

    }

    public Project getProject(Long projectId){
        EntityManager em = JPAUtil.getInstance().getEm();
        try {
            String qlString = "SELECT p FROM Project p WHERE p.id = ?1";
            TypedQuery<Project> query = em.createQuery(qlString, Project.class);
            query.setParameter(1, projectId);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
}

    public List<Project> findProject() {
        return null;
    }
}
