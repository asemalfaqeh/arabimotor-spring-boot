package com.af.arabimotors.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import com.af.arabimotors.entities.VehiclesEntity;

import javax.transaction.Transactional;

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
    
    
    @Query(nativeQuery = true,value="SELECT * FROM vehicles WHERE price <= :priceMax && price >= :priceMin && model = :modelId && year = :yearId && condition_type = :conditionType")
    List<VehiclesEntity> findAdvancedSearchPrice(
    		@Param("priceMin") int priceMin,
    	    @Param("priceMax") int priceMax,
    		@Param("modelId") String modelId,
    		@Param("yearId") String yearId,
    		@Param("conditionType") String conditionType);
    
    @Query(nativeQuery = true,value="SELECT * FROM vehicles WHERE price <= :priceName && model = :modelId && year = :yearId && condition_type = :conditionType")
    List<VehiclesEntity> findAdvancedSearch(
    		@Param("priceName") int price,
    		@Param("modelId") String modelId,
    		@Param("yearId") String yearId,
    		@Param("conditionType") String conditionType);
    
    @Query(nativeQuery = true,value="SELECT * FROM vehicles WHERE gear_type = :gearType && body_type = :bodyType && fuel_type = :fuelType")
    List<VehiclesEntity> findAdvancedSearchFGB(
    		@Param("fuelType") String fuelType,
    		@Param("bodyType") String bodyType,
    		@Param("gearType") String gearType);
    
    @Query(nativeQuery= true, value="SELECT * FROM vehicles WHERE ad_title LIKE CONCAT('%',:ad_title,'%')")
    List<VehiclesEntity> findByAd_title(@Param("ad_title")String ad_title);
    
    @Query(nativeQuery=true,value="SELECT * FROM vehicles WHERE body_type=:bodyType")
    List<VehiclesEntity> findByBodyTypeEntity(String bodyType);

    @Query(nativeQuery = true, value = "SELECT * FROM vehicles WHERE user_id=:userId")
    List<VehiclesEntity> findAllByUserEntity(@Param("userId") String userId);

    void deleteById(Long aLong);
}
