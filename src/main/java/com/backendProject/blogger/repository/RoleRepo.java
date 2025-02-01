package com.backendProject.blogger.repository;

import com.backendProject.blogger.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {
}
