package com.pinguinera.provider.services;

import static org.mockito.Mockito.*;

import com.pinguinera.provider.models.factories.TextFactory;
import com.pinguinera.provider.repositories.BookRepository;
import com.pinguinera.provider.repositories.NovelRepository;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuoteServiceTest {

    private QuoteService quoteService;
    private BookRepository bookRepository;
    private NovelRepository novelRepository;
    private TextFactory textFactory;

    @BeforeEach
    public void setUp() {
        bookRepository = mock(BookRepository.class);
        novelRepository = mock(NovelRepository.class);
        textFactory = mock(TextFactory.class);
        quoteService = new QuoteService(bookRepository, novelRepository, textFactory);
    }

    @Test
    void createStock() {
    }

    @Test
    void saveText() {
    }

    @Test
    void calculateWholesaleQuote() {
    }

    @Test
    void calculateBudgetSaleQuote() {
    }

    @Test
    void createBooksEntitiesFromIndices() {
    }

    @Test
    void createNovelsEntitiesFromIndices() {
    }
}