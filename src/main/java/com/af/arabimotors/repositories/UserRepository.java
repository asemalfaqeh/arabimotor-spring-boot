package com.af.arabimotors.repositories;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.af.arabimotors.entities.UserEntity;
import com.af.arabimotors.model.request.UserChangePasswordRequest;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	UserEntity findByEmail(final String email);
	/*
	@Transactional
	@Modifying
	@Query("UPDATE Users u set u.password = :user.new_password WHERE id = :user.id")
	void changeUserPassword(final @PathVariable(name = "user") UserChangePasswordRequest user);
	*/

}
