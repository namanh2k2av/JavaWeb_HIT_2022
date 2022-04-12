package com.hit.baiktlan1.controllers;

import com.hit.baiktlan1.dto.AddressDTO;
import com.hit.baiktlan1.exception.NotFoundException;
import com.hit.baiktlan1.model.Address;
import com.hit.baiktlan1.model.Darling;
import com.hit.baiktlan1.repositories.AddressRepository;
import com.hit.baiktlan1.repositories.DarlingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/addresses")
public class AddressController {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private DarlingRepository darlingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> getAllAddress() {
        return ResponseEntity.status(200).body(addressRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAddressById(@PathVariable("id") Long id) {
        Address address = getAddressId(id);
        return ResponseEntity.status(200).body(address);
    }

    @PostMapping("/{darlingId}")
    public ResponseEntity<?> CreateAddress(@PathVariable("darlingId") Long id,
                                           @RequestBody AddressDTO addressDTO) {
        Darling darling = getDarlingId(id);
        Address address = modelMapper.map(addressDTO, Address.class);
        address.setName(address.getName());
        address.setCode(address.getCode());
        address.setDarling(darling);
        return ResponseEntity.status(201).body(addressRepository.save(address));
    }

    @PatchMapping("/{darlingId}/{id}")
    public ResponseEntity<?> editAddress(@PathVariable("darlingId") Long darlingId,
                                         @PathVariable("id") Long id,
                                         @RequestBody AddressDTO addressDTO) {
        Darling darling = getDarlingId(darlingId);
        Address address = getAddressId(id);
        address.setName(addressDTO.getName());
        address.setCode(addressDTO.getCode());
        return ResponseEntity.status(201).body(addressRepository.save(address));
    }

    @DeleteMapping("/{darlingId}/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable("darlingId") Long darlingId,
                                           @PathVariable("id") Long id,
                                           @RequestBody AddressDTO addressDTO) {
        Darling darling = getDarlingId(darlingId);
        Address address = getAddressId(id);
        addressRepository.deleteById(id);
        return ResponseEntity.status(201).body("Xóa thành công");
    }

    private Darling getDarlingId(Long id) {
        Optional<Darling> optional = darlingRepository.findById(id);
        if (optional.isEmpty()) {
            throw new NotFoundException("Không tìm thấy người yêu có id: " + id);
        }
        return optional.get();
    }

    private Address getAddressId(Long id) {
        Optional<Address> optional = addressRepository.findById(id);
        if (optional.isEmpty()) {
            throw new NotFoundException("Không tìm thấy địa chỉ có id: " + id);
        }
        return optional.get();
    }
}
