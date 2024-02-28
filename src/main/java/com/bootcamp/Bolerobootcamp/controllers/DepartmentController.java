package com.bootcamp.Bolerobootcamp.controllers;

import com.bootcamp.Bolerobootcamp.exceptions.CustomException;
import com.bootcamp.Bolerobootcamp.models.Department;
import com.bootcamp.Bolerobootcamp.services.DepartmentService;
import jakarta.validation.Valid;
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
    public Department addDepartment(@Valid @RequestBody Department department)
    {
        return departmentService.addDepartment(department);
    }

    @GetMapping("{id}")
    public Department getDepartmentsById(@PathVariable("id") int id)
    {
        return departmentService.getDepartmentById(id);
    }

    @PutMapping()
    public Department updateDepartmentById(@Valid @RequestBody Department department)
    {
        return departmentService.updateDepartment(department);
    }

    @DeleteMapping("{id}")
    public int deleteDepartmentById(@PathVariable("id") int id)
    {
        departmentService.deleteDepartmentById(id);
        return id;
    }
}
