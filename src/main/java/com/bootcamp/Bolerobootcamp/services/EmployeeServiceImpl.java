package com.bootcamp.Bolerobootcamp.services;

import com.bootcamp.Bolerobootcamp.exceptions.CustomException;
import com.bootcamp.Bolerobootcamp.models.Department;
import com.bootcamp.Bolerobootcamp.models.Employee;
import com.bootcamp.Bolerobootcamp.repositories.DepartmentRepository;
import com.bootcamp.Bolerobootcamp.repositories.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository)
    {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Employee> getAllEmployees()
    {
        List<Employee> employees = new ArrayList<Employee>();
        employeeRepository.findAll().forEach(emp -> employees.add((Employee)emp));
        return employees;
    }

    public Employee getEmployeeById(int id)
    {
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee addEmployee(Employee employee) {
        if(employee.getName() == null)
        {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Name cannot be empty");
        }

        validateDepartmentsForEmployee(employee);
        //add the default departments
        this.addMandatoryDepartmentsToEmployee(employee);
        employeeRepository.save(employee);
        return employee;
    }

    public Employee updateEmployee(Employee employee)
    {
        employeeRepository.findById(employee.getId()).orElseThrow(() -> new RuntimeException("Invalid employee id"));
        if(employee.getName() == null)
        {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Name cannot be empty");
        }
        validateDepartmentsForEmployee(employee);
        //add the default departments
        this.addMandatoryDepartmentsToEmployee(employee);
        employeeRepository.save(employee);
        return employee;
    }

    public void deleteEmployeeById(int id)
    {
        employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Invalid employee id"));
        employeeRepository.deleteById(id);
    }

    private void validateDepartmentsForEmployee(Employee inputEmployee)
    {
        for (Department department: inputEmployee.getDepartments())
        {
            departmentRepository.findById(department.getId()).orElseThrow(() -> new RuntimeException("department with department id = "+department.getId()+" not found"));
        }
    }

    private void addMandatoryDepartmentsToEmployee(Employee employee)
    {
        for(Department dept : departmentRepository.findByMandatory(true))
        {
            if(employee.getDepartments().stream().noneMatch(x -> x.getId()==dept.getId()))
            {
                employee.addDepartment(dept);
            }
        }
    }
}
