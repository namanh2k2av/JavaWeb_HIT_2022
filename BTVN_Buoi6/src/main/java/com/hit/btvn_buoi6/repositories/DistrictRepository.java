package com.hit.btvn_buoi6.repositories;

import com.hit.btvn_buoi6.models.Districts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<Districts, Long> {
}
