package com.mazhar.blogs.app.repositories;

import com.mazhar.blogs.app.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepo extends JpaRepository<Roles,Integer> {
}
