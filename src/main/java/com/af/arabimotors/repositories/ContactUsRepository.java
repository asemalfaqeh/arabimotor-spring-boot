package com.af.arabimotors.repositories;

import com.af.arabimotors.entities.ContactUsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactUsRepository extends JpaRepository<ContactUsEntity, Long> {

}
