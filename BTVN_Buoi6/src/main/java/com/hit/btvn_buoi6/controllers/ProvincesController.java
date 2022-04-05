package com.hit.btvn_buoi6.controllers;

import com.github.slugify.Slugify;
import com.hit.btvn_buoi6.base.RestApiV1;
import com.hit.btvn_buoi6.base.UrlConstant;
import com.hit.btvn_buoi6.dto.ProvincesDTO;
import com.hit.btvn_buoi6.exception.DuplicateException;
import com.hit.btvn_buoi6.exception.NotFoundException;
import com.hit.btvn_buoi6.models.Provinces;
import com.hit.btvn_buoi6.repositories.ProvinceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestApiV1
public class ProvincesController {
    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Slugify slugify;

    //Lấy tất cả
    @GetMapping(UrlConstant.Province.PROVINCE)
    public ResponseEntity<?> getAllProvinces() {
        return ResponseEntity.status(200).body(provinceRepository.findAll());
    }

    //Thêm thông tin province
    @PostMapping(UrlConstant.Province.PROVINCE)
    public ResponseEntity<?> createNewProvince(@RequestBody ProvincesDTO provincesDTO) {
        if (checkProvinceExists(provincesDTO.getCode())) {
            throw new DuplicateException("Duplicate province with code: " + provincesDTO.getCode());
        }
        Provinces provinces = modelMapper.map(provincesDTO, Provinces.class);
        provinces.setSlug(slugify.slugify(provinces.getName()));
        provinces.setNameWithType(provinces.getType() + " " + provinces.getName());
        return ResponseEntity.status(201).body(provinceRepository.save(provinces));
    }

    //Lấy province theo code
    @GetMapping(UrlConstant.Province.PROVINCE_WITH_CODE)
    public ResponseEntity<?> getProvinceByCode(@PathVariable("code") Long code) {
        return ResponseEntity.status(200).body(getProvinceCode(code));
    }

    //Sửa province
    @PatchMapping(UrlConstant.Province.PROVINCE_WITH_CODE)
    public ResponseEntity<?> editProvinceByCode(@PathVariable("code") Long code,
                                                @RequestBody ProvincesDTO provincesDTO) {
        Provinces provinces = getProvinceCode(provincesDTO.getCode());

        provinces.setName(provincesDTO.getName());
        provinces.setType(provincesDTO.getType());

        provinces.setSlug(slugify.slugify(provinces.getName()));
        provinces.setNameWithType(provinces.getType() + " " + provinces.getName());
        return ResponseEntity.status(201).body(provinceRepository.save(provinces));
    }

    //Xóa province
    @DeleteMapping(UrlConstant.Province.PROVINCE_WITH_CODE)
    public ResponseEntity<?> deleteProvinceByCode(@PathVariable("code") Long code) {
        Provinces provinces = getProvinceCode(code);
        provinceRepository.delete(provinces);
        return ResponseEntity.status(200).body(provinces);
    }

    //Lấy district
    @GetMapping(UrlConstant.Province.DISTRICT_WITH_CODE_PROVINCE)
    public ResponseEntity<?> getDistrictWithProvince(@PathVariable("code") Long code) {
        return ResponseEntity.status(200).body(getProvinceCode(code).getDistricts());
    }

    //Thêm danh sách province
    @PostMapping(UrlConstant.Province.COLLECTION)
    @Transactional
    public ResponseEntity<?> addCollection(@RequestBody List<ProvincesDTO> provincesDTOS) {
        List<Provinces> provincesList = new ArrayList<>();
        provincesDTOS.forEach(item -> {
            if (checkProvinceExists(item.getCode())) {
                throw new DuplicateException("Duplicate province with code: " + item.getCode());
            }
            Provinces provinces = modelMapper.map(item, Provinces.class);
            provinces.setSlug(slugify.slugify(provinces.getName()));
            provinces.setNameWithType(provinces.getType() + " " + provinces.getName());

            Provinces provinces1 = provinceRepository.save(provinces);
            provincesList.add(provinces1);
        });
        return ResponseEntity.status(201).body(provincesList);
    }

    private Provinces getProvinceCode(Long code) {
        Optional<Provinces> optional = provinceRepository.findById(code);
        if (optional.isEmpty()) {
            throw new NotFoundException("Can not find province by code: " + code);
        }
        return optional.get();
    }

    private Boolean checkProvinceExists(Long code) {
        Optional<Provinces> optional = provinceRepository.findById(code);
        return optional.isPresent();
    }
}
