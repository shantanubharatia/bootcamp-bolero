package com.bootcamp.Bolerobootcamp.services;

import com.bootcamp.Bolerobootcamp.models.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartments();
    Department getDepartmentById(int id);
    Department addDepartment(Department department);
    Department updateDepartment(Department department);
    void deleteDepartmentById(int id);
}
