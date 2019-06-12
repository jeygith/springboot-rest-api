package com.githinit.springboot.demo.dao;

import com.githinit.springboot.demo.entity.Employee;
import com.githinit.springboot.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String s);
}
