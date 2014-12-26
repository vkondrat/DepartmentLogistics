package com.vkondrat.experiment.myServletPackage;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("hello")
public class RESTResources {

    @GET
    @Path("{name}")
    public String sayHello(@PathParam("name") String name){
        StringBuilder stringBuilder = new StringBuilder("Need to check Hello ");
        stringBuilder.append(name).append("!");

        return stringBuilder.toString();
    }

}