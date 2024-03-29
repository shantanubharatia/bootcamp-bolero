package com.bootcamp.Bolerobootcamp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
public class Department {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_DEPARTMENT")
    @SequenceGenerator(name = "SEQUENCE_DEPARTMENT", sequenceName = "SEQUENCE_DEPARTMENT", allocationSize = 1)
    private int id;

    @Column
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Column
    private boolean mandatory = false;

    @Column
    private boolean readOnly = false;

    @ManyToMany(mappedBy = "departments")
    @JsonIgnoreProperties(value = "departments")
    private Set<Employee> employees = new HashSet<>();

    public void addEmployee(Employee employee)
    {
        this.employees.add(employee);
    }
}
