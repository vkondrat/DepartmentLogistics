Create Maven Java project with JPA, JAX-RS, JSON libraries of your choice (example: Hibernate, Jersey, Jackson) and RDBMS ofr your choice (e.g. MySQL, PostreSQL, etc)
- Create JPA entities for Employee (id, name, age), Department (id, name), Project (id, name, start date, end date). The relationship between entities should be as follows: each department has 0 to many employees, one employee works for one and only one department; each project has 0 to many employees assigned to it, one employee may work on 0 to many projects. 
- deploy your project to Application Server of your choice (example: Tomcat)
- Create RESTful services to insert, update, delete, get the data for employees, departments, projects. Use REST client of your choice (example: Chrome plugin Postman) to test your services
