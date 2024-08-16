package com.libraryproviderbackend.text.events;

import com.libraryproviderbackend.generic.DomainEvent;
import com.libraryproviderbackend.text.values.TextTypeEnum;


public class TextCreated extends DomainEvent {
    public String title;
    public TextTypeEnum textType;
    public Float initialPrice;

    public TextCreated() {
    }

    /**
     * Crea un evento que representa la creación de un texto.
     *
     * @param title       Título del texto.
     * @param textType    Tipo de texto, representado por un enum.
     * @param initialPrice Precio inicial del texto, usando Float para mayor precisión.
     */
    public TextCreated(
            String title,
            TextTypeEnum textType,
            Float initialPrice
    ) {
        super(TextEventsEnum.TEXT_CREATED.toString());
        this.title = title;
        this.textType = textType;
        this.initialPrice = initialPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTextType(TextTypeEnum textType) {
        this.textType = textType;
    }

    public void setInitialPrice(Float initialPrice) {
        this.initialPrice = initialPrice;
    }

    public TextTypeEnum getTextType() {
        return textType;
    }

    public Float getInitialPrice() {
        return initialPrice;
    }
}


//public class TextCreated extends DomainEvent {
//    public String textId;
//    public String title;
//    public int textType;
//    public Float initialPrice;
//
//    public TextCreated() {
//    }
//
//    public TextCreated(
//            String textId,
//            String title,
//            int textType,
//            Float initialPrice
//    ) {
//        super(TextEventsEnum.TEXT_CREATED.toString());
//        this.textId = textId;
//        this.title = title;
//        this.textType = textType;
//        this.initialPrice = initialPrice;
//    }
//
//    public String getTextId() {
//        return textId;
//    }
//
//    public void setTextId(String textId) {
//        this.textId = textId;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public int getTextType() {
//        return textType;
//    }
//
//    public void setTextType(int textType) {
//        this.textType = textType;
//    }
//
//    public Float getInitialPrice() {
//        return initialPrice;
//    }
//
//    public void setInitialPrice(Float initialPrice) {
//        this.initialPrice = initialPrice;
//    }
//}
