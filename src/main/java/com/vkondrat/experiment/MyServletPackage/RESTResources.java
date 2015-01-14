package com.vkondrat.experiment.myServletPackage;
import com.google.gson.Gson;
import com.sun.jersey.spi.container.servlet.PerSession;
import com.vkondrat.experiment.entities.Department;
import com.vkondrat.experiment.entities.Employee;
import com.vkondrat.experiment.entities.Project;
import com.vkondrat.experiment.service.CRUD;
import com.vkondrat.experiment.service.CRUDService;
import com.vkondrat.experiment.transport.Assignment;
import com.vkondrat.experiment.util.JPAUtil;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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

    @POST
    @Path("/employees")
    @Consumes("application/json")

    public void addEmployee(String jsonString) throws SQLException {
        Employee employee = new CRUDService<Employee>().getEntityFromJson(jsonString, Employee.class);
        if (employee.getDepartmentId()!=0){

            EntityManager em = JPAUtil.getInstance().getEm();
            em.getTransaction().begin();
            Department department = em.find(Department.class, employee.getDepartmentId());
            department.getEmployeeList().add(employee);
            employee.setDepartment(department);
            em.merge(department);
            em.getTransaction().commit();
            em.close();
        }
        new CRUDService<Employee>().updateDB(employee);

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

        EntityManager em = JPAUtil.getInstance().getEm();
        Department department = em.find(Department.class, id);
        em.close();
        for (Employee employee : department.getEmployeeList()) {
            new CRUDService<Employee>().deleteEntity(employee.getId(), Employee.class);
        }
        new CRUDService<Department>().deleteEntity(id, Department.class);
    }

    @DELETE
    @Path("/projects/{id}")
    public void deleteProjectById(@PathParam("id") int id) {
        new CRUDService<Project>().deleteEntity(id, Project.class);
    }


    /************************************ PUT ************************************/
// Plan to optimize to updateFromJSON, but setId requires CRUD to know the nature of the class
    @PUT
    @Path("/employees/{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    public void updateEmployeeById(String jsonString, @PathParam("id") int id) {
        Gson gson = new Gson();

        Employee employee = gson.fromJson(jsonString, Employee.class);
        employee.setId(id);
// Put in a separate class, see POST employee
        if (employee.getDepartmentId()!=0){

            EntityManager em = JPAUtil.getInstance().getEm();
            em.getTransaction().begin();
            Department department = em.find(Department.class, employee.getDepartmentId());
            department.getEmployeeList().add(employee);
            employee.setDepartment(department);
            em.merge(department);
            em.getTransaction().commit();
            em.close();
        }

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
        String qlString = "SELECT p FROM Employee p";
        TypedQuery<Employee> query = em.createQuery(qlString, Employee.class);
        List<Employee> listEmployee = query.getResultList();
        em.close();
        return gson.toJson(listEmployee.toArray());
    }

    @GET
    @Path("/departments")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Object getDepartments() {
        Gson gson = new Gson();
        EntityManager em = JPAUtil.getInstance().getEm();
        String qlString = "SELECT p FROM Department p";
        TypedQuery<Department> query = em.createQuery(qlString, Department.class);
        List<Department> listDepartment = query.getResultList();
        em.close();
        Object A = listDepartment.toArray();
        return gson.toJson(listDepartment.toArray());
    }

    @GET
    @Path("/projects")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Object getProject() {
        Gson gson = new Gson();
        EntityManager em = JPAUtil.getInstance().getEm();
        String qlString = "SELECT p FROM Project p";
        TypedQuery<Project> query = em.createQuery(qlString, Project.class);
        List<Project> listProject = query.getResultList();
        em.close();
        return gson.toJson(listProject.toArray());
    }

    /************************************ GET ALL Employees by Department.ID ************************************/


    @GET
    @Path("/department/{id}/employees")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Object getEmployeesByDepartment(@PathParam("id") int id) {
        Gson gson = new Gson();
        EntityManager em = JPAUtil.getInstance().getEm();
        Department department = new CRUDService<Department>().findEntity(id, Department.class);
        em.close();
        return gson.toJson(department.getEmployeeList().toArray());
    }

    @GET
    @Path("/project/{id}/employees")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Object getEmployeesByProject(@PathParam("id") int id) {
        Gson gson = new Gson();
        EntityManager em = JPAUtil.getInstance().getEm();
        Project project = new CRUDService<Project>().findEntity(id, Project.class);
        em.close();
        return gson.toJson(project.getEmployeeList().toArray());
    }

    @GET
    @Path("/employee/{id}/projects")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Object getProjectsByEmployee(@PathParam("id") int id) {
        Gson gson = new Gson();
        EntityManager em = JPAUtil.getInstance().getEm();
        Employee employee = new CRUDService<Employee>().findEntity(id, Employee.class);
        em.close();
        return gson.toJson(employee.getProjectList().toArray());
    }

    /************************************ Assignment ************************************/

/* Assigning Employee to the Department is done through initializing Employee (see POST or PUT)*/

    @POST
    @Path("/projects/{projectId}/employees/{employeeId}")
    //PUT in DAO
    public void assignEmployeeToProject(@PathParam("projectId") int projectId, @PathParam("employeeId") int employeeId) {
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
    @Path("/employees/{employeeId}/projects/{projectId}")
    //PUT in DAO
    public void assignProjectToEmployee(@PathParam("employeeId") int employeeId, @PathParam("projectId") int projectId) {
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

    /************* DELETE removes the project from the employee and the other way ************************************/
    @DELETE
    @Path("/projects/{projectId}/employees/{employeeId}")
    public void removeEmployeeFromProject(@PathParam("projectId") int projectId, @PathParam("employeeId") int employeeId) {
        EntityManager em = JPAUtil.getInstance().getEm();
        em.getTransaction().begin();
        Employee employee = em.find(Employee.class, employeeId);
        Project project = em.find(Project.class, projectId);
        project.getEmployeeList().remove(employee);
        employee.getProjectList().remove(project);
        em.merge(employee);
        em.merge(project);
        em.getTransaction().commit();
        em.close();
    }
    @DELETE
    @Path("/employees/{employeeId}/projects/{projectId}")
    public void removeProjectFromEmployee(@PathParam("projectId") int projectId, @PathParam("employeeId") int employeeId) {
        EntityManager em = JPAUtil.getInstance().getEm();
        em.getTransaction().begin();
        Employee employee = em.find(Employee.class, employeeId);
        Project project = em.find(Project.class, projectId);
        project.getEmployeeList().remove(employee);
        employee.getProjectList().remove(project);
        em.merge(employee);
        em.merge(project);
        em.getTransaction().commit();
        em.close();
    }

    /************************************ TESTING ************************************/
    @GET
    @Path("/test/{name}")
    public String sayHello(@PathParam("name") String name) throws SQLException{
        StringBuilder stringBuilder = new StringBuilder("Check 3 Hello ");
        stringBuilder.append(name).append("!");

        return stringBuilder.toString();
    }
}

/*

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
 */