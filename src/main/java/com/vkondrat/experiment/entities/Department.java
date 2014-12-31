package com.vkondrat.experiment.entities;

import javax.persistence.*;
import java.util.List;
import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity

  public class Department{
    private int id;

    @XmlTransient
    private List<Employee> employeeList;

    private String name;

    public Department() {
    }

    public Department(List<Employee> employeeList, String name) {
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

    @OneToMany(mappedBy = "department")
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
}
