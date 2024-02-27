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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DepartmentServiceImplTest {

    @InjectMocks
    private DepartmentServiceImpl departmentServiceImpl;
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
    void getAllDepartmentsIsSuccessful() {
        Department d1 = new Department();
        d1.setName("department 1");

        Department d2 = new Department();
        d2.setName("department 2");

        Employee e1 = new Employee();
        e1.setName("Shantanu");
        d1.addEmployee(e1);
        d2.addEmployee(e1);

        List<Department> expectedDepartmentList = Arrays.asList(d1, d2);

        when(departmentRepository.findAll()).thenReturn(expectedDepartmentList);

        List<Department> actualDepartmentList = departmentServiceImpl.getAllDepartments();

        assertEquals(expectedDepartmentList.size(), actualDepartmentList.size());
        for(int i=0; i<expectedDepartmentList.size(); i++)
        {
            validateDepartment(expectedDepartmentList.get(i), actualDepartmentList.get(i));

        }


    }

    @Test
    void getDepartmentByIdIsSuccessful() {
        Department d1 = new Department();
        d1.setName("department 1");

        Employee e1 = new Employee();
        e1.setName("Shantanu");
        d1.addEmployee(e1);

        when(departmentRepository.findById(d1.getId())).thenReturn(Optional.of(d1));

        Department actualDepartment = departmentServiceImpl.getDepartmentById(d1.getId());

        validateDepartment(d1, actualDepartment);
    }

    @Test
    void addDepartmentIsSuccessful() {
        Department d1 = new Department();
        d1.setName("department 1");
        d1.setId(1);

        Employee e1 = new Employee();
        e1.setId(1);
        e1.setName("Shantanu");
        d1.addEmployee(e1);

        Employee e2 = new Employee();
        e2.setId(2);
        e2.setName("Bharatia");
        d1.addEmployee(e2);

        when(employeeRepository.findById(e1.getId())).thenReturn(Optional.of(e1));
        when(employeeRepository.findById(e2.getId())).thenReturn(Optional.of(e2));

        Department addedDepartment = departmentServiceImpl.addDepartment(d1);
        //since we have mocked the save function, hence the added department won't have the id populated automatically. Hence adding it
        addedDepartment.setId(1);

        validateDepartment(d1, addedDepartment);
    }

    @Test
    void addDepartmentFailsDueToInvalidEmployeeId()
    {
        Department d1 = new Department();
        d1.setId(1);
        d1.setName("Organisation");

        Employee e1 = new Employee();
        e1.setName("Shantanu");
        e1.setId(1);

        d1.addEmployee(e1);

        when(employeeRepository.findById(e1.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> departmentServiceImpl.addDepartment(d1));
        assertEquals("employee with employee id = "+e1.getId()+" not found", exception.getMessage());
    }

    @Test
    void addDepartmentFailsDueToMissingNameInInput()
    {
        Department d1 = new Department();
        d1.setId(1);
        Employee e1 = new Employee();
        d1.addEmployee(e1);

        Exception exception = assertThrows(RuntimeException.class, () -> departmentServiceImpl.addDepartment(d1));
        assertEquals("Name cannot be empty", exception.getMessage());
    }

    @Test
    void updateDepartmentIsSuccessful() {

        //existing department
        Department existingDepartment = new Department();
        existingDepartment.setName("HR");
        existingDepartment.setId(1);

        //department details to be updated - //we are trying to add to add two employees, and change name
        Department d1 = new Department();
        d1.setName("HR_Updated");
        d1.setId(1);
        Employee e1 = new Employee();
        e1.setId(1);
        e1.setName("Employee-1");
        Employee e2 = new Employee();
        e2.setId(2);
        e2.setName("Employee-2");

        d1.addEmployee(e1);
        d1.addEmployee(e2);

        when(departmentRepository.findById(d1.getId())).thenReturn(Optional.of(existingDepartment));
        when(employeeRepository.findById(e1.getId())).thenReturn(Optional.of(e1));
        when(employeeRepository.findById(e2.getId())).thenReturn(Optional.of(e2));

        Department updatedDepartment = departmentServiceImpl.updateDepartment(d1);
        //since we have mocked the save function, hence the added employee won't have the id populated automatically. Hence adding it
        updatedDepartment.setId(1);

        validateDepartment(d1, updatedDepartment);
    }

    @Test
    void updateDepartmentFailsDueToInvalidDepartmentId() {

        //existing department
        Department existingDepartment = new Department();
        existingDepartment.setName("HR");
        existingDepartment.setId(1);

        //department details to be updated - //we are trying to add to add two employees, and change name
        Department d1 = new Department();
        d1.setName("HR_Updated");
        d1.setId(1);
        Employee e1 = new Employee();
        e1.setId(1);
        e1.setName("Employee-1");
        Employee e2 = new Employee();
        e2.setId(2);
        e2.setName("Employee-2");

        d1.addEmployee(e1);
        d1.addEmployee(e2);

        when(departmentRepository.findById(d1.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> departmentServiceImpl.updateDepartment(d1));
        assertEquals("Invalid department id", exception.getMessage());
    }

    @Test
    void updateDepartmentFailsDueToInvalidDepartmentName() {

        //existing department
        Department existingDepartment = new Department();
        existingDepartment.setId(1);
        existingDepartment.setName("HR");

        //department details to be updated - //we are trying to add to add two employees, and change name
        Department d1 = new Department();
        d1.setId(1);
        Employee e1 = new Employee();
        e1.setId(1);
        e1.setName("Employee-1");
        Employee e2 = new Employee();
        e2.setId(2);
        e2.setName("Employee-2");

        d1.addEmployee(e1);
        d1.addEmployee(e2);

        when(departmentRepository.findById(d1.getId())).thenReturn(Optional.of(existingDepartment));

        Exception exception = assertThrows(RuntimeException.class, () -> departmentServiceImpl.updateDepartment(d1));
        assertEquals("Name cannot be empty", exception.getMessage());
    }

    @Test
    void updateDepartmentFailsDueToInvalidEmployeeId() {

        //existing department
        Department existingDepartment = new Department();
        existingDepartment.setId(1);
        existingDepartment.setName("HR");

        //department details to be updated - //we are trying to add to add two employees, and change name
        Department d1 = new Department();
        d1.setName("HR_Updated");
        d1.setId(1);
        Employee e1 = new Employee();
        e1.setId(1);
        e1.setName("Employee-1");
        Employee e2 = new Employee();
        e2.setId(2);
        e2.setName("Employee-2");

        d1.addEmployee(e1);
        d1.addEmployee(e2);

        when(departmentRepository.findById(d1.getId())).thenReturn(Optional.of(existingDepartment));
        when(employeeRepository.findById(e1.getId())).thenReturn(Optional.empty());
        when(employeeRepository.findById(e2.getId())).thenReturn(Optional.of(e2));

        Exception exception = assertThrows(RuntimeException.class, () -> departmentServiceImpl.updateDepartment(d1));
        assertEquals("employee with employee id = "+e1.getId()+" not found", exception.getMessage());
    }

    @Test
    void updateDepartmentFailsForReadOnlyDepartments() {

        //existing department
        Department existingDepartment = new Department();
        existingDepartment.setId(1);
        existingDepartment.setName("HR");
        existingDepartment.setReadOnly(true);

        //department details to be updated - //we are trying to add to add two employees, and change name
        Department d1 = new Department();
        d1.setName("HR_Updated");
        d1.setId(1);
        Employee e1 = new Employee();
        e1.setId(1);
        e1.setName("Employee-1");
        Employee e2 = new Employee();
        e2.setId(2);
        e2.setName("Employee-2");

        d1.addEmployee(e1);
        d1.addEmployee(e2);

        when(departmentRepository.findById(d1.getId())).thenReturn(Optional.of(existingDepartment));
        when(employeeRepository.findById(e1.getId())).thenReturn(Optional.of(e1));
        when(employeeRepository.findById(e2.getId())).thenReturn(Optional.of(e2));

        Exception exception = assertThrows(RuntimeException.class, () -> departmentServiceImpl.updateDepartment(d1));
        assertEquals("Cannot update department as it is read only", exception.getMessage());
    }

    @Test
    void deleteDepartmentById() {
        Employee e1 = new Employee();
        e1.setName("Shantanu");
        Department d1 = new Department();
        d1.setName("Organisation");
        d1.addEmployee(e1);
        e1.setId(1);

        when(departmentRepository.findById(d1.getId())).thenReturn(Optional.of(d1));

        departmentServiceImpl.deleteDepartmentById(d1.getId());

        //nothing to assert - no exceptions thrown means delete was successful
    }

    @Test
    void deleteDepartmentByIdFailsDueToInvalidDepartmentId() {
        Employee e1 = new Employee();
        e1.setName("Shantanu");
        Department d1 = new Department();
        d1.setName("Organisation");
        d1.addEmployee(e1);
        e1.setId(1);

        Exception ex = assertThrows(RuntimeException.class, () -> departmentServiceImpl.deleteDepartmentById(d1.getId()));
        assertEquals("Invalid department id", ex.getMessage());
    }

    @Test
    void deleteDepartmentByIdFailsDueForReadOnlyDepartment() {
        Employee e1 = new Employee();
        e1.setName("Shantanu");
        Department d1 = new Department();
        d1.setName("Organisation");
        d1.addEmployee(e1);
        e1.setId(1);
        d1.setReadOnly(true);

        when(departmentRepository.findById(d1.getId())).thenReturn(Optional.of(d1));

        Exception ex = assertThrows(RuntimeException.class, () -> departmentServiceImpl.deleteDepartmentById(d1.getId()));
        assertEquals("Cannot delete department as it is read only", ex.getMessage());
    }

    private void validateDepartment(Department expectedDepartment, Department actualDepartment)
    {
        assertEquals(expectedDepartment.getId(), actualDepartment.getId());
        assertEquals(expectedDepartment.getName(), actualDepartment.getName());
        assertEquals(expectedDepartment.isReadOnly(), actualDepartment.isReadOnly());
        assertEquals(expectedDepartment.isMandatory(), actualDepartment.isMandatory());

        Set<Employee> expectedEmployees = expectedDepartment.getEmployees();
        Set<Employee> actualEmployees = actualDepartment.getEmployees();
        assertEquals(expectedEmployees.size(), actualEmployees.size());

        for(Employee e : expectedEmployees)
        {
            assertTrue(actualEmployees.contains(e));
        }
    }
}