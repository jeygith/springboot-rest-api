package com.githinit.springboot.demo.dao;

import com.githinit.springboot.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
