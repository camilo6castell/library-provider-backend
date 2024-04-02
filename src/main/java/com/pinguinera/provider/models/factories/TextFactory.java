package com.pinguinera.provider.models.factories;

import com.pinguinera.provider.models.DTOS.RetailSaleDTO;
import com.pinguinera.provider.models.entities.text.Book;
import com.pinguinera.provider.models.entities.text.Novel;
import com.pinguinera.provider.models.entities.text.Text;
import com.pinguinera.provider.models.enums.TextType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class TextFactory {
    public Text create(RetailSaleDTO payload, boolean isRetail) {
        Map<TextType, Text> textFilter = new HashMap<>();
        textFilter.put(TextType.BOOK, new Book(payload.getTitle(), payload.getBasePrice()));
        textFilter.put(TextType.NOVEL, new Novel(payload.getTitle(), payload.getBasePrice()));

        Text isText = textFilter.get(payload.getType());

        if (isText == null) {
            throw new IllegalArgumentException("Type unrecognized.");
        } else {
            isText.setIsRetail(isRetail);
            return isText;
        }


    }
}
