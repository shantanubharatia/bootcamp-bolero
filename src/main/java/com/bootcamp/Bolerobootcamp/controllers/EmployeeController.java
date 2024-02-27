package com.bootcamp.Bolerobootcamp.controllers;

import com.bootcamp.Bolerobootcamp.exceptions.CustomException;
import com.bootcamp.Bolerobootcamp.models.Employee;
import com.bootcamp.Bolerobootcamp.services.EmployeeService;
import jakarta.validation.Valid;
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
    public Employee addEmployee(@Valid @RequestBody Employee employee)
    {
        return employeeService.addEmployee(employee);
    }

    @GetMapping("{id}")
    public Employee getEmployeeById(@PathVariable("id") int id)
    {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping()
    public Employee updateEmployeeById(@Valid @RequestBody Employee employee)
    {
        return employeeService.updateEmployee(employee);
    }

    @DeleteMapping("{id}")
    public int deleteEmployeeById(@PathVariable("id") int id)
    {
        employeeService.deleteEmployeeById(id);
        return id;
    }

}


