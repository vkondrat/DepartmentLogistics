package com.vkondrat.experiment.entities;

import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

import javax.persistence.*;
import java.util.List;
import javax.xml.bind.annotation.*;


/**
 * Created by vkondrat on 11/25/14.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Department implements Common{
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
