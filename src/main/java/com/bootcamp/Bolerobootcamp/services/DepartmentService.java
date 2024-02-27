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
public class DepartmentService implements IDepartmentService{
    private final IDepartmentRepository departmentRepository;
    private final IEmployeeRepository employeeRepository;

    public DepartmentService(IDepartmentRepository departmentRepository, IEmployeeRepository employeeRepository)
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
        Department newDepartment = new Department();
        if(department.getName() == null)
        {
            throw new RuntimeException("Name cannot be empty");
        }
        newDepartment.setName(department.getName());

        this.setEmployeesForDepartment(department, newDepartment);

        departmentRepository.save(newDepartment);
        return newDepartment;
    }

    public Department updateDepartment(int id, Department department)
    {
        var existingDepartment = departmentRepository.findById(id).orElse(null);
        if(existingDepartment == null)
        {
            //department with the given id does not exist
            throw new RuntimeException("Invalid department id");
        }
        else if(existingDepartment.isReadOnly())
        {
            throw new RuntimeException("Cannot update department as it is read only");
        }

        if(department.getName() == null)
        {
            throw new RuntimeException("Name cannot be empty");
        }
        existingDepartment.setName(department.getName());

        //deleting the relationships with employees before deleting the department itself
        for(Employee e : existingDepartment.getEmployees())
        {
            e.getDepartments().remove(existingDepartment);
        }
        existingDepartment.setEmployees(new HashSet<>());

        setEmployeesForDepartment(department, existingDepartment);

        departmentRepository.save(existingDepartment);

        return existingDepartment;
    }

    public void deleteDepartmentById(int id)
    {
        var existingDepartment = departmentRepository.findById(id).orElse(null);
        if(existingDepartment == null)
        {
            //department with the given id does not exist
            throw new RuntimeException("Invalid department id");
        }
        else if(existingDepartment.isReadOnly())
        {
            throw new RuntimeException("Cannot delete department as it is read only");
        }

        //deleting the relationships with employees before deleting the department itself
        for(Employee e : existingDepartment.getEmployees())
        {
            e.getDepartments().remove(existingDepartment);
        }
        departmentRepository.deleteById(id);
    }

    private void setEmployeesForDepartment(Department inputDepartment, Department department)
    {
        for (Employee employee : inputDepartment.getEmployees())
        {
            Employee emp = employeeRepository.findById(employee.getId()).orElse(null);
            if (emp==null)
            {
                throw new RuntimeException("employee with employee id = "+employee.getId()+" not found");
            }
            department.addEmployee(emp); // to populate the return object
            emp.addDepartment(department); // since employee is the parent of the relationship, hence it has to add the departments
        }
    }
}
