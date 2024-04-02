package com.pinguinera.provider.models.factories;

import com.pinguinera.provider.models.DTOS.RetailSaleDTO;
import com.pinguinera.provider.models.entities.text.Book;
import com.pinguinera.provider.models.entities.text.Text;
import com.pinguinera.provider.models.enums.TextType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class TextFactory {
    public Text create(RetailSaleDTO payload) {
        Map<TextType, Text> textFilter = new HashMap<>();
        textFilter.put(TextType.BOOK, new Book(payload.getTitle(), payload.getBasePrice(), false));
        return null;
    }
}
