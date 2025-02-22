package com.ironplug.entity.business;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.ironplug.entity.enums.ImageType;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)

@Table(name = "t_image")
public class Image {
    //EmreAktas

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob //büyük boyutlu datalar için sütun oluşmasını sağlar
    @Column(nullable = false)
    @Basic(fetch = FetchType.LAZY) // Lazy loading
    private byte[] data;

    @Column(nullable = false)
    private String name;


    //@Column
    //private String type;
    //todo emun tipinde olsa daha mi iyi olur?
    @Enumerated(EnumType.STRING) // Enum olarak saklanacak
    @Column(nullable = false)
    private ImageType type;

    @Column(nullable = false)
    private Boolean featured;




}




