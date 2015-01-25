Prerequisits:

- Maven
- Application Server (example: Tomcat)
- REST Client (example: Chrome plugin Postman)

Task:


- Create Maven Java project with JPA, JAX-RS, JSON libraries of your choice (example: Hibernate, Jersey, Jackson) and RDBMS ofr your choice (e.g. MySQL, PostreSQL, etc)
- Create JPA entities for Employee (id, name, age), Department (id, name), Project (id, name, start date, end date). The relationship between entities should be as follows: each department has 0 to many employees, one employee works for one and only one department; each project has 0 to many employees assigned to it, one employee may work on 0 to many projects. 
- deploy your project to Application Server of your choice (example: Tomcat)
- Create RESTful services to insert, update, delete, get the data for employees, departments, projects. Use REST client of your choice (example: Chrome plugin Postman) to test your services

How to run:

- Download/clone the project;
- Build the project using Maven (mvn clean; mvn compile; mvn package);
- Deploy the project to an Application Server (example for Tomcat: Copy test-project.war from DepartmentLogistics/target to Tomcat/webapps)
- Start an Application Server, Open REST Client 

Examples of HTTP requests:
ROOT = http://localhost:8080/test-project/rest 

1) Add an entity (POST)

http://localhost:8080/test-project/rest/employees
(From now ROOT = http://localhost:8080/test-project/rest)

JSON: {"name":"Vlad", "age":27, “departmentId”:1}
(Note: Employee is assigned to the Department through departmentId) 

ROOT/departments

JSON: {"name":"Mathematics"}

ROOT/projects

JSON: {"name":"Calculus","startDate":"10-10-1990", "endDate":"1-1-1999"}

2) Get all entities (GET)

ROOT/employees (D,P)

3)  Find an entity by Id (GET)

ROOT/employees/Id (D,P)

4) List all employees working for the department {departmentId} (GET) 

ROOT/department/{departmentId}/employees

5) Update an entity or add if it's Id is unique (PUT)

ROOT/employees/Id (D,P)

JSON: {"name":"Vlad","age":27,”departmentId”:2}

6) Delete an entity by Id (DELETE)

ROOT/employees/Id (D,P)

7) Assign the relationship between employee {employeeId} and project {projectId}
(many-to-many) (POST)

ROOT/projects/{projectId}/employees/{employeeId}  
ROOT/employees/{employeeId}/projects/{projectId}

Note: Assigning an employee to a department is done in (1)

8) Return all projects employee is assigned to (GET) 
ROOT/employee/{employeeId}/projects
And the other way
ROOT/project/{employeeId}/employees


9) Unassign employee {employeeId} from project {projectId} (DELETE)
ROOT/projects/{projectId}/employees/{employeeId} or 
ROOT/employees/{employeeId}/projects/{projectId}

