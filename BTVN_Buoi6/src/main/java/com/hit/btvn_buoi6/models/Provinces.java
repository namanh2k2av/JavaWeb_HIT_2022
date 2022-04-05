package com.hit.btvn_buoi6.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Provinces {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long provinceId;

    @Nationalized
    private String name;

    private String slug;

    @Nationalized
    private String type;

    @Nationalized
    private String nameWithType;

    @Column(unique = true)
    private Long code;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "provinces")
    @JsonIgnore
    private List<Districts> districts;
}
