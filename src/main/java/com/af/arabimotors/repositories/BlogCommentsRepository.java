package com.af.arabimotors.repositories;

import com.af.arabimotors.entities.BlogCommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogCommentsRepository extends JpaRepository<BlogCommentsEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM comments c WHERE blog_id =:id AND enabled=1 ORDER BY c.id DESC")
    List<BlogCommentsEntity> findAllCommentsByID(@Param("id") String id);
}
