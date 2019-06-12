package com.githinit.springboot.demo.controller;


import com.githinit.springboot.demo.entity.Employee;
import com.githinit.springboot.demo.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private EmployeeService employeeService;

    // inject employee dao

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    // expose "/employees" and return list of employees

    @GetMapping("/employees")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }
}
