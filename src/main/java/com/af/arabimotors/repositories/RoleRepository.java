package com.af.arabimotors.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.af.arabimotors.entities.RoleEntity;


@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

   RoleEntity findByRole(final String role);

}
