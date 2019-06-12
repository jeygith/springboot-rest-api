package com.githinit.springboot.demo.dao;

import com.githinit.springboot.demo.entity.Employee;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
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
    @Transactional
    public List<Employee> findAll() {
        // get current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        // create a query

        Query<Employee> query = currentSession.createQuery("from Employee", Employee.class);


        // execute query to get employees

        List<Employee> employees = query.getResultList();
        // return results

        return employees ;
    }
}
