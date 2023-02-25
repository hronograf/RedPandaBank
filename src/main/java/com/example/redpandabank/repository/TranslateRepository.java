package com.example.redpandabank.repository;

import com.example.redpandabank.model.Translate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TranslateRepository extends JpaRepository<Translate, Long> {
    Translate getBySlug(String slug);
}
