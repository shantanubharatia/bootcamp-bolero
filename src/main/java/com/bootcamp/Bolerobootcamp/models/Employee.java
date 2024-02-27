package com.bootcamp.Bolerobootcamp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Validated
public class Employee {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_EMPLOYEE")
    @SequenceGenerator(name = "SEQUENCE_EMPLOYEE", sequenceName = "SEQUENCE_EMPLOYEE", allocationSize = 1)
    private int id;

    @NotNull(message = "Name cannot be empty")
    @Column()
    private String name;

    @ManyToMany()
    @JoinTable(
            name="map_employee_department",
            joinColumns = @JoinColumn(name = "id_employee"),
            inverseJoinColumns = @JoinColumn(name = "id_department")
    )
    @JsonIgnoreProperties(value = "employees")
    private Set<Department> departments = new HashSet<>();

    public void addDepartment(Department department)
    {
        this.departments.add(department);
    }
}
