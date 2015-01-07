package com.vkondrat.experiment.myServletPackage;
import com.google.gson.Gson;
import com.sun.jersey.spi.container.servlet.PerSession;
import com.vkondrat.experiment.entities.Department;
import com.vkondrat.experiment.entities.Employee;
import com.vkondrat.experiment.entities.Project;
import com.vkondrat.experiment.service.CRUDService;
import com.vkondrat.experiment.transport.Assignment;
import com.vkondrat.experiment.util.JPAUtil;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
        new CRUDService<Employee>().addEntity(jsonString, Employee.class);
      }

    @POST
    @Path("/departments")
    @Consumes("application/json")

    public void addDepartment(String jsonString) throws SQLException {
        new CRUDService<Department>().addEntity(jsonString, Department.class);
         }
    @POST
    @Path("/projects")
    @Consumes("application/json")

    public void addProject(String jsonString) throws SQLException {
        new CRUDService<Project>().addEntity(jsonString, Project.class);
    }
    /************************************ GET ************************************/

    @GET
    @Path("/employees/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Employee getEmployeeById(@PathParam("id") int id) {
        return new CRUDService<Employee>().findEntity(id, Employee.class);
    }

    @GET
    @Path("/departments/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Department getDepartmentById(@PathParam("id") int id) {
        return new CRUDService<Department>().findEntity(id, Department.class);
    }

    @GET
    @Path("/projects/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Project getProjectById(@PathParam("id") int id) {
        return new CRUDService<Project>().findEntity(id, Project.class);
    }


    /************************************ DELETE ************************************/

    @DELETE
    @Path("/employees/{id}")
    public void deleteEmployeeById(@PathParam("id") int id) {

        new CRUDService<Employee>().deleteEntity(id, Employee.class);

    }

    @DELETE
    @Path("/departments/{id}")
    public void deleteDepartmentById(@PathParam("id") int id) {
        new CRUDService<Department>().deleteEntity(id, Department.class);
    }

    @DELETE
    @Path("/projects/{id}")
    public void deleteProjectById(@PathParam("id") int id) {
        new CRUDService<Project>().deleteEntity(id, Project.class);
    }


    /************************************ PUT ************************************/

    @PUT
    @Path("/employees/{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    public void updateEmployeeById(String jsonString, @PathParam("id") int id) {
        Gson gson = new Gson();

        Employee employee = gson.fromJson(jsonString, Employee.class);
        employee.setId(id);

        new CRUDService<Employee>().updateDB(employee);
    }

    @PUT
    @Path("/departments/{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    public void updateDepartmentById(String jsonString, @PathParam("id") int id) {
        Gson gson = new Gson();
        Department department = gson.fromJson(jsonString, Department.class);
        department.setId(id);

        new CRUDService<Department>().updateDB(department);
    }

    @PUT
    @Path("/projects/{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    public void updateProjectById(String jsonString, @PathParam("id") int id) {
        Gson gson = new Gson();
        Project project = gson.fromJson(jsonString, Project.class);
        project.setId(id);

        new CRUDService<Project>().updateDB(project);
    }

    /************************************ GET ALL ************************************/


    @GET
    @Path("/employees")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Object getEmployees() {
        Gson gson = new Gson();
        EntityManager em = JPAUtil.getInstance().getEm();
        em.getTransaction().begin();
        String qlString = "SELECT p FROM Employee p";
        TypedQuery<Employee> query = em.createQuery(qlString, Employee.class);
        List<Employee> listEmployee = (List<Employee>) query.getResultList();
        em.close();
        return gson.toJson(listEmployee.toArray());
    }

    @GET
    @Path("/departments")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Object getDepartments() {
        Gson gson = new Gson();
        EntityManager em = JPAUtil.getInstance().getEm();
        em.getTransaction().begin();
        String qlString = "SELECT p FROM Department p";
        TypedQuery<Department> query = em.createQuery(qlString, Department.class);
        List<Department> listDepartment = (List<Department>) query.getResultList();
        em.close();
        return gson.toJson(listDepartment.toArray());
    }

    @GET
    @Path("/projects")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Object getProject() {
        Gson gson = new Gson();
        EntityManager em = JPAUtil.getInstance().getEm();
        em.getTransaction().begin();
        String qlString = "SELECT p FROM Project p";
        TypedQuery<Project> query = em.createQuery(qlString, Project.class);
        List<Project> listProject = (List<Project>) query.getResultList();
        em.close();
        return gson.toJson(listProject.toArray());
    }

    /************************************ GET ALL Employees by Department.ID ************************************/


    @GET
    @Path("/department/{id}/employee")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Object getEmployeesByDepartment(String jsonString, @PathParam("id") int id) {
      //  Gson gson = new Gson();
        EntityManager em = JPAUtil.getInstance().getEm();
        em.getTransaction().begin();
        Department department = new CRUDService<Department>().findEntity(id, Department.class);
        em.close();
        return department.getEmployeeList();
    }
    // Not updated the employees list Error 500

    /************************************ Assignment ************************************/

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
    @Path("/employee/{employeeId}/project/{projectId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    public void assignEmployeeToProjectThroughId(int employeeId, int projectId) {
        EntityManager em = JPAUtil.getInstance().getEm();
        em.getTransaction().begin();
        Employee employee = em.find(Employee.class, employeeId);
        Project project = em.find(Project.class, projectId);
        project.getEmployeeList().add(employee);
        employee.getProjectList().add(project);
        em.merge(employee);
        em.merge(project);
        em.getTransaction().commit();
        em.close();
    }

    @POST
    @Path("/project/{projectId}/employee/{employeeId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    public void assignProjectToEmployeeThroughId(int projectId, int employeeId) {
        EntityManager em = JPAUtil.getInstance().getEm();
        em.getTransaction().begin();
        Employee employee = em.find(Employee.class, employeeId);
        Project project = em.find(Project.class, projectId);
        project.getEmployeeList().add(employee);
        employee.getProjectList().add(project);
        em.merge(employee);
        em.merge(project);
        em.getTransaction().commit();
        em.close();
    }

    // Can I just call assignEmployeeToProjectThroughId(int projectId, int employeeId)
    // How to check if I got the connection

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


    /************************************ TESTING ************************************/
    @GET
    @Path("/test/{name}")
    public String sayHello(@PathParam("name") String name) throws SQLException{
        StringBuilder stringBuilder = new StringBuilder("Check 28 Hello ");
        stringBuilder.append(name).append("!");

        return stringBuilder.toString();
    }
}


