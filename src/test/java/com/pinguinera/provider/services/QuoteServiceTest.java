package com.pinguinera.provider.services;

import static org.mockito.Mockito.*;

import com.pinguinera.provider.models.DTOS.BudgetSaleDTO;
import com.pinguinera.provider.models.DTOS.CreateStockDTO;
import com.pinguinera.provider.models.DTOS.SaveAndQuoteTextDTO;
import com.pinguinera.provider.models.DTOS.WholeSaleDTO;
import com.pinguinera.provider.models.enums.TextType;
import com.pinguinera.provider.models.factories.TextFactory;
import com.pinguinera.provider.models.objects.quote.BudgetSaleQuoteObject;
import com.pinguinera.provider.models.objects.quote.WholesaleQuoteObject;
import com.pinguinera.provider.models.objects.response.ResponseObject;
import com.pinguinera.provider.repositories.TextRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class QuoteServiceTest {

//    private QuoteService quoteService;
//    private TextRepository textRepository;
//    private NovelRepository novelRepository;
//    private TextFactory textFactory;
//
//    @BeforeEach
//    public void setUp() {
//        textRepository = mock(TextRepository.class);
//        novelRepository = mock(NovelRepository.class);
//        textFactory = mock(TextFactory.class);
//        quoteService = new QuoteService(textRepository, novelRepository, textFactory);
//    }
//
//    @Test
//    void createStock() {
//        CreateStockDTO payload = new CreateStockDTO(true);
//        ResponseObject result = quoteService.createStock(payload);
//
//        ResponseObject resultTest =  new ResponseObject("Textos agregados correctamente a la base de datos ;)");
//
//        assertNotNull(result);
//        Assertions.assertEquals(result.getResponse(), resultTest.getResponse());
//    }
//
//    @Test
//    void createStock2() {
//        CreateStockDTO payload = new CreateStockDTO(false);
//        ResponseObject result = quoteService.createStock(payload);
//
//        ResponseObject resultTest =  new ResponseObject("No se han hecho cambios a la base de datos. ;)");
//
//        assertNotNull(result);
//        Assertions.assertEquals(result.getResponse(), resultTest.getResponse());
//    }
//
//
//    @Test
//    void saveText() {
//        SaveAndQuoteTextDTO payload = new SaveAndQuoteTextDTO("El origen de las especies", TextType.BOOK, 100);
//        ResponseObject result = quoteService.saveAndQuoteText(payload);
//
//        verify(textRepository).save(any(BookEntity.class));
//        assertNotNull(result);
//        assertEquals("El título " + payload.getTitle() + " con el precio base de " + payload.getBasePrice() + "ha sido guardado con éxito.", result.getResponse());
//    }
//
//    @Test
//    void calculateWholesaleQuote() {
//        List<Long> bookIndices = Arrays.asList(1L, 2L);
//        List<Long> novelIndices = Arrays.asList(1L, 2L);
//        WholeSaleDTO payload = new WholeSaleDTO(bookIndices, novelIndices, LocalDate.now());
//
//        when(textRepository.findById(any(Long.class))).thenReturn(Optional.of(new BookEntity("BookTest1", 100)));
//        when(textRepository.findById(any(Long.class))).thenReturn(Optional.of(new BookEntity("BookTest2", 200)));
//        when(textRepository.findById(any(Long.class))).thenReturn(Optional.of(new BookEntity("BookTest3", 300)));
//        when(novelRepository.findById(any(Long.class))).thenReturn(Optional.of(new NovelEntity("NovelTest1", 100)));
//        when(novelRepository.findById(any(Long.class))).thenReturn(Optional.of(new NovelEntity("NovelTest2", 200)));
//        when(novelRepository.findById(any(Long.class))).thenReturn(Optional.of(new NovelEntity("NovelTest3", 300)));
//
//        WholesaleQuoteObject result = quoteService.calculateWholesaleQuote(payload);
//
//
//        assertNotNull(result);
//    }
//
//    @Test
//    void calculateBudgetSaleQuote() {
//
//        List<Long> textIndices = Arrays.asList(1L, 2L, 3L);
//        float budget = 1000;
//        BudgetSaleDTO payload = new BudgetSaleDTO(textIndices, budget);
//
//        when(textRepository.findById(any(Long.class))).thenReturn(Optional.of(new BookEntity("BookTest1", 100)));
//        when(novelRepository.findById(any(Long.class))).thenReturn(Optional.of(new NovelEntity("NoveTest1", 200)));
//
//        BudgetSaleQuoteObject result = quoteService.calculateBudgetSaleQuote(payload);
//
//        assertNotNull(result);
//    }
//
//    @Test
//    void createBooksEntitiesFromIndices() {
//        List<Long> indices = Arrays.asList(1L, 2L, 3L); // Índices de libros simulados
//        List<BookEntity> expectedEntities = Arrays.asList(
//                new BookEntity("BookTest1", 100),
//                new BookEntity("BookTest2", 200),
//                new BookEntity("BookTest3", 300)
//        );
//
//        when(textRepository.findById(1L)).thenReturn(Optional.of(expectedEntities.get(0)));
//        when(textRepository.findById(2L)).thenReturn(Optional.of(expectedEntities.get(1)));
//        when(textRepository.findById(3L)).thenReturn(Optional.of(expectedEntities.get(2)));
//
//        List<BookEntity> actualEntities = quoteService.createBooksEntitiesFromIndices(indices);
//
//        assertNotNull(actualEntities);
//        assertEquals(expectedEntities.size(), actualEntities.size());
//        assertEquals(expectedEntities, actualEntities);
//    }
//
//    @Test
//    void createNovelsEntitiesFromIndices() {
//        QuoteService quoteService = new QuoteService(Mockito.mock(TextRepository.class), Mockito.mock(NovelRepository.class), Mockito.mock(TextFactory.class));
//        List<Long> indices = new ArrayList<>();
//        indices.add(1L);
//
//        NovelEntity novelEntity1 = new NovelEntity("La rebelión en la granja", 1300);
//        NovelEntity novelEntity2 = new NovelEntity("1984", 3030);
//        Mockito.when(quoteService.novelRepository.findById(1L)).thenReturn(Optional.of(novelEntity1));
//        Mockito.when(quoteService.novelRepository.findById(2L)).thenReturn(Optional.of(novelEntity2));
//
//        List<NovelEntity> novelsEntities = quoteService.createNovelsEntitiesFromIndices(indices);
//
//        assertEquals(1, novelsEntities.size());
//        assertEquals(novelEntity1, novelsEntities.get(0));
//    }
}