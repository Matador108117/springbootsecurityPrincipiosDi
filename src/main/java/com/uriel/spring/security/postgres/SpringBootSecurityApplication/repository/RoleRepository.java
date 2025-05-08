package com.uriel.spring.security.postgres.SpringBootSecurityApplication.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uriel.spring.security.postgres.SpringBootSecurityApplication.models.ERole;
import com.uriel.spring.security.postgres.SpringBootSecurityApplication.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
