package com.vkondrat.experiment;

import com.google.gson.Gson;
import com.vkondrat.experiment.entities.Employee;
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
    public static void main( String[] args )throws SQLException {

        setPort(12345);
        get("/dest", new Route() {
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
    }


}
