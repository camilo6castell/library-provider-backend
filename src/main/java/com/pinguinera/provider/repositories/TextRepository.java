package com.pinguinera.provider.repositories;

import com.pinguinera.provider.models.persistence.TextEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextRepository extends JpaRepository<TextEntity, Long> {
}
