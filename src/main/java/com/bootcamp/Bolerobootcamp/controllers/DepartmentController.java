package com.bootcamp.Bolerobootcamp.controllers;

import com.bootcamp.Bolerobootcamp.exceptions.CustomException;
import com.bootcamp.Bolerobootcamp.models.Department;
import com.bootcamp.Bolerobootcamp.services.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("departments")
public class DepartmentController {
    DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService)
    {
        this.departmentService = departmentService;
    }

    @GetMapping()
    public List<Department> getAllDepartments()
    {
        return departmentService.getAllDepartments();
    }

    @PostMapping()
    public Department addDepartment(@RequestBody Department department)
    {
        try
        {
            return departmentService.addDepartment(department);
        }
        catch(CustomException ex)
        {
            throw new ResponseStatusException(ex.getStatusCode(), ex.getMessage());
        }
    }

    @GetMapping("{id}")
    public Department getDepartmentsById(@PathVariable("id") int id)
    {
        return departmentService.getDepartmentById(id);
    }

    @PutMapping()
    public Department updateDepartmentById(@RequestBody Department department)
    {
        try
        {
            return departmentService.updateDepartment(department);
        }
        catch(CustomException ex)
        {
            throw new ResponseStatusException(ex.getStatusCode(), ex.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public int deleteDepartmentById(@PathVariable("id") int id)
    {
        try
        {
            departmentService.deleteDepartmentById(id);
            return id;
        }
        catch(CustomException ex)
        {
            throw new ResponseStatusException(ex.getStatusCode(), ex.getMessage());
        }

    }
}
