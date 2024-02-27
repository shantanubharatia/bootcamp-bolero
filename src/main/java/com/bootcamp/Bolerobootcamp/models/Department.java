package com.bootcamp.Bolerobootcamp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Department {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_sequence")
    @SequenceGenerator(name = "department_sequence", sequenceName = "department_sequence", allocationSize = 1)
    private int id;

    @Column
    private String name;

    @Column
    private boolean mandatory = false;

    @Column
    private boolean readOnly = false;

    @ManyToMany(mappedBy = "departments", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnoreProperties(value = "departments")
    private Set<Employee> employees = new HashSet<>();

    public void addEmployee(Employee employee)
    {
        this.employees.add(employee);
    }
}
