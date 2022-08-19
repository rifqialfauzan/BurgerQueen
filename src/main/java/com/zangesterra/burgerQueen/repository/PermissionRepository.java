package com.zangesterra.burgerQueen.repository;

import com.zangesterra.burgerQueen.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {
    Permission findByName(String name);
}
