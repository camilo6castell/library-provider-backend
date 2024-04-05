package com.pinguinera.provider.models.factories;

import com.pinguinera.provider.models.DTOS.RetailSaleDTO;
import com.pinguinera.provider.models.objects.text.BookObject;
import com.pinguinera.provider.models.objects.text.NovelObject;
import com.pinguinera.provider.models.objects.text.TextObject;
import com.pinguinera.provider.models.enums.TextType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class TextFactory {
    public TextObject create(RetailSaleDTO payload, boolean isRetail) {
        Map<TextType, TextObject> textFilter = new HashMap<>();
        textFilter.put(TextType.BOOK, new BookObject(payload.getTitle(), payload.getBasePrice()));
        textFilter.put(TextType.NOVEL, new NovelObject(payload.getTitle(), payload.getBasePrice()));

        TextObject isTextObject = textFilter.get(payload.getType());

        if (isTextObject == null) {
            throw new IllegalArgumentException("Type unrecognized.");
        } else {
            isTextObject.setIsRetail(isRetail);
            return isTextObject;
        }


    }
}
