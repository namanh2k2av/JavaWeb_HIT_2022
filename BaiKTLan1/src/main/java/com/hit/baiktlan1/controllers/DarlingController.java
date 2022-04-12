package com.hit.baiktlan1.controllers;

import com.hit.baiktlan1.dto.DarlingDTO;
import com.hit.baiktlan1.exception.NotFoundException;
import com.hit.baiktlan1.model.Address;
import com.hit.baiktlan1.model.Darling;
import com.hit.baiktlan1.repositories.DarlingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/darlings")
public class DarlingController {
    @Autowired
    private DarlingRepository darlingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> getAllDarlings() {
        return ResponseEntity.status(200).body(darlingRepository.findAll());
    }

    @GetMapping("/current")
    public ResponseEntity<?> getDarlingCurrent() {
        return ResponseEntity.status(200).body(darlingRepository.findByStatusIsNot(3L));
    }

    @GetMapping("status")
    public ResponseEntity<?> getStatusDarling(@RequestParam(name = "status", required = false) Long status) {
        return ResponseEntity.status(200).body(darlingRepository.findDarlingByStatus(status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDarlingById(@PathVariable("id") Long id) {
        Darling darling = getDarlingId(id);
        return ResponseEntity.status(200).body(darling);
    }

    @PostMapping
    public ResponseEntity<?> createNewDarling(@RequestBody DarlingDTO darlingDTO) {
        Darling darling = modelMapper.map(darlingDTO, Darling.class);
        darling.setName(darlingDTO.getName());
        darling.setPhone(darlingDTO.getPhone());
        darling.setEmail(darlingDTO.getEmail());
        darling.setFavorite(darlingDTO.getFavorite());
        darling.setStatus(darlingDTO.getStatus());
        return ResponseEntity.status(201).body(darlingRepository.save(darling));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> editDarlingById(@PathVariable("id") Long id,
                                               @RequestBody DarlingDTO darlingDTO) {
        Darling darling = getDarlingId(id);
        darling.setName(darlingDTO.getName());
        darling.setPhone(darlingDTO.getPhone());
        darling.setEmail(darlingDTO.getEmail());
        darling.setFavorite(darlingDTO.getFavorite());
        darling.setStatus(darlingDTO.getStatus());
        return ResponseEntity.status(201).body(darlingRepository.save(darling));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDarlingById(@PathVariable("id") Long id) {
        Darling darling = getDarlingId(id);
        if (darling.getStatus() != 3) {
            darling.setStatus(3L);
        }
        return ResponseEntity.status(201).body(darlingRepository.save(darling));
    }

    @GetMapping("/{id}/addresses")
    public ResponseEntity<?> getAddressDarling(@PathVariable("id") Long id) {
        List<Address> addresses = darlingRepository.findById(id).get().getAddresses();
        return ResponseEntity.status(200).body(addresses);
    }

    private Darling getDarlingId(Long id) {
        Optional<Darling> optional = darlingRepository.findById(id);
        if (optional.isEmpty()) {
            throw new NotFoundException("Không tìm thấy người yêu có id: " + id);
        }
        return optional.get();
    }
}
