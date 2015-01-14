Prerequisits:

- Maven
- Application Server (example: Tomcat)
- REST Client (example: Chrome plugin Postman)

How to run:

- Download/clone the project;
- Build the project using Maven (mvn clean; mvn compile; mvn package);
- Deploy the project to an Application Server (example for Tomcat: Copy test-project.war from DepartmentLogistics/target to Tomcat/webapps)
- Start an Application Server, Open REST Client 

Examples of HTTP requests:

1) Add an entity (POST)

http://localhost:8080/test-project/rest/employees

JSON: {"name":"Vlad", "age":27, “departmentId”:1}

http://localhost:8080/test-project/rest/departments

JSON: {"name":"Mathematics"}

http://localhost:8080/test-project/rest/projects

JSON: {"name":"Calculus","startDate":"10-10-1990", "endDate":"1-1-1999"}

2) Get all entities (GET)

http://localhost:8080/test-project/rest/employees (D,P)

3)  Find an entity by Id (GET)

http://localhost:8080/test-project/rest/employees/Id (D,P)

4) List all employees working for the department {departmentId} (GET) 

http://localhost:8080/test-project/rest/department/{departmentId}/employees

5) Update an entity or add if it's Id is unique (PUT)

http://localhost:8080/test-project/rest/employees/Id (D,P)

JSON: {"name":"Vlad","age":27,”departmentId”:2}

6) Delete an entity by Id (DELETE)

http://localhost:8080/test-project/rest/employees/Id (D,P)
Note: Deleting Department is currently not working

7) Assign the relationship between employee {employeeId} and project {projectId}
(many-to-many) (POST)

http://localhost:8080/test-project/rest/projects/{projectId}/employees/{employeeId}  
http://localhost:8080/test-project/rest/employees/{employeeId}/projects/{projectId}

Note: Assigning an employee to a department is done in (1)

8) Return all projects employee is assigned to (GET) 
http://localhost:8080/test-project/rest/employee/{employeeId}/projects
And the other way
http://localhost:8080/test-project/rest/project/{employeeId}/employees


9) Unassign employee {employeeId} from project {projectId} (DELETE)
http://localhost:8080/test-project/rest/projects/{projectId}/employees/{employeeId} or 
http://localhost:8080/test-project/rest/employees/{employeeId}/projects/{projectId}

Task:


- Create Maven Java project with JPA, JAX-RS, JSON libraries of your choice (example: Hibernate, Jersey, Jackson) and RDBMS ofr your choice (e.g. MySQL, PostreSQL, etc)
- Create JPA entities for Employee (id, name, age), Department (id, name), Project (id, name, start date, end date). The relationship between entities should be as follows: each department has 0 to many employees, one employee works for one and only one department; each project has 0 to many employees assigned to it, one employee may work on 0 to many projects. 
- deploy your project to Application Server of your choice (example: Tomcat)
- Create RESTful services to insert, update, delete, get the data for employees, departments, projects. Use REST client of your choice (example: Chrome plugin Postman) to test your services
