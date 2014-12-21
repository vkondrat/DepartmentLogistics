package com.vkondrat.experiment;

import com.google.gson.Gson;
import com.vkondrat.experiment.entities.Department;
import com.vkondrat.experiment.entities.Employee;
import com.vkondrat.experiment.entities.Project;
import com.vkondrat.experiment.service.CRUDService;
import com.vkondrat.experiment.transport.Assignment;
import com.vkondrat.experiment.util.JPAUtil;
import spark.Request;
import spark.Response;
import spark.Route;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

import static spark.Spark.get;
import static spark.SparkBase.setPort;

public class App {
    //////// Add check for wrong input
    //////// Figure out the right format for start and enddate

    public static void main(String[] args) throws SQLException {
        int port = 12345;
        if (args.length > 0)
            port = Integer.parseInt(args[0]);
        setPort(port);
        get("/test", (request, response) -> "Scuba duba!" );


        get("/addEmployee", (request, response) -> new CRUDService<Employee>().saveFromJson(request.queryParams("data"),Employee.class));
        get("/addDepartment", (request, response) -> new CRUDService<Department>().saveFromJson(request.queryParams("data"), Department.class));
        get("/addProject", (request, response) -> new CRUDService<Project>().saveFromJson(request.queryParams("data"), Project.class));

        get("/dest", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                Gson gson = new Gson();
                String data = request.queryParams("data");
                System.out.println("param: " + data);
                Employee example = gson.fromJson(data, Employee.class);
                System.out.println(example.toString());
                example.setName("modifiedName");
                EntityManager em = JPAUtil.getInstance().getEm();

                em.getTransaction().begin();
                em.createQuery("DELETE FROM Employee").executeUpdate();
                em.persist(example.getClass());
                em.getTransaction().commit();
                Query query = em.createQuery("SELECT e FROM Employee e"); // where e.id=1|| where e.name='Vasya'
                // where e.age>20 // Google JQL

                List<Employee> list = (List<Employee>) query.getResultList();
                for (Employee employee : list) {
                    System.out.println(employee.getName());
                }
                em.close();
                String json = gson.toJson(example);
                return json;
            }
        });


        get("/assignEmployeeToDepartment", (request, response) -> {
            Gson gson = new Gson();
            String data = request.queryParams("data"); // {to: 1, whatIds: [1,2,3,4]}
            Assignment assignment = gson.fromJson(data, Assignment.class);
            CRUDService<Employee> employeeService = new CRUDService<>();
            Employee employee = employeeService.loadById(assignment.getWhat(), Employee.class);
            CRUDService<Department> departmentService = new CRUDService<>();
            Department department = departmentService.loadById(assignment.getTo(), Department.class);
            department.getEmployeeList().add(employee);
            employee.setDepartment(department);
            employeeService.update(employee);
            departmentService.update(department);
            return "OK";
        });

        get("/assignEmployeeToDepartment_2", (request, response) -> {
            Gson gson = new Gson();
            String data = request.queryParams("data"); // {to: 1, what:34}
            Assignment assignment = gson.fromJson(data, Assignment.class);
            EntityManager em = JPAUtil.getInstance().getEm();

            em.getTransaction().begin();
            Employee employee = em.find(Employee.class, assignment.getWhat());
            Department department = em.find(Department.class, assignment.getTo());
            department.getEmployeeList().add(employee);
            employee.setDepartment(department);
            em.getTransaction().commit();
            em.close();
            return "OK";
        });

        get("/assignDepartmentToEmployee", (request, response) -> {
            Gson gson = new Gson();
            String data = request.queryParams("data"); // {to: 1, whatIds: [1,2,3,4]}
            Assignment assignment = gson.fromJson(data, Assignment.class);
            CRUDService<Department> departmentService = new CRUDService<>();
            Department department = departmentService.loadById(assignment.getWhat(), Department.class);
            CRUDService<Employee> employeeService = new CRUDService<>();
            Employee employee = employeeService.loadById(assignment.getTo(), Employee.class);
            // Finish from here!!!
            department.getEmployeeList().add(employee);
            employee.setDepartment(department);
            employeeService.update(employee);
            departmentService.update(department);
            return "OK";
        });

        get("/assignDepartmentToEmployee_2", (request, response) -> {
            Gson gson = new Gson();
            String data = request.queryParams("data"); // {to: 1, what:34}
            Assignment assignment = gson.fromJson(data, Assignment.class);
            EntityManager em = JPAUtil.getInstance().getEm();

            em.getTransaction().begin();
            Employee employee = em.find(Employee.class, assignment.getWhat());
            Department department = em.find(Department.class, assignment.getTo());
            department.getEmployeeList().add(employee);
            employee.setDepartment(department);
            em.getTransaction().commit();
            em.close();
            return "OK";
        });

        get("/assignEmployeeToDepartment", (request, response) -> {
            Gson gson = new Gson();
            String data = request.queryParams("data"); // {to: 1, whatIds: [1,2,3,4]}
            Assignment assignment = gson.fromJson(data, Assignment.class);
            CRUDService<Employee> employeeService = new CRUDService<>();
            Employee employee = employeeService.loadById(assignment.getWhat(), Employee.class);
            CRUDService<Department> departmentService = new CRUDService<>();
            Department department = departmentService.loadById(assignment.getTo(), Department.class);
            department.getEmployeeList().add(employee);
            employee.setDepartment(department);
            employeeService.update(employee);
            departmentService.update(department);
            return "OK";
        });

        get("/assignEmployeeToDepartment_2", (request, response) -> {
            Gson gson = new Gson();
            String data = request.queryParams("data"); // {to: 1, what:34}
            Assignment assignment = gson.fromJson(data, Assignment.class);
            EntityManager em = JPAUtil.getInstance().getEm();

            em.getTransaction().begin();
            Employee employee = em.find(Employee.class, assignment.getWhat());
            Department department = em.find(Department.class, assignment.getTo());
            department.getEmployeeList().add(employee);
            employee.setDepartment(department);
            em.getTransaction().commit();
            em.close();
            return "OK";
        });

        get("/assignEmployeeToDepartment", (request, response) -> {
            Gson gson = new Gson();
            String data = request.queryParams("data"); // {to: 1, whatIds: [1,2,3,4]}
            Assignment assignment = gson.fromJson(data, Assignment.class);
            CRUDService<Employee> employeeService = new CRUDService<>();
            Employee employee = employeeService.loadById(assignment.getWhat(), Employee.class);
            CRUDService<Department> departmentService = new CRUDService<>();
            Department department = departmentService.loadById(assignment.getTo(), Department.class);
            department.getEmployeeList().add(employee);
            employee.setDepartment(department);
            employeeService.update(employee);
            departmentService.update(department);
            return "OK";
        });

        get("/assignEmployeeToDepartment_2", (request, response) -> {
            Gson gson = new Gson();
            String data = request.queryParams("data"); // {to: 1, what:34}
            Assignment assignment = gson.fromJson(data, Assignment.class);
            EntityManager em = JPAUtil.getInstance().getEm();

            em.getTransaction().begin();
            Employee employee = em.find(Employee.class, assignment.getWhat());
            Department department = em.find(Department.class, assignment.getTo());
            department.getEmployeeList().add(employee);
            employee.setDepartment(department);
            em.getTransaction().commit();
            em.close();
            return "OK";
        });



        System.out.println("Code works");
    }
}




   /* public static void main( String[] args )throws SQLException {

        setPort(12345);

        get("/test", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                Gson gson = new Gson();
                String data = request.queryParams("data");
                System.out.println("param: " + data);
                Employee example = gson.fromJson(data, Employee.class);
                System.out.println(example.toString());
                example.setName("modifiedName");
                String json = gson.toJson(example);
                return json;
            }
        });

        get("/addEmployee", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                Gson gson = new Gson();
                String data = request.queryParams("data");
                Employee employee = gson.fromJson(data, Employee.class);

                EntityManager em = JPAUtil.getInstance().getEm();

                em.getTransaction().begin();
                em.persist(employee);
                em.getTransaction().commit();
                em.close();
                System.out.println(employee.toString());
                String json = gson.toJson(employee);
                return json;
            }
        });

        get("/addDepartment", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                Gson gson = new Gson();
                String data = request.queryParams("data");
                Department department = gson.fromJson(data, Department.class);
                System.out.println(department.toString());
                String json = gson.toJson(department);
                return json;
            }
        });
        get("/addProject", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                Gson gson = new Gson();
                String data = request.queryParams("data");
                Project project = gson.fromJson(data, Project.class);
                System.out.println(project.toString());
                String json = gson.toJson(project);
                return json;
            }
        });
        get("/assignDepartmentToEmployee", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                Gson gson = new Gson();
                String data = request.queryParams("data");
                Employee employee = gson.fromJson(data, Employee.class);
                System.out.println(employee.toString());
                String json = gson.toJson(employee);
                return json;
            }
        });
        get("/assignEmployeesToDepartment", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                Gson gson = new Gson();
                String data = request.queryParams("data");
                Employee employee = gson.fromJson(data, Employee.class);
                System.out.println(employee.toString());
                String json = gson.toJson(employee);
                return json;
            }
        });
        get("/assignEmployeesToProject", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                Gson gson = new Gson();
                String data = request.queryParams("data");
                Employee employee = gson.fromJson(data, Employee.class);
                System.out.println(employee.toString());
                String json = gson.toJson(employee);
                return json;
            }
        });
        get("/assignProjectsToEmployee", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                Gson gson = new Gson();
                String data = request.queryParams("data");
                Employee employee = gson.fromJson(data, Employee.class);
                System.out.println(employee.toString());
                String json = gson.toJson(employee);
                return json;
            }
        });

        System.out.println("Works");
    }


}*/
