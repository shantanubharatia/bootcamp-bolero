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

        Department addedDepartment = departmentServiceImpl.addDepartment(d1);
        //since we have mocked the save function, hence the added department won't have the id populated automatically. Hence adding it
        addedDepartment.setId(1);

        validateDepartment(d1, addedDepartment);
    }

    @Test
    void updateDepartmentIsSuccessful() {

        //existing department
        Department existingDepartment = new Department();
        existingDepartment.setName("HR");
        existingDepartment.setId(1);

        //department details to be updated - we are trying change name
        Department d1 = new Department();
        d1.setName("HR_Updated");

        when(departmentRepository.findById(d1.getId())).thenReturn(Optional.of(existingDepartment));

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

        //department details to be updated - //we are trying to change name
        Department d1 = new Department();
        d1.setName("HR_Updated");
        d1.setId(1);

        when(departmentRepository.findById(d1.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> departmentServiceImpl.updateDepartment(d1));
        assertEquals("Invalid department id", exception.getMessage());
    }

    @Test
    void updateDepartmentFailsForReadOnlyDepartments() {

        //existing department
        Department existingDepartment = new Department();
        existingDepartment.setId(1);
        existingDepartment.setName("HR");
        existingDepartment.setReadOnly(true);

        //trying to update department name
        Department d1 = new Department();
        d1.setName("HR_Updated");
        d1.setId(1);

        when(departmentRepository.findById(d1.getId())).thenReturn(Optional.of(existingDepartment));

        Exception exception = assertThrows(RuntimeException.class, () -> departmentServiceImpl.updateDepartment(d1));
        assertEquals("Cannot update department as it is read only", exception.getMessage());
    }

    @Test
    void deleteDepartmentById() {
        Department d1 = new Department();
        d1.setName("Organisation");

        when(departmentRepository.findById(d1.getId())).thenReturn(Optional.of(d1));

        departmentServiceImpl.deleteDepartmentById(d1.getId());

        //nothing to assert - no exceptions thrown means delete was successful
    }

    @Test
    void deleteDepartmentByIdFailsDueToInvalidDepartmentId() {
        Department d1 = new Department();
        d1.setName("Organisation");

        Exception ex = assertThrows(RuntimeException.class, () -> departmentServiceImpl.deleteDepartmentById(d1.getId()));
        assertEquals("Invalid department id", ex.getMessage());
    }

    @Test
    void deleteDepartmentByIdFailsDueForReadOnlyDepartment() {
        Department d1 = new Department();
        d1.setName("Organisation");
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