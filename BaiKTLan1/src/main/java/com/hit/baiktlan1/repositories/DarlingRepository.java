package com.hit.baiktlan1.repositories;

import com.hit.baiktlan1.model.Darling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DarlingRepository extends JpaRepository<Darling, Long> {
    List<Darling> findByStatusIsNot(Long status);
    List<Darling> findDarlingByStatus(Long status);
}
