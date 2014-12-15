package com.vkondrat.experiment;

import com.google.gson.Gson;
import com.vkondrat.experiment.entities.Department;
import com.vkondrat.experiment.entities.Employee;
import com.vkondrat.experiment.entities.Project;
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

public class App 
{
    //////// Add it to the DB
    //////// Add check for wrong input
    //////// Figure out the right format for start and enddate

    public static void main( String[] args )throws SQLException {

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


    }


}
