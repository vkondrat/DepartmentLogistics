package com.vkondrat.experiment.dao;

import java.util.List;
import com.vkondrat.experiment.entities.Employee;
import com.vkondrat.experiment.entities.Department;
import com.vkondrat.experiment.entities.Project;


/**
 * Created by vkondrat on 11/26/14.
 */
public interface DepartmentDao {

    public void addEmployee(Employee employee);

    public void updateEmployee(Employee employee);

    public Employee getEmployee(Long employeeId);

    public List<Employee> findAllEmployees();

    public void addDepartment(Department department);

    public void updateDepartment(Department department);

    public Department getDepartment(Long departmentId);

    public List<Department> findDepartment();

    public void addProject(Project project);

    public void updateProject(Project project);

    public Project getProject(Long projectId);

    public List<Project> findProject();
}









