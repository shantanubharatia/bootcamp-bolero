package com.bootcamp.Bolerobootcamp.repositories;

import com.bootcamp.Bolerobootcamp.models.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IDepartmentRepository extends CrudRepository<Department, Integer> {
    List<Department> findByMandatory(Boolean mandatory);
}
