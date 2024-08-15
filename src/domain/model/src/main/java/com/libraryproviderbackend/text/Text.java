package com.libraryproviderbackend.text;

import com.libraryproviderbackend.generic.AggregateRoot;
import com.libraryproviderbackend.text.events.TextCreated;
import com.libraryproviderbackend.text.values.*;

import java.math.BigDecimal;
import java.util.List;

public class Text extends AggregateRoot<TextId> {

    public Title title;
    public Type type;
    public InitialPrice initialPrice;

    /**
     * Constructor para crear una nueva instancia de Text.
     *
     * @param textId        ID del texto.
     * @param title         Título del texto.
     * @param type          Tipo de texto.
     * @param initialPrice  Precio inicial del texto.
     */
    public Text(
            TextId textId,
            Title title,
            Type type,
            InitialPrice initialPrice
    ) {
        super(textId);
        subscribe(new TextBehavior(this));
        appendEvent(
                new TextCreated(
                        title.value(),
                        type.value(),
                        initialPrice.value()
                )
        ).apply();
    }


    private Text(TextId textId){
        super(textId);
        subscribe(new TextBehavior(this));
    }

    public static Text from(TextId textId, List<TextCreated> events) {
        Text text = new Text(textId);
        events.forEach(text::applyEvent);
        return text;
    }

    /**
     * Obtiene el título del texto.
     *
     * @return El título del texto.
     */
    public Title getTitle() {
        return title;
    }

    /**
     * Obtiene el tipo de texto.
     *
     * @return El tipo de texto.
     */
    public Type getType() {
        return type;
    }

    /**
     * Obtiene el precio inicial del texto.
     *
     * @return El precio inicial del texto.
     */
    public InitialPrice getInitialPrice() {
        return initialPrice;
    }

    /**
     * Obtiene el precio inicial del texto como BigDecimal.
     *
     * @return El precio inicial del texto.
     */
    public Float getInitialFloatPrice() {
        return initialPrice.value();
    }
}


//public class Text extends AggregateRoot<TextId> {
//
//    public static TextTypeEnum[] textTypeEnumValues = TextTypeEnum.values();
//
//    public Title title;
//    public Type type;
//    public InitialPrice initialPrice;
//
//    public Text() {
//        super(new TextId());
//    }
//
//    public Text(
//            TextId textId,
//            Title title,
//            Type type,
//            InitialPrice initialPrice
//    ) {
//        super(textId);
//        subscribe(new TextBehavior(this));
//        appendEvent(
//                new TextCreated(
//                        textId.value(),
//                        title.value(),
//                        type.value(),
//                        initialPrice.value()
//                )
//        ).apply();
//    }
//
//    public static Text from(
//            TextCreated event
//    ) {
//        Text text = new Text();
//        text.title = Title.of(event.getTitle());
//        text.type = Type.of(event.getTextType());
//        text.initialPrice = InitialPrice.of(event.getInitialPrice());
//        return text;
//    }
//
//    public Title getTitle() {
//        return title;
//    }
//
//    public void setTitle(Title title) {
//        this.title = title;
//    }
//
//    public Type getType() {
//        return type;
//    }
//
//    public void setType(Type type) {
//        this.type = type;
//    }
//
//    public InitialPrice getInitialPrice() {
//        return initialPrice;
//    }
//
//    public BigDecimal getInitialFloatPrice() {
//        return initialPrice.value();
//    }
//    public void setInitialPrice(InitialPrice initialPrice) {
//        this.initialPrice = initialPrice;
//    }
//}
