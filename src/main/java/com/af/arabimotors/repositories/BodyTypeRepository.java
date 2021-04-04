package com.af.arabimotors.repositories;

import com.af.arabimotors.entities.BodyTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyTypeRepository extends JpaRepository<BodyTypeEntity, Long> {

}
