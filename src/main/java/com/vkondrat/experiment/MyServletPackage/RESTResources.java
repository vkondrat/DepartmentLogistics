package com.vkondrat.experiment.myServletPackage;
import com.google.gson.Gson;
import com.sun.jersey.spi.container.servlet.PerSession;
import com.vkondrat.experiment.dao.DepartmentDao;
import com.vkondrat.experiment.entities.Department;
import com.vkondrat.experiment.entities.Employee;
import com.vkondrat.experiment.entities.Project;
import com.vkondrat.experiment.service.CRUDService;
import com.vkondrat.experiment.transport.Assignment;
import com.vkondrat.experiment.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

    /************************************ POST ************************************/

    @POST
    @Path("/employees")
    @Consumes("application/json")

    public void addEmployee(String jsonString) throws SQLException {
        new CRUDService<Employee>().saveFromJson(jsonString, Employee.class);

      //  new DepartmentDao().addEmployee(jsonString);
      //  departmentDao.addEmployee(jsonString);
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
    /************************************ GET ************************************/


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

    @GET
    @Path("/employees/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Employee getEmployeeById(@PathParam("id") int id) {

        try {
            EntityManager em = JPAUtil.getInstance().getEm();
            em.getTransaction().begin();
            String qlString = "SELECT p FROM Employee p WHERE p.id = ?1";
            TypedQuery<Employee> query = em.createQuery(qlString, Employee.class);
            query.setParameter(1, id);
            Employee employee = (Employee) query.getSingleResult();
            em.close();
            return employee;


            /*if (employee != null) return Response
                    .status(200)
                    .entity(employee)
                    .header("Access-Control-Allow-Headers", "X-extra-header")
                    .build();
            else {
                return Response
                        .status(404)
                        .entity("The podcast with the id " + id + " does not exist")
                        .build();
            }*/
        } catch (NoResultException e) {
            return null;
        }
    }

    @GET
    @Path("/departments/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Department getDepartmentById(@PathParam("id") int id) {

        try {
            EntityManager em = JPAUtil.getInstance().getEm();
            em.getTransaction().begin();
            String qlString = "SELECT p FROM Department p WHERE p.id = ?1";
            TypedQuery<Department> query = em.createQuery(qlString, Department.class);
            query.setParameter(1, id);
            Department department = (Department) query.getSingleResult();
            em.close();
            return department;
        } catch (NoResultException e) {
            return null;
        }
    }

    @GET
    @Path("/projects/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Project getProjectById(@PathParam("id") int id) {

        try {
            EntityManager em = JPAUtil.getInstance().getEm();
            em.getTransaction().begin();
            String qlString = "SELECT p FROM Project p WHERE p.id = ?1";
            TypedQuery<Project> query = em.createQuery(qlString, Project.class);
            query.setParameter(1, id);
            Project project = (Project) query.getSingleResult();
            em.close();
            return project;

        } catch (NoResultException e) {
            return null;
        }
    }

    /************************************ PUT ************************************/

    @PUT
    @Path("/employees")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Employee updateEmployeeById(String jsonString) {
        Gson gson = new Gson();
        Employee employee = gson.fromJson(jsonString, Employee.class);
        if ((Integer)employee.getId() != null) {
            new CRUDService<Employee>().update(employee);
        } else if (employeeCanBeCreated(employee)) {
            new CRUDService<Employee>().saveFromJson(jsonString, Employee.class);
        }
        return employee;
    }

    private boolean employeeCanBeCreated(Employee employee) {
        return employee.getName() != null && (Integer) employee.getAge() != null;
    }

    @PUT
    @Path("/departments")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Department updateDepartmentById(String jsonString) {
        Gson gson = new Gson();
        Department department = gson.fromJson(jsonString, Department.class);
        if ((Integer)department.getId() != null) {
            new CRUDService<Department>().update(department);
        } else if (departmentCanBeCreated(department)) {
            new CRUDService<Department>().saveFromJson(jsonString, Department.class);
        }
        return department;
    }

    private boolean departmentCanBeCreated(Department department) {
        return department.getName() != null;
    }

    @PUT
    @Path("/projects")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Project updateProjectById(String jsonString) {
        Gson gson = new Gson();
        Project project = gson.fromJson(jsonString, Project.class);
        if ((Integer)project.getId() != null) {
            new CRUDService<Project>().update(project);
        } else if (projectCanBeCreated(project)) {
            new CRUDService<Project>().saveFromJson(jsonString, Project.class);
        }
        return project;
    }

    private boolean projectCanBeCreated(Project project) {
        return project.getName() != null;
    }

    /************************************ DELETE ************************************/

    @DELETE
    @Path("/employees/{id}")
/*    @Produces({ MediaType.TEXT_HTML })*/
    public void deleteEmployeeById(@PathParam("id") int id) {

        EntityManager em = JPAUtil.getInstance().getEm();
        em.getTransaction().begin();
        Employee employee = em.find(Employee.class, id);
        em.remove(employee);
        em.getTransaction().commit();
        em.close();

    }

    @DELETE
    @Path("/departments/{id}")
/*    @Produces({ MediaType.TEXT_HTML })*/
    public void deleteDepartmentById(@PathParam("id") int id) {

        EntityManager em = JPAUtil.getInstance().getEm();
        em.getTransaction().begin();
        Department department = em.find(Department.class, id);
        em.remove(department);
        em.getTransaction().commit();
        em.close();

    }

    @DELETE
    @Path("/projects/{id}")
/*    @Produces({ MediaType.TEXT_HTML })*/
    public void deleteProjectById(@PathParam("id") int id) {

        EntityManager em = JPAUtil.getInstance().getEm();
        em.getTransaction().begin();
        Project project = em.find(Project.class, id);
        em.remove(project);
        em.getTransaction().commit();
        em.close();

    }

    /************************************ Assignment ************************************/
    // {to: 1, what:34}

    @POST
    @Path("/assign/employee-to-department")
    @Consumes({ MediaType.APPLICATION_JSON })
    public void assignEmployeeToDepartment(String jsonString) {
        Gson gson = new Gson();
        Assignment assignment = gson.fromJson(jsonString, Assignment.class);
        EntityManager em = JPAUtil.getInstance().getEm();
        em.getTransaction().begin();
        Employee employee = em.find(Employee.class, assignment.getWhat());
        Department department = em.find(Department.class, assignment.getTo());
        department.getEmployeeList().add(employee);
        employee.setDepartment(department);
        em.merge(employee);
        em.merge(department);
        em.getTransaction().commit();
        em.close();
    }

    @POST
    @Path("/assign/department-to-employee")
    @Consumes({ MediaType.APPLICATION_JSON })
    public void assignDepartmentToEmployee(String jsonString) {
        Gson gson = new Gson();
        Assignment assignment = gson.fromJson(jsonString, Assignment.class);
        EntityManager em = JPAUtil.getInstance().getEm();
        em.getTransaction().begin();
        Employee employee = em.find(Employee.class, assignment.getTo());
        Department department = em.find(Department.class, assignment.getWhat());
        department.getEmployeeList().add(employee);
        employee.setDepartment(department);
        em.merge(employee);
        em.merge(department);
        em.getTransaction().commit();
        em.close();
    }

    @POST
    @Path("/assign/employee-to-project")
    @Consumes({ MediaType.APPLICATION_JSON })
    public void assignEmployeeToProject(String jsonString) {
        Gson gson = new Gson();
        Assignment assignment = gson.fromJson(jsonString, Assignment.class);
        EntityManager em = JPAUtil.getInstance().getEm();
        em.getTransaction().begin();
        Employee employee = em.find(Employee.class, assignment.getWhat());
        Project project = em.find(Project.class, assignment.getTo());
        project.getEmployeeList().add(employee);
        employee.getProjectList().add(project);
        em.merge(employee);
        em.merge(project);
        em.getTransaction().commit();
        em.close();
    }

    @POST
    @Path("/assign/project-to-employee")
    @Consumes({ MediaType.APPLICATION_JSON })
    public void assignProjectToEmployee(String jsonString) {
        Gson gson = new Gson();
        Assignment assignment = gson.fromJson(jsonString, Assignment.class);
        EntityManager em = JPAUtil.getInstance().getEm();
        em.getTransaction().begin();
        Employee employee = em.find(Employee.class, assignment.getTo());
        Project project = em.find(Project.class, assignment.getWhat());
        project.getEmployeeList().add(employee);
        employee.getProjectList().add(project);
        em.merge(employee);
        em.merge(project);
        em.getTransaction().commit();
        em.close();
    }

  /*  @DELETE
    @Produces({ MediaType.TEXT_HTML })
    @Transactional
    public Response deletePodcasts() {
        podcastDao.deletePodcasts();
        return Response.status(200)
                .entity("All podcasts have been successfully removed").build();
    }
*/
    /************************************ TESTING ************************************/
    @GET
    @Path("/test/{name}")
    public String sayHello(@PathParam("name") String name) throws SQLException{
        StringBuilder stringBuilder = new StringBuilder("Check 26 Hello ");
        stringBuilder.append(name).append("!");

        return stringBuilder.toString();
    }
}


