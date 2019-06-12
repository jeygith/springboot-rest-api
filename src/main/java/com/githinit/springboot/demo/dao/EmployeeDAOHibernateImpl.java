package com.githinit.springboot.demo.dao;

import com.githinit.springboot.demo.entity.Employee;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


@Repository
public class EmployeeDAOHibernateImpl implements EmployeeDAO {

    // define entitymanager

    private EntityManager entityManager;

    // setup constructor injection

    @Autowired
    public EmployeeDAOHibernateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> findAll() {
        // get current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        // create a query

        Query<Employee> query = currentSession.createQuery("from Employee", Employee.class);


        // execute query to get employees

        List<Employee> employees = query.getResultList();
        // return results

        return employees;
    }

    @Override
    public Employee findById(int id) {
        // get current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        // get employee
        Employee employee = currentSession.get(Employee.class, id);

        // return employee
        return employee;
    }

    @Override
    public void save(Employee employee) {
        // get current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        // save employee
        currentSession.saveOrUpdate(employee);

    }

    @Override
    public void deleteById(int id) {

        // get current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        // get employee
        Query query = currentSession.createQuery("delete from Employee where id=: id");
        query.setParameter("id", id);

        query.executeUpdate();

    }
}
