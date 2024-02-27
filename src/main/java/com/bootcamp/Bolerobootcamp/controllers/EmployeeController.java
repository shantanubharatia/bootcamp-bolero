package com.bootcamp.Bolerobootcamp.controllers;

import com.bootcamp.Bolerobootcamp.exceptions.CustomException;
import com.bootcamp.Bolerobootcamp.models.Employee;
import com.bootcamp.Bolerobootcamp.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService)
    {
        this.employeeService = employeeService;
    }

    @GetMapping()
    public List<Employee> getAllEmployees()
    {
        return employeeService.getAllEmployees();
    }

    @PostMapping()
    public Employee addEmployee(@RequestBody Employee employee)
    {
        try
        {
            return employeeService.addEmployee(employee);
        }
        catch(CustomException ex)
        {
            throw new ResponseStatusException(ex.getStatusCode(), ex.getMessage());
        }
    }

    @GetMapping("{id}")
    public Employee getEmployeeById(@PathVariable("id") int id)
    {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping()
    public Employee updateEmployeeById(@RequestBody Employee employee)
    {
        try
        {
            return employeeService.updateEmployee(employee);
        }
        catch(CustomException ex)
        {
            throw new ResponseStatusException(ex.getStatusCode(), ex.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public int deleteEmployeeById(@PathVariable("id") int id)
    {
        try
        {
            employeeService.deleteEmployeeById(id);
            return id;
        }
        catch(CustomException ex)
        {
            throw new ResponseStatusException(ex.getStatusCode(), ex.getMessage());
        }
    }
}


