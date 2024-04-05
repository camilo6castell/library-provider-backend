package com.pinguinera.provider.repositories;

import com.pinguinera.provider.models.persistence.NovelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NovelRepository extends JpaRepository<NovelEntity, Long> {
}
