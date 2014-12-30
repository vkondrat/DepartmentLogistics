package com.vkondrat.experiment.myServletPackage;
import com.google.gson.Gson;
import com.sun.jersey.spi.container.servlet.PerSession;
import com.vkondrat.experiment.dao.DepartmentDao;
import com.vkondrat.experiment.entities.Department;
import com.vkondrat.experiment.entities.Employee;
import com.vkondrat.experiment.entities.Project;
import com.vkondrat.experiment.service.CRUDService;
import com.vkondrat.experiment.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@PerSession
@Path("/rest")
public class RESTResources {

    @POST
    @Path("/employees")
    @Consumes("application/json")

    public void addEmployee(String jsonString) throws SQLException {
        new CRUDService<Employee>().saveFromJson(jsonString, Employee.class);

      //  new DepartmentDao().addEmployee(jsonString);
      //  departmentDao.addEmployee(jsonString);
      //  System.out.println("Added Employee");
      }

    @POST
    @Path("/departments")
    @Consumes("application/json")

    public void addDepartment(String jsonString) throws SQLException {
        new CRUDService<Department>().saveFromJson(jsonString, Department.class);
         }
    @POST
    @Path("/projects")
    @Consumes("application/json")

    public void addProject(String jsonString) throws SQLException {
        new CRUDService<Project>().saveFromJson(jsonString, Project.class);

    }

    @GET
    @Path("/employees")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Employee> getEmployees() {
        EntityManager em = JPAUtil.getInstance().getEm();
        em.getTransaction().begin();
        String qlString = "SELECT p FROM Employee p";
        TypedQuery<Employee> query = em.createQuery(qlString, Employee.class);
        List<Employee> listEmployee = (List<Employee>) query.getResultList();
        em.close();
        return listEmployee;
    }

    @GET
    @Path("/departments")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Department> getDepartments() {
        EntityManager em = JPAUtil.getInstance().getEm();
        em.getTransaction().begin();
        String qlString = "SELECT p FROM Department p";
        TypedQuery<Department> query = em.createQuery(qlString, Department.class);
        List<Department> listDepartment = (List<Department>) query.getResultList();
        em.close();
        return listDepartment;
    }

    @GET
    @Path("/projects")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Project> getProject() {
        EntityManager em = JPAUtil.getInstance().getEm();
        em.getTransaction().begin();
        String qlString = "SELECT p FROM Project p";
        TypedQuery<Project> query = em.createQuery(qlString, Project.class);
        List<Project> listProject = (List<Project>) query.getResultList();
        em.close();
        return listProject;
    }

    // Testing
    @GET
    @Path("/test/{name}")
    public String sayHello(@PathParam("name") String name) throws SQLException{
        StringBuilder stringBuilder = new StringBuilder("Check 25 Hello ");
        stringBuilder.append(name).append("!");

        return stringBuilder.toString();
    }
}


