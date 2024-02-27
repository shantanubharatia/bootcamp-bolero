package com.bootcamp.Bolerobootcamp.services;

import com.bootcamp.Bolerobootcamp.models.Employee;

import java.util.List;

public interface IEmployeeService {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(int id);
    Employee addEmployee(Employee employeeDTO);
    Employee updateEmployee(int id, Employee employeeDTO);
    void deleteEmployeeById(int id);

}
