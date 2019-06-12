package com.githinit.springboot.demo.controller;


import com.githinit.springboot.demo.entity.Employee;
import com.githinit.springboot.demo.exception.EmployeeNotFoundException;
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
            throw new EmployeeNotFoundException("Employee not found - " + id);
        }

        return employee;
    }

    // post to add new employee
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {

        employee.setId(0);

        employeeService.save(employee);

        return employee;

    }

    // put to update existing employee
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee) {


        employeeService.save(employee);

        return employee;
    }

    // delete employee

    @DeleteMapping("/employees/{id}")
    public String deleteEmployee(@PathVariable int id) {

        Employee employee = employeeService.findById(id);

        if (employee == null) {
            throw new RuntimeException("Employee id not found - " + id);
        }

        employeeService.deleteById(id);

        return "Deleted employee id - " + id;

    }


}
