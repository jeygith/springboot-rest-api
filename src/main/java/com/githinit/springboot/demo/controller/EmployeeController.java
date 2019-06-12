package com.githinit.springboot.demo.controller;


import com.githinit.springboot.demo.entity.Employee;
import com.githinit.springboot.demo.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

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

    //find one employee by id

    @GetMapping("/employees/{id}")
    public Employee find(@PathVariable int id) {
        Employee employee = employeeService.findById(id);

        if (employee == null) {
            throw new RuntimeException("Employee not found - " + id);
        }

        return employee;
    }

    // post to add new employee
    @PostMapping("/employees")
    @CrossOrigin
    public Employee addEmployee(@RequestBody Employee employee) {

        employee.setId(0);

        employeeService.save(employee);

        return employee;

    }


}
