package com.vkondrat.experiment.entities;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Project {
    private int id;
    @XmlTransient
    private List<Employee> employeeList;

    private String name;
    private String startDate;
    private String endDate;

    public Project() {
    }

    public Project(List<Employee> employeeList, String name, String startDate, String endDate) throws ParseException {
        this.employeeList = employeeList;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getStartDate() { return startDate; }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


 /*   public Date getStartDate() { return startDate; }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }*/
}
/*public void setStartDate(String startDate) throws ParseException{
        DateFormat formatter;
        formatter = new SimpleDateFormat("MM/dd/yyyy");
        this.StartDate = (Date)formatter.parse(startDate);
        }*/


  /*  public Project(List<Employee> employeeList, String name, String stDate, String eDate) throws ParseException {
        this.employeeList = employeeList;
        this.name = name;
        DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        this.startDate = formatter.parse(stDate);
        this.endDate = formatter.parse(eDate);
    }
*/
   /* public Project(List<Employee> employeeList, String name) {
        this.employeeList = employeeList;
        this.name = name;
    }
*/
