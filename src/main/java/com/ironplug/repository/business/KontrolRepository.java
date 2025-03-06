package com.ironplug.repository.business;

import com.ironplug.entity.business.Image;
import com.ironplug.entity.business.Kontrol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface KontrolRepository extends JpaRepository<Kontrol, Long> {


    // 7 günden eski kontrolleri bulan sorgu
    List<Kontrol> findByCreateAtBefore(ZonedDateTime dateTime);

    // 7 günden eski kontrolleri silen metod
    void deleteByCreateAtBefore(ZonedDateTime dateTime);

    Optional<Kontrol> findByBaslikId(Long baslikId);


        List<Kontrol> findAllByBaslikId(Long baslikId);


}
