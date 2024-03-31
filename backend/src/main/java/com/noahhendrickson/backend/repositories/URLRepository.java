package com.noahhendrickson.backend.repositories;

import com.noahhendrickson.backend.models.URL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface URLRepository extends JpaRepository<URL, Long> {
    URL findByShortCode(String shortCode);
}
