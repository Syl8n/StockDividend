package com.zerobase.stockdividend.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController {

    @GetMapping("/company/autucomplete")
    public ResponseEntity<?> autocomplete(@RequestParam String keyword) {
        return null;
    }

    @GetMapping("/company")
    public ResponseEntity<?> searchCompany() {
        return null;
    }

    @PostMapping("/company")
    public ResponseEntity<?> addCompany() {
        return null;
    }

    @DeleteMapping("/company")
    public ResponseEntity<?> deleteCompany() {
        return null;
    }
}
