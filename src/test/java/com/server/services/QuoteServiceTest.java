//package com.pinguinera.provider.services;
//
//import static org.mockito.Mockito.*;
//
//import com.pinguinera.provider.model.dto.quote.request.BudgetSaleRequest;
//import com.pinguinera.provider.model.dto.quote.request.SaveAndQuoteTextRequest;
//import com.pinguinera.provider.model.dto.quote.request.WholeSaleRequest;
//import com.pinguinera.provider.model.dto.quote.request.shared.ItemFromTextBatchRequest;
//import com.pinguinera.provider.model.dto.quote.request.shared.TextRequest;
//import com.pinguinera.provider.model.enums.TextType;
//import com.pinguinera.provider.model.factory.TextFactory;
//import com.pinguinera.provider.model.dto.quote.response.BudgetSaleQuoteResponse;
//import com.pinguinera.provider.model.dto.quote.response.shared.TextQuoteResponse;
//import com.pinguinera.provider.model.dto.quote.response.WholesaleQuoteResponse;
//import com.pinguinera.provider.model.object.text.BookObject;
//import com.pinguinera.provider.model.object.text.NovelObject;
//import com.pinguinera.provider.model.object.text.TextObject;
//import com.pinguinera.provider.model.persistence.TextEntity;
//import com.pinguinera.provider.repositories.TextRepository;
//import org.junit.jupiter.api.BeforeEach;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//
//import java.time.LocalDate;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class QuoteServiceTest {
//
//    @Mock
//    private TextRepository textRepository;
//
//    @Mock
//    private TextFactory textFactory;
//
//    @InjectMocks
//    private QuoteService service;
//
//    @BeforeEach
//    public void setUp() {
//        textRepository = mock(TextRepository.class);
//        textFactory = mock(TextFactory.class);
//        service = new QuoteService(textRepository, textFactory);
//    }
//
//    @Test
//    public void testSaveAndQuoteText() {
//        // Arrange
//        SaveAndQuoteTextRequest payload = new SaveAndQuoteTextRequest(new TextRequest("El llamado de la selva", TextType.BOOK, 200), LocalDate.of(2000, 3, 17));
//        TextObject outputFactory = new BookObject("El llamado de la selva", TextType.BOOK, 200, true);
//
//        when(textFactory.create(any(TextRequest.class), eq(true))).thenReturn(outputFactory);
//        ArgumentCaptor<TextEntity> captor = ArgumentCaptor.forClass(TextEntity.class);
//
//        // Act
//        TextQuoteResponse result = service.saveAndQuoteText(payload);
//
//        // Assert
//        verify(textRepository, times(1)).save(captor.capture());
//
//        assertEquals("El llamado de la selva", captor.getValue().getTitle());
//        assertEquals(TextType.BOOK, captor.getValue().getType());
//        assertEquals(200, captor.getValue().getBasePrice());
//
//        assertEquals("El llamado de la selva", result.getTitle());
//        assertEquals(TextType.BOOK, result.getType());
//        assertEquals(271.32F, result.getPrice());
//        assertEquals(1, result.getDiscounts().size()); // No discounts applied
//        assertEquals(225.1956F, result.getTotalPrice());
//    }
//
//    @Test
//    public void testCalculateWholesaleQuote() {
//        // Arrange
//
//        List<ItemFromTextBatchRequest> bookIndicesAnQuantity = new ArrayList<>();
//        List<ItemFromTextBatchRequest> novelIndicesAndQuantity = new ArrayList<>();
//        ItemFromTextBatchRequest testItemBookFromTextBatchDTO = new ItemFromTextBatchRequest(0, 1);
//        ItemFromTextBatchRequest testItemNovelFromTextBatchDTO = new ItemFromTextBatchRequest(0, 1);
//        bookIndicesAnQuantity.add(testItemBookFromTextBatchDTO);
//        novelIndicesAndQuantity.add(testItemNovelFromTextBatchDTO);
//
//        WholeSaleRequest payload = new WholeSaleRequest(bookIndicesAnQuantity, novelIndicesAndQuantity, LocalDate.of(2000, 3, 17));
//
//        TextObject bookOutputFactory = new BookObject("El llamado de la selva", TextType.BOOK, 200, true);
//        TextObject novelOutputFactory = new NovelObject("Cien años de soledad", TextType.BOOK, 550, true);
//
//                List<TextEntity> mockedListTextEntity = new ArrayList<>();
//        mockedListTextEntity.add(new TextEntity("El llamado de la selva", TextType.BOOK, 200));
//        mockedListTextEntity.add(new TextEntity("Cien años de soledad", TextType.NOVEL, 550));
//
//
//        when(textRepository.findAll()).thenReturn(mockedListTextEntity);
//        when(textFactory.create(any(TextRequest.class), eq(true))).thenReturn(bookOutputFactory).thenReturn(novelOutputFactory);
//
//        // Act
//        WholesaleQuoteResponse result = service.calculateWholesaleQuote(payload);
//
//        // Asserts
//        assertEquals(1, result.getBooksQuote().size());
//        assertEquals(1, result.getNovelsQuote().size());
//
//        verify(textRepository, times(2)).findAll();
//    }
//
//    @Test
//    public void testCalculateBudgetSaleQuote_WithBookAndNovel_ReturnsValidQuoteObject() {
//        // Setup
//        BudgetSaleRequest payload = new BudgetSaleRequest(Arrays.asList(1, 9), 6000, LocalDate.of(2000, 3, 17));
//
//        // Mocking required methods in textRepository
//        List<TextEntity> mockedTextEntities = createMockTextEntities();
//        when(textRepository.findAll()).thenReturn(mockedTextEntities);
//        when(textFactory.create(any(TextRequest.class), eq(false)))
//                .thenReturn(new BookObject("El llamado de la selva", TextType.BOOK, 100, true))
//                .thenReturn(new NovelObject("Cien años de soledad", TextType.NOVEL, 550, true));
//        // Execute
//        BudgetSaleQuoteResponse result = service.calculateBudgetSaleQuote(payload);
//
//        // Verify
//        assertNotNull(result);
//        assertTrue(result.getMessageFromServer().isSucceed());
//
//        verify(textRepository, times(1)).findAll();
//    }
//
//    @Test
//    public void testCalculateBudgetSaleQuote_WithoutNovels_ReturnsErrorObject() {
//        // Setup
//        BudgetSaleRequest payload = new BudgetSaleRequest(Arrays.asList(1, 2), 1000, LocalDate.of(2000, 3, 17));
//
//        // Mocking required methods in textRepository
//        List<TextEntity> mockedTextEntities = createMockTextEntities();
//        when(textRepository.findAll()).thenReturn(mockedTextEntities);
//        when(textFactory.create(any(TextRequest.class), eq(true)))
//                .thenReturn(new BookObject("El llamado de la selva", TextType.BOOK, 100, true))
//                .thenReturn(new BookObject("El llamado de la selva", TextType.BOOK, 200, true))
//                .thenReturn(new BookObject("El llamado de la selva", TextType.BOOK, 200, true))
//                .thenReturn(new BookObject("El llamado de la selva", TextType.BOOK, 200, true))
//                .thenReturn(new BookObject("El llamado de la selva", TextType.BOOK, 200, true))
//                .thenReturn(new BookObject("El llamado de la selva", TextType.BOOK, 200, true))
//                .thenReturn(new BookObject("Cien años de soledad", TextType.BOOK, 150, true))
//                .thenReturn(new BookObject("Cien años de soledad", TextType.BOOK, 550, true))
//                .thenReturn(new BookObject("Cien años de soledad", TextType.BOOK, 550, true))
//                .thenReturn(new BookObject("Cien años de soledad", TextType.BOOK, 550, true))
//                .thenReturn(new BookObject("Cien años de soledad", TextType.BOOK, 550, true))
//                .thenReturn(new BookObject("Cien años de soledad", TextType.BOOK, 550, true));
//        // Action
//        BudgetSaleQuoteResponse result = service.calculateBudgetSaleQuote(payload);
//
//        // Asserts
//        assertNotNull(result);
//        assertFalse(result.getMessageFromServer().isSucceed());
//
//        verify(textRepository, times(1)).findAll();
//
//    }
//
//    // Helper method to create a mock list of TextEntity objects
//    private List<TextEntity> createMockTextEntities() {
//        List<TextEntity> usableNovelEntitiesList = new ArrayList<>();
//        usableNovelEntitiesList.add(new TextEntity("El llamado de la selva", TextType.BOOK, 200));
//        usableNovelEntitiesList.add(new TextEntity("El llamado de la selva", TextType.BOOK, 200));
//        usableNovelEntitiesList.add(new TextEntity("El llamado de la selva", TextType.BOOK, 200));
//        usableNovelEntitiesList.add(new TextEntity("El llamado de la selva", TextType.BOOK, 200));
//        usableNovelEntitiesList.add(new TextEntity("El llamado de la selva", TextType.BOOK, 200));
//        usableNovelEntitiesList.add(new TextEntity("El llamado de la selva", TextType.BOOK, 200));
//        usableNovelEntitiesList.add(new TextEntity("Cien años de soledad", TextType.NOVEL, 550));
//        usableNovelEntitiesList.add(new TextEntity("Cien años de soledad", TextType.NOVEL, 550));
//        usableNovelEntitiesList.add(new TextEntity("Cien años de soledad", TextType.NOVEL, 550));
//        usableNovelEntitiesList.add(new TextEntity("Cien años de soledad", TextType.NOVEL, 550));
//        usableNovelEntitiesList.add(new TextEntity("Cien años de soledad", TextType.NOVEL, 550));
//        usableNovelEntitiesList.add(new TextEntity("Cien años de soledad", TextType.NOVEL, 550));
//        return usableNovelEntitiesList;
//    }
//
//}