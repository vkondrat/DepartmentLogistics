package com.vkondrat.experiment.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
public class Project {
    private int id;
    private List<Employee> employeeList;
    private String name;
    private Date StartDate;
    private Date EndDate;

    public Project() {
    }

    public Project(List<Employee> employeeList, String name, Date startDate, Date endDate) {
        this.employeeList = employeeList;
        this.name = name;
        StartDate = startDate;
        EndDate = endDate;
    }

    public Project(List<Employee> employeeList, String name) {
        this.employeeList = employeeList;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }
}
