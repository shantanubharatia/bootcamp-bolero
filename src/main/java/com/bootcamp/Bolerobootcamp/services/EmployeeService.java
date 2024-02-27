package com.bootcamp.Bolerobootcamp.services;

import com.bootcamp.Bolerobootcamp.exceptions.CustomException;
import com.bootcamp.Bolerobootcamp.models.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(int id);
    Employee addEmployee(Employee employeeDTO) throws CustomException;
    Employee updateEmployee(Employee employeeDTO);
    void deleteEmployeeById(int id);

}
