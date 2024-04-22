package com.pinguinera.provider.controller;

import com.pinguinera.provider.model.dto.quote.request.*;
import com.pinguinera.provider.services.QuoteService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

//@RequestMapping("/provider")
@RestController
public class QuoteController {
    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/CreateStock")
    public ResponseEntity<?> createStockService(
            @Valid @RequestBody CreateStockRequest payload,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(quoteService.createStock(payload));
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/GetStockTexts")
    public ResponseEntity<?> getStockService(
            @Valid @RequestBody GetTextRequest payload,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(quoteService.getStockTexts(payload));
        }
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/SaveText")
    public ResponseEntity<?> saveAndQuoteTextService(
            @Valid @RequestBody SaveAndQuoteTextRequest payload,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(quoteService.saveAndQuoteText(payload));
        }
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/CalculateWholesaleQuote")
    public ResponseEntity<?> calculateWholesaleQuote(
            @Valid @RequestBody WholeSaleRequest payload,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(quoteService.calculateWholesaleQuote(payload));
        }
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/CalculateBudgetSaleQuote")
    public ResponseEntity<?> CalculateBudgetSaleQuote(
            @Valid @RequestBody BudgetSaleRequest payload,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(quoteService.calculateBudgetSaleQuote(payload));
        }
    }
}
