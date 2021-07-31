package com.af.arabimotors.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.af.arabimotors.entities.VehiclesEntity;

@Repository
public interface VehicleRepository extends JpaRepository<VehiclesEntity, Long>{

	@Query(nativeQuery = true, value="SELECT * FROM vehicles v ORDER BY v.id DESC LIMIT 4")
	List<VehiclesEntity> findFirstFourVehicles();
	
	Optional<VehiclesEntity> findById(Long id);
    List<VehiclesEntity> findAll();
    
    @Query(nativeQuery = true,value="SELECT * FROM vehicles v ORDER BY v.price ASC")
    List<VehiclesEntity> findAllOrderByPriceAsc();
    
    @Query(nativeQuery = true,value="SELECT * FROM vehicles v ORDER BY v.price DESC")
    List<VehiclesEntity> findAllOrderByPriceDesc();
    
    @Query(nativeQuery = true,value="SELECT * FROM vehicles v ORDER BY v.created_date DESC")
    List<VehiclesEntity> findAllOrderByCreatedAt();
    
    @Query(nativeQuery = true, value="SELECT * FROM vehicles WHERE is_featured = 1")
	List<VehiclesEntity> findAllFeaturedVehicles();
    
    @Query(nativeQuery = true, value="SELECT * FROM vehicles WHERE condition_type = :conditionType")
    List<VehiclesEntity> findAllByCondition(@Param("conditionType") String conditionType);
    
}
