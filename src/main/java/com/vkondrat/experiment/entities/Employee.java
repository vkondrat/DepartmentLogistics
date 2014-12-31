package com.vkondrat.experiment.entities;

import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

import javax.persistence.*;
import java.util.List;
import javax.xml.bind.annotation.*;

/**
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Employee implements Common {
    private int id;
    @XmlTransient
    private List<Project> projectList;
    private String name;
    private int age;

    @XmlTransient
    private Department department;
    
    /*@Transient
    private DepartmentID;

    if department!=null
    return department.getID
    else return null;*/


    public Employee(){
    }

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }


    public Employee(String name, int age, List<Project> projectList) {
        this.name = name;
        this.age = age;
        this.projectList = projectList;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @ManyToOne
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @ManyToMany(mappedBy = "employeeList")
    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

}
