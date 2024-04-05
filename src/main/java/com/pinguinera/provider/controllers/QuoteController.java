package com.pinguinera.provider.controllers;

import com.pinguinera.provider.models.DTOS.BudgetSaleDTO;
import com.pinguinera.provider.models.DTOS.FillDataBaseDTO;
import com.pinguinera.provider.models.DTOS.RetailSaleDTO;
import com.pinguinera.provider.models.DTOS.WholeSaleDTO;
import com.pinguinera.provider.models.objects.response.ResponseObject;
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

    @GetMapping("/FillDataBase")
    public ResponseEntity<?> fillDataBase(@Valid @RequestBody FillDataBaseDTO payload, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(quoteService.createStock(payload));
        }
    }

    @PostMapping("/CalculateRetailSaleQuote")
    public ResponseEntity<?> calculateRetailSaleQuote(@Valid @RequestBody RetailSaleDTO payload, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(quoteService.calculateRetailSaleQuote(payload));
        }
    }

    @PostMapping("/CalculateWholesaleQuote")
    public ResponseEntity<?> calculateWholesaleQuote(@Valid @RequestBody WholeSaleDTO payload, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(quoteService.calculateWholesaleQuote(payload));
        }
    }

    @PostMapping("/CalculateBudgetSaleQuote")
    public ResponseEntity<?> CalculateBudgetSaleQuote(@Valid @RequestBody BudgetSaleDTO payload, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(quoteService.calculateBudgetSaleQuote(payload));
        }
    }
}
