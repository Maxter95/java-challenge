package com.challenge.services.dao;

import com.challenge.services.entity.Role;
import com.challenge.services.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
	Optional<Role> findByName(RoleName roleName);
}
