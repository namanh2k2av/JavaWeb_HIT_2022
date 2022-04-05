package com.hit.btvn_buoi6.controllers;

import com.github.slugify.Slugify;
import com.hit.btvn_buoi6.base.RestApiV1;
import com.hit.btvn_buoi6.base.UrlConstant;
import com.hit.btvn_buoi6.dto.DistrictsDTO;
import com.hit.btvn_buoi6.dto.ProvincesDTO;
import com.hit.btvn_buoi6.exception.DuplicateException;
import com.hit.btvn_buoi6.exception.NotFoundException;
import com.hit.btvn_buoi6.models.Districts;
import com.hit.btvn_buoi6.models.Provinces;
import com.hit.btvn_buoi6.repositories.DistrictRepository;
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
public class DistrictsController {
    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Slugify slugify;

    //Lấy tất cả
    @GetMapping
    public ResponseEntity<?> getAllDistricts() {
        List<Districts> districts = districtRepository.findAll();
        return ResponseEntity.status(200).body(districts);
    }

    //Thêm district
    @PostMapping(UrlConstant.District.DISTRICT)
    public ResponseEntity<?> createNewDistrict(@RequestBody DistrictsDTO districtsDTO) {
        if (checkDistrictExists(districtsDTO.getCode())) {
            throw new DuplicateException("Duplicate district by code: " + districtsDTO.getCode());
        }
        Provinces provinces = getProvinceCode(districtsDTO.getParentCode());

        Districts districts = modelMapper.map(districtsDTO, Districts.class);
        districts.setSlug(slugify.slugify(districts.getName()));
        districts.setNameWithType(districts.getType() + " " + districts.getName());
        districts.setPathWithType(districts.getNameWithType() + ", " + provinces.getNameWithType());

        return ResponseEntity.status(201).body(districtRepository.save(districts));
    }

    //Lấy district theo code
    @GetMapping(UrlConstant.District.DISTRICT_WITH_CODE)
    public ResponseEntity<?> getDistrictByCode(@PathVariable("code") Long code) {
        return ResponseEntity.status(200).body(getDistrictCode(code));
    }

    //Sửa district
    @PatchMapping(UrlConstant.District.DISTRICT_WITH_CODE)
    public ResponseEntity<?> editDistrictByCode(@PathVariable("code") Long code,
                                                @RequestBody DistrictsDTO districtsDTO) {
        Districts districts = getDistrictCode(code);
        Provinces provinces = getProvinceCode(districts.getParentCode());
        districts.setName(districtsDTO.getName());
        districts.setSlug(slugify.slugify(districts.getName()));
        districts.setNameWithType(districts.getType() + " " + districts.getName());
        districts.setPathWithType(districts.getNameWithType() + ", " + provinces.getNameWithType());
        districts.setParentCode(districtsDTO.getParentCode());

        return ResponseEntity.status(201).body(districtRepository.save(districts));
    }

    //Xóa district
    @DeleteMapping(UrlConstant.District.DISTRICT_WITH_CODE)
    public ResponseEntity<?> deleteDistrictByCode(@PathVariable("Code") Long code) {
        Districts districts = getDistrictCode(code);
        districtRepository.delete(districts);
        return ResponseEntity.status(200).body(districts);
    }

    //Thêm danh sách district
    @PostMapping(UrlConstant.District.COLLECTION)
    @Transactional
    public ResponseEntity<?> addCollection(@RequestBody List<DistrictsDTO> districtsDTOS) {
        List<Districts> districtsList = new ArrayList<>();
        districtsDTOS.forEach(item -> {
            if (checkDistrictExists(item.getCode())) {
                throw new DuplicateException("Duplicate district with code: " + item.getCode());
            }
            Provinces provinces = getProvinceCode(item.getParentCode());

            Districts districts = modelMapper.map(item, Districts.class);
            districts.setSlug(slugify.slugify(districts.getName()));
            districts.setNameWithType(districts.getType() + " " + districts.getName());
            districts.setPathWithType(districts.getNameWithType() + ", " + provinces.getNameWithType());

            districtsList.add(districtRepository.save(districts));
        });
        return ResponseEntity.status(201).body(districtsList);
    }

    private Districts getDistrictCode(Long code) {
        Optional<Districts> optional = districtRepository.findById(code);
        if (optional.isEmpty()) {
            throw new NotFoundException("Can not find district by code: " + code);
        }
        return optional.get();
    }

    private Boolean checkDistrictExists(Long code) {
        Optional<Districts> optional = districtRepository.findById(code);
        return optional.isPresent();
    }

    private Provinces getProvinceCode(Long code) {
        Optional<Provinces> optional = provinceRepository.findById(code);
        if (optional.isEmpty()) {
            throw new NotFoundException("Can not find province by code: " + code);
        }
        return optional.get();
    }
}
