package com.hit.btvn_buoi6.repositories;

import com.hit.btvn_buoi6.models.Provinces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Provinces, Long> {

}
