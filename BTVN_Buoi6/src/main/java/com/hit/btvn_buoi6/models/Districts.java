package com.hit.btvn_buoi6.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Districts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long districtId;

    @Nationalized
    private String name;

    @Nationalized
    private String type;
    private String slug;

    @Nationalized
    private String nameWithType;

    @Nationalized
    private String path;

    @Nationalized
    private String pathWithType;

    @Column(unique = true)
    private Long code;
    private Long parentCode;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "provinceId")
    private Provinces provinces;
}
