package com.pinguinera.provider.repositories;

import com.pinguinera.provider.model.persistence.TextEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextRepository extends JpaRepository<TextEntity, Long> {
}
