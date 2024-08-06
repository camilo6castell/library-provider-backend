package com.libraryproviderbackend.user.entity;

import com.libraryproviderbackend.generic.Entity;
import com.libraryproviderbackend.text.values.TextTypeEnum;
import com.libraryproviderbackend.text.values.Type;
import com.libraryproviderbackend.user.values.identities.BookQuoteId;
import com.libraryproviderbackend.user.values.shared.Discount;
import com.libraryproviderbackend.user.values.shared.DiscountsEnum;
import com.libraryproviderbackend.user.values.shared.Subtotal;
import com.libraryproviderbackend.text.values.Title;
import com.libraryproviderbackend.user.values.shared.Total;

public class TextQuote extends Entity<BookQuoteId> implements Cloneable{

    private final float INCREMENT_BY_DEMAND_NOVEL = 2f;
    private final float INCREMENT_BY_DEMAND_BOOK = 1.33f;
    private final float WHOLE_SALE_DECREMENT = 0.9985f;
    private final float RETAIL_SALE_INCREMENT = 1.02f;

    public Title title;
    public Type type;
    public Subtotal subtotal;
    public Discount discount;
    public Total total;


    public TextQuote() {
        super(new BookQuoteId());
    }

    public TextQuote(String title, Float initialPrice, TextTypeEnum type, DiscountsEnum discount) {
        super(new BookQuoteId());

        this.title = Title.of(title);

        this.type = Type.of(type);

        this.discount = Discount.of(discount);

        this.subtotal = discount == DiscountsEnum.NONE ? Subtotal.of(
                initialPrice*
                        (type == TextTypeEnum.BOOK ?
                                INCREMENT_BY_DEMAND_BOOK : INCREMENT_BY_DEMAND_NOVEL
                        ) * RETAIL_SALE_INCREMENT

        ) : Subtotal.of(
                initialPrice *
                        (type== TextTypeEnum.BOOK ?
                                INCREMENT_BY_DEMAND_BOOK : INCREMENT_BY_DEMAND_NOVEL
                        ));
        this.total = discount == DiscountsEnum.NONE ? Total.of(
                this.subtotal.value()
        ) : Total.of(this.subtotal.value() *
                WHOLE_SALE_DECREMENT
        );
    }

    @Override
    public TextQuote clone() {
        try {
            TextQuote clone = (TextQuote) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }



    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Subtotal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Subtotal subtotal) {
        this.subtotal = subtotal;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }
}
