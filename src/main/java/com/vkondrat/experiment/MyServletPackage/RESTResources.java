package com.vkondrat.experiment.myServletPackage;
import com.google.gson.Gson;
import com.sun.jersey.spi.container.servlet.PerSession;
import com.vkondrat.experiment.entities.Department;
import com.vkondrat.experiment.entities.Employee;
import com.vkondrat.experiment.service.CRUDService;
import com.vkondrat.experiment.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@PerSession
@Path("/employees")
public class RESTResources {

    // Do only read and print first to check


    @POST
    @Path("/post")
    @Consumes("application/json")

    public void addEmployee(String jsonString) throws SQLException {

        System.out.println(jsonString);
        System.out.println("Is working!");

        Gson gson = new Gson();
        Employee entity = gson.fromJson(jsonString, Employee.class);
        System.out.println(entity.toString());
        System.out.println(gson.toJson(entity));
        System.out.println(entity.getId());
        System.out.println(entity.getName());
        System.out.println(entity.getAge());

        EntityManager em = JPAUtil.getInstance().getEm();

        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();

        Query query = em.createQuery("SELECT e FROM Employee e");

        List<Employee> list = (List<Employee>) query.getResultList();
        for (Employee employee : list) {
            System.out.println(employee.getName());
        }
      //  em.getTransaction().begin();
        em.close();
        //   String test= new CRUDService<Employee>().saveFromJson(jsonString, Employee.class);
        System.out.println("test2");

    }
    // Testing
    @GET
    @Path("{name}")
    public String sayHello(@PathParam("name") String name) throws SQLException{
        StringBuilder stringBuilder = new StringBuilder("Check 20 Hello ");
        stringBuilder.append(name).append("!");

        return stringBuilder.toString();
    }
}


