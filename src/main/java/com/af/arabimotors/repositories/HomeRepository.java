package com.af.arabimotors.repositories;

import com.af.arabimotors.entities.BannerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeRepository<T> extends JpaRepository<BannerEntity, Long> {
    
}
