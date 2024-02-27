package com.bootcamp.Bolerobootcamp.repositories;

import com.bootcamp.Bolerobootcamp.models.Employee;
import org.springframework.data.repository.CrudRepository;

public interface IEmployeeRepository extends CrudRepository<Employee, Integer> {
}
