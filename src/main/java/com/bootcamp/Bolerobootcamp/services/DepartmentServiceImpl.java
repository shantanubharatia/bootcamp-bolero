package com.bootcamp.Bolerobootcamp.services;

import com.bootcamp.Bolerobootcamp.exceptions.CustomException;
import com.bootcamp.Bolerobootcamp.models.Department;
import com.bootcamp.Bolerobootcamp.models.Employee;
import com.bootcamp.Bolerobootcamp.repositories.DepartmentRepository;
import com.bootcamp.Bolerobootcamp.repositories.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository)
    {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Department> getAllDepartments()
    {
        List<Department> departments = new ArrayList<Department>();
        departmentRepository.findAll().forEach(dep -> departments.add((Department)dep));
        return departments;
    }

    public Department getDepartmentById(int id)
    {
        return departmentRepository.findById(id).orElse(null);
    }

    public Department addDepartment(Department department)
    {
        this.setEmployeesForDepartment(department);

        departmentRepository.save(department);
        return department;
    }

    public Department updateDepartment(Department department)
    {
        Department existingDepartment = departmentRepository.findById(department.getId()).orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "Invalid department id"));

        if(existingDepartment.isReadOnly())
        {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Cannot update department as it is read only");
        }
        departmentRepository.save(department);

        return department;
    }

    public void deleteDepartmentById(int id)
    {
        Department existingDepartment = departmentRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "Invalid department id"));

        if(existingDepartment.isReadOnly())
        {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Cannot delete department as it is read only");
        }

        //deleting the relationships with employees before deleting the department itself
        for(Employee e : existingDepartment.getEmployees())
        {
            e.getDepartments().remove(existingDepartment);
        }
        departmentRepository.deleteById(id);
    }

    private void setEmployeesForDepartment(Department inputDepartment)
    {
        for (Employee employee : inputDepartment.getEmployees())
        {
            Employee emp = employeeRepository.findById(employee.getId()).orElseThrow(() -> new RuntimeException("employee with employee id = "+employee.getId()+" not found"));
            emp.addDepartment(inputDepartment); // since employee is the parent of the relationship, hence it has to add the departments
        }
    }
}
