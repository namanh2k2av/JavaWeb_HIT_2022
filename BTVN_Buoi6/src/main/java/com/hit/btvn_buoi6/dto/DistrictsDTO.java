package com.hit.btvn_buoi6.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DistrictsDTO {
    private String name;
    private String type;
    private String path;
    private Long code;
    private Long parentCode;
}
