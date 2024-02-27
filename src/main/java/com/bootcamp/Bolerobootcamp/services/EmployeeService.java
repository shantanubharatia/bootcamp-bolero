package com.bootcamp.Bolerobootcamp.services;

import com.bootcamp.Bolerobootcamp.models.Department;
import com.bootcamp.Bolerobootcamp.models.Employee;
import com.bootcamp.Bolerobootcamp.repositories.IDepartmentRepository;
import com.bootcamp.Bolerobootcamp.repositories.IEmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class EmployeeService implements IEmployeeService{
    private final IEmployeeRepository employeeRepository;
    private final IDepartmentRepository departmentRepository;

    public EmployeeService(IEmployeeRepository employeeRepository, IDepartmentRepository departmentRepository)
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

    public Employee addEmployee(Employee employee)
    {
        Employee newEmployee = new Employee();
        if(employee.getName() == null)
        {
            throw new RuntimeException("Name cannot be empty");
        }
        newEmployee.setName(employee.getName());

        setDepartmentsForEmployee(employee, newEmployee);

        employeeRepository.save(newEmployee);
        return newEmployee;
    }

    public Employee updateEmployee(int id, Employee employee)
    {
        var existingEmployee = employeeRepository.findById(id).orElse(null);
        if(existingEmployee == null)
        {
            //employee with the given id does not exist
            throw new RuntimeException("Invalid employee id");
        }
        if(employee.getName() == null)
        {
            throw new RuntimeException("Name cannot be empty");
        }
        existingEmployee.setName(employee.getName());
        existingEmployee.setDepartments(new HashSet<>());
        setDepartmentsForEmployee(employee, existingEmployee);

        employeeRepository.save(existingEmployee);
        return existingEmployee;
    }

    public void deleteEmployeeById(int id)
    {
        if(employeeRepository.findById(id).orElse(null) == null)
        {
            //employee with the given id does not exist
            throw new RuntimeException("Invalid employee id");
        }
        employeeRepository.deleteById(id);
    }

    private void setDepartmentsForEmployee(Employee inputEmployee, Employee employee)
    {
        for (Department department: inputEmployee.getDepartments())
        {
            Department dept = departmentRepository.findById(department.getId()).orElse(null);
            if (dept==null)
            {
                throw new RuntimeException("department with department id = "+department.getId()+" not found");
            }
            employee.addDepartment(dept);
        }

        //add the default departments
        this.addMandatoryDepartmentsToEmployee(employee);
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
