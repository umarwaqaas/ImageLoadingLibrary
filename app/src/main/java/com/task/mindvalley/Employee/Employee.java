package com.task.mindvalley.Employee;

/**
 * Created by Umer Waqas on 10/08/2017.
 */

public class Employee {
    private String employeeID;
    private String employeeName;
    private String employeeAvatarUrl;
    private String employeeUsername;

    public String getEmployeeUsername() {
        return employeeUsername;
    }

    public void setEmployeeUsername(String employeeUsername) {
        this.employeeUsername = employeeUsername;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeAvatarUrl() {
        return employeeAvatarUrl;
    }

    public void setEmployeeAvatarUrl(String employeeAvatarUrl) {
        this.employeeAvatarUrl = employeeAvatarUrl;
    }
}
