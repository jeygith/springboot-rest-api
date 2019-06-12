package com.githinit.springboot.demo.dao;

import com.githinit.springboot.demo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@Primary
public class EmployeeDAOJpaImpl implements EmployeeDAO {

    private EntityManager entityManager;

    @Autowired
    public EmployeeDAOJpaImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> findAll() {
        // create query
        Query query = entityManager.createQuery(" from Employee");

        // execute query

        List<Employee> employees = query.getResultList();

        // return results
        return employees;
    }

    @Override
    public Employee findById(int id) {

        // get employee
        Employee employee = entityManager.find(Employee.class, id);

        // return employee
        return employee;

    }

    @Override
    public void save(Employee employee) {
        // save or update the employee
        Employee dbEmployee = entityManager.merge(employee);

        // update with id from db
        employee.setId(dbEmployee.getId());

    }

    @Override
    public void deleteById(int id) {

        // delete object with primary key

        Query query = entityManager.createQuery("delete from Employee where id=: id");

        query.setParameter("id", id);

        query.executeUpdate();
    }
}
