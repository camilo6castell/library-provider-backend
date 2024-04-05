package com.pinguinera.provider.repositories;

import com.pinguinera.provider.models.persistence.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
