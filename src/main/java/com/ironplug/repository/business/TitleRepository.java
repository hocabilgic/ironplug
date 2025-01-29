package com.ironplug.repository.business;



import com.ironplug.entity.business.Title;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface TitleRepository extends JpaRepository<Title, Long> {






    @Query("SELECT t FROM Title t WHERE t.user.id = :id")
    List<Title> findAllUserTitle(@Param("id") Long id);
}
