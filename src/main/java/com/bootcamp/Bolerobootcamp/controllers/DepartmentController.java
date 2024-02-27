package com.bootcamp.Bolerobootcamp.controllers;

import com.bootcamp.Bolerobootcamp.models.Department;
import com.bootcamp.Bolerobootcamp.services.IDepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class DepartmentController {
    IDepartmentService departmentService;

    public DepartmentController(IDepartmentService departmentService)
    {
        this.departmentService = departmentService;
    }

    @GetMapping("/departments")
    public List<Department> getAllDepartments()
    {
        return departmentService.getAllDepartments();
    }

    @PostMapping("/departments")
    public Department addDepartment(@RequestBody Department department)
    {
        try
        {
            return departmentService.addDepartment(department);
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @GetMapping("/departments/{id}")
    public Department getDepartmentsById(@PathVariable("id") int id)
    {
        return departmentService.getDepartmentById(id);
    }

    @PutMapping("/departments/{id}")
    public Department updateDepartmentById(@PathVariable("id") int id, @RequestBody Department department)
    {
        try
        {
            return departmentService.updateDepartment(id, department);
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @DeleteMapping("/departments/{id}")
    public int deleteDepartmentById(@PathVariable("id") int id)
    {
        try
        {
            departmentService.deleteDepartmentById(id);
            return id;
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }

    }
}
