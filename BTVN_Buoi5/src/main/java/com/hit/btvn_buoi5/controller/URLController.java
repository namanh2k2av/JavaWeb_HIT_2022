package com.hit.btvn_buoi5.controller;

import com.hit.btvn_buoi5.exception.DuplicateException;
import com.hit.btvn_buoi5.model.URL;
import com.hit.btvn_buoi5.repository.URLRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@RestController
public class URLController {

    @Autowired
    private URLRepository urlRepository;

    @GetMapping
    public ResponseEntity<?> getAllURL() {
        return ResponseEntity.status(200).body(urlRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createURL(@RequestParam("link") String link) {
        URL urlNew = new URL();

        URL urlOld = urlRepository.findURLByLink(link);

        if (urlOld != null) {
            throw new DuplicateException("Đã có url!");
        }

        urlNew.setLink(link);
        urlNew.setLinkShort(RandomStringUtils.randomAlphanumeric(6));
        return ResponseEntity.status(200).body(urlRepository.save(urlNew));
    }

    @GetMapping("/{linkShort}")
    public RedirectView getURL(@PathVariable("linkShort") String linkShort) {
        return new RedirectView(urlRepository.findURLByLinkShort(linkShort).getLink());
    }
}
