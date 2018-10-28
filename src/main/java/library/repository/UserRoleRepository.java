package library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import library.model.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>{

	UserRole findByRoleName(String roleName);
}
