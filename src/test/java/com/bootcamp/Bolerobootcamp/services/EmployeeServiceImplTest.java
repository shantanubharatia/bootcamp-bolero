package com.bootcamp.Bolerobootcamp.services;

import com.bootcamp.Bolerobootcamp.models.Department;
import com.bootcamp.Bolerobootcamp.models.Employee;
import com.bootcamp.Bolerobootcamp.repositories.DepartmentRepository;
import com.bootcamp.Bolerobootcamp.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private DepartmentRepository departmentRepository;

    @BeforeEach
    void setup()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEmployeesIsSuccessful() {
        Department d1 = new Department();
        d1.setName("Organisation");

        Employee e1 = new Employee();
        e1.setName("Shantanu");
        e1.addDepartment(d1);
        e1.setId(1);

        Employee e2 = new Employee();
        e2.setName("Bharatia");
        e2.addDepartment(d1);
        e2.setId(2);

        List<Employee> expectedEmployeeList = Arrays.asList(e1, e2);

        when(employeeRepository.findAll()).thenReturn(expectedEmployeeList);

        List<Employee> actualEmployeeList = employeeServiceImpl.getAllEmployees();

        assertEquals(expectedEmployeeList.size(), actualEmployeeList.size());
        for(int i=0; i<expectedEmployeeList.size(); i++)
        {
            validateEmployee(expectedEmployeeList.get(i), actualEmployeeList.get(i));

        }
    }

    @Test
    void getEmployeeByIdIsSuccessful()
    {
        Department d1 = new Department();
        d1.setName("Organisation");
        Employee e1 = new Employee();
        e1.setName("Shantanu");
        e1.addDepartment(d1);
        e1.setId(1);

        when(employeeRepository.findById(e1.getId())).thenReturn(Optional.of(e1));

        Employee actualEmployee = employeeServiceImpl.getEmployeeById(e1.getId());

        validateEmployee(e1, actualEmployee);
    }

    @Test
    void addEmployeeIsSuccesfulAndAddsTheMandatoryDepartments()
    {
        Department d1 = new Department();
        d1.setId(1);
        d1.setName("Organisation");
        d1.setMandatory(true);
        Department d2 = new Department();
        d2.setId(2);
        d2.setName("HR");
        d2.setMandatory(false);
        Employee e1 = new Employee();
        e1.setName("Shantanu");
        e1.addDepartment(d2);
        e1.setId(1);

        when(departmentRepository.findById(d1.getId())).thenReturn(Optional.of(d1));
        when(departmentRepository.findById(d2.getId())).thenReturn(Optional.of(d2));
        when(departmentRepository.findByMandatory(true)).thenReturn(List.of(d1));

        Employee addedEmployee = employeeServiceImpl.addEmployee(e1);
        //since we have mocked the save function, hence the added employee won't have the id populated automatically. Hence adding it
        addedEmployee.setId(1);

        //expected employee should have the mandatory department too
        e1.addDepartment(d1);
        validateEmployee(e1, addedEmployee);
    }

    @Test
    void addEmployeeFailsDueToInvalidDepartmentId()
    {
        Department d1 = new Department();
        d1.setId(1);
        d1.setName("Organisation");

        Employee e1 = new Employee();
        e1.setName("Shantanu");
        e1.addDepartment(d1);
        e1.setId(1);

        when(departmentRepository.findById(d1.getId())).thenReturn(Optional.empty());
        when(departmentRepository.findByMandatory(true)).thenReturn(List.of(d1));

        Exception exception = assertThrows(RuntimeException.class, () -> employeeServiceImpl.addEmployee(e1));
        assertEquals("department with department id = "+d1.getId()+" not found", exception.getMessage());
    }

    @Test
    void updateEmployeeIsSuccesfulAndAddsTheMandatoryDepartments()
    {
        //existing employee
        Employee existingEmployee = new Employee();
        existingEmployee.setName("Shantanu");
        existingEmployee.setId(1);

        //employee details to be updated - //we are trying to add department d2, and change name
        Employee e1 = new Employee();
        e1.setName("ShantanuUpdated");
        e1.setId(1);
        Department d1 = new Department();
        d1.setId(1);
        d1.setName("Organisation");
        d1.setMandatory(true);
        Department d2 = new Department();
        d2.setId(2);
        d2.setName("HR");
        d2.setMandatory(false);
        e1.addDepartment(d2);

        when(employeeRepository.findById(e1.getId())).thenReturn(Optional.of(existingEmployee));
        when(departmentRepository.findById(d1.getId())).thenReturn(Optional.of(d1));
        when(departmentRepository.findById(d2.getId())).thenReturn(Optional.of(d2));
        when(departmentRepository.findByMandatory(true)).thenReturn(List.of(d1));

        Employee updatedEmployee = employeeServiceImpl.updateEmployee(e1);
        //since we have mocked the save function, hence the added employee won't have the id populated automatically. Hence adding it
        updatedEmployee.setId(1);

        //expected employee should have the mandatory department too
        e1.addDepartment(d1);
        validateEmployee(e1, updatedEmployee);
    }

    @Test
    void updateEmployeeFailsDueToInvalidEmployeeId()
    {
        //existing employee
        Employee existingEmployee = new Employee();
        existingEmployee.setName("Shantanu");
        existingEmployee.setId(1);

        //employee details to be updated - //we are trying to add department d2, and change name
        Employee e1 = new Employee();
        e1.setId(1);
        e1.setName("ShantanuUpdated");
        Department d1 = new Department();
        d1.setId(1);
        d1.setName("Organisation");
        d1.setMandatory(true);
        Department d2 = new Department();
        d2.setId(2);
        d2.setName("HR");
        d2.setMandatory(false);
        e1.addDepartment(d2);

        when(employeeRepository.findById(e1.getId())).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> employeeServiceImpl.updateEmployee(e1));
        assertEquals("Invalid employee id", exception.getMessage());
    }

    @Test
    void updateEmployeeFailsDueToInvalidDepartmentId()
    {
        //existing employee
        Employee existingEmployee = new Employee();
        existingEmployee.setName("Shantanu");
        existingEmployee.setId(1);

        //employee details to be updated - //we are trying to add department d2, and change name
        Employee e1 = new Employee();
        e1.setId(1);
        e1.setName("Shantanu");
        Department d1 = new Department();
        d1.setId(1);
        d1.setName("Organisation");
        d1.setMandatory(true);
        Department d2 = new Department();
        d2.setId(2);
        d2.setName("HR");
        d2.setMandatory(false);
        e1.addDepartment(d2);


        when(departmentRepository.findById(d1.getId())).thenReturn(Optional.empty());
        when(departmentRepository.findByMandatory(true)).thenReturn(List.of(d1));
        when(employeeRepository.findById(e1.getId())).thenReturn(Optional.of(existingEmployee));
        Exception exception = assertThrows(RuntimeException.class, () -> employeeServiceImpl.updateEmployee(e1));
        assertEquals("department with department id = "+d2.getId()+" not found", exception.getMessage());

    }

    @Test
    void deleteByIdIsSuccessful()
    {
        Department d1 = new Department();
        d1.setName("Organisation");
        Employee e1 = new Employee();
        e1.setName("Shantanu");
        e1.addDepartment(d1);
        e1.setId(1);

        when(employeeRepository.findById(e1.getId())).thenReturn(Optional.of(e1));

        employeeServiceImpl.deleteEmployeeById(e1.getId());

        //nothing to assert - no exceptions thrown means delete was successful
    }

    @Test
    void deleteByIdFailsDueToInvalidEmployeeId()
    {
        Department d1 = new Department();
        d1.setName("Organisation");
        Employee e1 = new Employee();
        e1.setName("Shantanu");
        e1.addDepartment(d1);
        e1.setId(1);

        Exception ex = assertThrows(RuntimeException.class, () -> employeeServiceImpl.deleteEmployeeById(e1.getId()));
        assertEquals("Invalid employee id", ex.getMessage());
    }

    private void validateEmployee(Employee expectedEmployee, Employee actualEmployee)
    {
        assertEquals(expectedEmployee.getId(), actualEmployee.getId());
        assertEquals(expectedEmployee.getName(), actualEmployee.getName());

        Set<Department> expectedDepartments = expectedEmployee.getDepartments();
        Set<Department> actualDepartments = actualEmployee.getDepartments();
        assertEquals(expectedDepartments.size(), actualDepartments.size());

        for(Department d : expectedDepartments)
        {
            assertTrue(actualDepartments.contains(d));
        }
    }
}