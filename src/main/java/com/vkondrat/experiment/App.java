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

        get("/test", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                return "Hello World!";
            }
        });

        get("/addEmployee", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                return new CRUDService<Employee>().saveFromJson(request.queryParams("data"), Employee.class);
            }
        });
        get("/addDepartment", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                return new CRUDService<Department>().saveFromJson(request.queryParams("data"), Department.class);
            }
        });
        get("/addProject", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                return new CRUDService<Project>().saveFromJson(request.queryParams("data"), Project.class);
            }
        });

        get("/assignEmployeeToDepartment", new Route() {
            @Override
            public Object handle(Request request, Response response) {
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
            }
        });

        System.out.println("Finished");
    }
}

/*
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
            em.merge(employee);
            em.merge(department);
            em.getTransaction().commit();
            em.close();
            return "OK_2";
        });

 */



