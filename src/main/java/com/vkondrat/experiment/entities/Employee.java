package com.vkondrat.experiment.entities;

import com.vkondrat.experiment.util.JPAUtil;

import javax.persistence.*;
import java.util.List;
import javax.xml.bind.annotation.*;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Employee {
    private int id;

    @XmlTransient
    private List<Project> projectList;

    private String name;
    private int age;

    @XmlTransient
    private Department department;
    @XmlTransient
    private int departmentID;

    private String departmentName;

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

    public Employee(String name, int age, List<Project> projectList, int departmentID) {
        this.name = name;
        this.age = age;
        this.projectList = projectList;
        this.departmentID = departmentID;
        setDepartment(departmentID);
        this.departmentName=department.getName();
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

    public void setDepartment(int departmentID)
    {
        if (departmentID!=0)
            try {
                EntityManager em = JPAUtil.getInstance().getEm();
                em.getTransaction().begin();
                Department entity = em.find(Department.class, departmentID);
                department = entity;
            } catch (NoResultException e) {
                department=null;
            }
    }

    public void setDepartment()
    {
        if (departmentID!=0)
            try {
                EntityManager em = JPAUtil.getInstance().getEm();
                em.getTransaction().begin();
                Department entity = em.find(Department.class, departmentID);
                department = entity;
            } catch (NoResultException e) {
                department=null;
            }
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public String getDepartmentName() {
        if (department!=null)
            return department.getName();
        else
            return "No Department";
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    @ManyToMany(mappedBy = "employeeList")
    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }
}


    /*@Transient
    private DepartmentID;

    if department!=null
    return department.getID
    else return null;*/

    /*  public int getDepartmentID() {
        if (department!=null)
            return this.department.getId();
        else
            return 0;
    }*/
