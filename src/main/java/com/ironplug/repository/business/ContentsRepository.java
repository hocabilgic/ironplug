package com.ironplug.repository.business;

import com.ironplug.entity.business.Contents;
import com.ironplug.entity.business.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentsRepository extends JpaRepository<Contents , Long> {

    @Query("SELECT c FROM Contents c WHERE c.title.id = :titleId")
    List<Contents> findByTitleId(@Param("titleId") Long titleId);

}
