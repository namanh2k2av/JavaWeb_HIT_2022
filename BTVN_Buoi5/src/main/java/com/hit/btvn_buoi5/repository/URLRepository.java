package com.hit.btvn_buoi5.repository;

import com.hit.btvn_buoi5.model.URL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface URLRepository extends JpaRepository<URL, Long> {
    URL findURLByLink(String link);
    URL findURLByLinkShort(String linkShort);
}
