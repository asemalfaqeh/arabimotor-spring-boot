package com.af.arabimotors.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.af.arabimotors.entities.BodyTypeEntity;
import com.af.arabimotors.entities.ConditionsEntity;

import java.util.List;


@Repository
@Transactional
public interface ConditionRepository extends JpaRepository<ConditionsEntity, Long>{
    
	@Modifying
	@Query(value = "update conditions e set e.condition_name = :name where e.id = :id", nativeQuery = true)
	public void updateCondition(@Param("name") String name, @Param("id") Long id);
	
	@Modifying
	@Query(value = "update conditions e set e.enabled = 0 where e.id = :id", nativeQuery = true)
	public void disableCondition(@Param("id") String id);
	
	public ConditionsEntity findById(String id);

	public List<ConditionsEntity> findAllByEnabled(boolean enabled);
	
	
	
}
