package com.af.arabimotors.repositories;

import com.af.arabimotors.entities.BloggerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BloggerRepository extends JpaRepository<BloggerEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM blog b ORDER BY b.created_date DESC")
    List<BloggerEntity> findAll();

    @Query(nativeQuery = true, value = "SELECT * FROM blog b WHERE title LIKE %:search% ORDER BY b.created_date DESC")
    List<BloggerEntity> findAllBySearchName(@Param("search") String search);
    @Override
    Optional<BloggerEntity> findById(Long aLong);

    @Query(nativeQuery = true, value = "SELECT * FROM blog b ORDER BY b.created_date DESC LIMIT :limit")
    List<BloggerEntity> findAllByLimitRepo(@Param("limit") int limit);

}
