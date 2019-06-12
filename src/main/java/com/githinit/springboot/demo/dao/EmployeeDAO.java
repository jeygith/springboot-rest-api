package com.githinit.springboot.demo.dao;

import com.githinit.springboot.demo.entity.Employee;

import java.util.List;

public interface EmployeeDAO {

    public List<Employee> findAll();
}
