package com.af.arabimotors.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import com.af.arabimotors.entities.VehiclesEntity;

import javax.transaction.Transactional;

@Repository
public interface VehicleRepository extends PagingAndSortingRepository<VehiclesEntity, Long> {

	@Query(nativeQuery = true, value="SELECT * FROM vehicles v ORDER BY v.id DESC LIMIT 4")
	List<VehiclesEntity> findFirstFourVehicles();
	
	Optional<VehiclesEntity> findById(Long id);

	@Query(nativeQuery = true,value = "SELECT * FROM vehicles v WHERE v.is_deleted = 0")
    List<VehiclesEntity> findAll();
    
    @Query(nativeQuery = true,value="SELECT * FROM vehicles v WHERE v.is_deleted = 0 ORDER BY v.price ASC")
    List<VehiclesEntity> findAllOrderByPriceAsc();
    
    @Query(nativeQuery = true,value="SELECT * FROM vehicles v WHERE v.is_deleted = 0 ORDER BY v.price DESC")
    List<VehiclesEntity> findAllOrderByPriceDesc();
    
    @Query(nativeQuery = true,value="SELECT * FROM vehicles v WHERE v.is_deleted = 0 ORDER BY v.created_date DESC")
    List<VehiclesEntity> findAllOrderByCreatedAt();
    
    @Query(nativeQuery = true, value="SELECT * FROM vehicles WHERE is_deleted = 0 && is_featured = 1")
	List<VehiclesEntity> findAllFeaturedVehicles();
    
    @Query(nativeQuery = true, value="SELECT * FROM vehicles WHERE is_deleted = 0 && condition_type = :conditionType")
    List<VehiclesEntity> findAllByCondition(@Param("conditionType") String conditionType);

    @Query(nativeQuery = true, value="SELECT * FROM vehicles WHERE is_deleted = 0 && user_id = :userId")
    List<VehiclesEntity> findAllByUserId(@Param("userId") String conditionType);

    @Query(nativeQuery = true,value="SELECT * FROM vehicles WHERE is_deleted = 0 && price <= :priceMax && price >= :priceMin && model = :modelId && year = :yearId && condition_type = :conditionType")
    List<VehiclesEntity> findAdvancedSearchPrice(
    		@Param("priceMin") int priceMin,
    	    @Param("priceMax") int priceMax,
    		@Param("modelId") String modelId,
    		@Param("yearId") String yearId,
    		@Param("conditionType") String conditionType);
    
    @Query(nativeQuery = true,value="SELECT * FROM vehicles WHERE  is_deleted = 0 && price <= :priceName && model = :modelId && year = :yearId && condition_type = :conditionType")
    List<VehiclesEntity> findAdvancedSearch(
    		@Param("priceName") int price,
    		@Param("modelId") String modelId,
    		@Param("yearId") String yearId,
    		@Param("conditionType") String conditionType);
    
    @Query(nativeQuery = true,value="SELECT * FROM vehicles WHERE  is_deleted = 0 && gear_type = :gearType && body_type = :bodyType && fuel_type = :fuelType")
    List<VehiclesEntity> findAdvancedSearchFGB(
    		@Param("fuelType") String fuelType,
    		@Param("bodyType") String bodyType,
    		@Param("gearType") String gearType);
    
    @Query(nativeQuery= true, value="SELECT * FROM vehicles WHERE  is_deleted = 0 && ad_title LIKE CONCAT('%',:ad_title,'%')")
    List<VehiclesEntity> findByAd_title(@Param("ad_title")String ad_title);
    
    @Query(nativeQuery=true,value="SELECT * FROM vehicles WHERE  is_deleted = 0 && body_type=:bodyType")
    List<VehiclesEntity> findByBodyTypeEntity(String bodyType);

    @Query(nativeQuery = true, value = "SELECT * FROM vehicles  WHERE is_deleted = 0 && user_id=:userId")
    List<VehiclesEntity> findAllByUserEntity(@Param("userId") String userId);

    void deleteById(Long aLong);
}
