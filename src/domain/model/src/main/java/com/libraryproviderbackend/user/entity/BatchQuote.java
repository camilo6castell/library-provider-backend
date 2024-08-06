package com.libraryproviderbackend.user.entity;

import com.libraryproviderbackend.generic.Entity;
import com.libraryproviderbackend.text.Text;
import com.libraryproviderbackend.text.values.TextTypeEnum;
import com.libraryproviderbackend.user.User;
import com.libraryproviderbackend.user.values.batchquote.Change;
import com.libraryproviderbackend.user.values.batchquote.TextQuoteResponse;
import com.libraryproviderbackend.user.values.identities.BatchQuoteId;
import com.libraryproviderbackend.user.values.shared.Discount;
import com.libraryproviderbackend.user.values.shared.DiscountsEnum;
import com.libraryproviderbackend.user.values.shared.Subtotal;
import com.libraryproviderbackend.user.values.shared.Total;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BatchQuote extends Entity<BatchQuoteId> {

    public float discountValue;
    public float toWholeSale = 0;

    public List<TextQuote> bookQuoteList;
    public List<TextQuote> novelQuoteList;
    public Subtotal subtotal;
    public Discount discount;
    public Total total;
    public Change change;

    public BatchQuote(
            List<Text> bookList,
            List<Text> novelList,
            LocalDate entryDate
    ) {
        super(new BatchQuoteId());

        this.discountValue = User.calculateSeniorityDiscount(entryDate);
        this.discount = discountValue == 1f ?  Discount.of(DiscountsEnum.NONE) : Discount.of(DiscountsEnum.SENIORITY);

        bookList.sort(Comparator.comparing(Text::getInitialFloatPrice).reversed());
        novelList.sort(Comparator.comparing(Text::getInitialFloatPrice).reversed());

        this.bookQuoteList = createNewTextQuote(bookList);
        this.novelQuoteList = createNewTextQuote(novelList);
    }

    public BatchQuote(
            List<Text> textsList,
            Float budget,
            LocalDate entryDate
    ) {

        super(new BatchQuoteId());
        this.bookQuoteList = new ArrayList<>();
        this.total = Total.of(0f);
        this.subtotal = Subtotal.of(0f);
        this.discountValue = User.calculateSeniorityDiscount(entryDate);
        this.discount = discountValue == 1f ? Discount.of(DiscountsEnum.NONE) : Discount.of(DiscountsEnum.SENIORITY);

        textsList.sort(Comparator.comparing(Text::getInitialFloatPrice).reversed());

        if (containsBookAndNovel(textsList)) {
            Text cheapestBook = getCheapestText(textsList, TextTypeEnum.BOOK);
            Text cheapestNovel = getCheapestText(textsList, TextTypeEnum.NOVEL);

            TextQuote cheapestBookQuote = new TextQuote(
                    cheapestBook.getTitle().value(),
                    cheapestBook.getInitialFloatPrice(),
                    cheapestBook.getType().value(),
                    DiscountsEnum.WHOLESALE
            );

            TextQuote cheapestNovelQuote = new TextQuote(
                    cheapestNovel.getTitle().value(),
                    cheapestNovel.getInitialFloatPrice(),
                    cheapestNovel.getType().value(),
                    DiscountsEnum.WHOLESALE
            );
            boolean isFirstTime = true;
            int count = 1;
            if (cheapestBookQuote.total.value() > cheapestNovelQuote.total.value()) {

                while (budget > 0) {

                    if (budget - cheapestBookQuote.total.value() < 0 && isFirstTime) {
                        break;
                    }
                    if (budget - cheapestNovelQuote.total.value() < 0 && !isFirstTime) {
                        break;
                    }
                    if (isFirstTime) {
                        TextQuote auxCheapestBookQuote = new TextQuote(
                                cheapestBook.getTitle().value(),
                                cheapestBook.getInitialFloatPrice(),
                                cheapestBook.getType().value(),
                                DiscountsEnum.NONE);

                        this.bookQuoteList.add(auxCheapestBookQuote);
                        budget -= auxCheapestBookQuote.total.value();

                        isFirstTime = false;

                        this.subtotal = Subtotal.of(this.total.value() + auxCheapestBookQuote.total.value());
                        this.total = Total.of(this.total.value() + auxCheapestBookQuote.total.value());

                    } else {
                        if (count < 10) {
                            TextQuote auxCheapestNovelQuote = new TextQuote(
                                    cheapestNovel.getTitle().value(),
                                    cheapestNovel.getInitialFloatPrice(),
                                    cheapestNovel.getType().value(),
                                    DiscountsEnum.NONE);

                            this.bookQuoteList.add(auxCheapestNovelQuote);
                            budget -= cheapestNovelQuote.total.value();
                            this.total = Total.of(this.subtotal.value() + cheapestBookQuote.total.value());
                            this.subtotal =  Subtotal.of(this.subtotal.value() + cheapestBookQuote.subtotal.value());

                            count += 1;
                        } else {
                            TextQuote auxCheapestNovelQuote = new TextQuote(
                                    cheapestNovel.getTitle().value(),
                                    cheapestNovel.getInitialFloatPrice(),
                                    cheapestNovel.getType().value(),
                                    DiscountsEnum.WHOLESALE);

                            this.bookQuoteList.add(auxCheapestNovelQuote);
                            budget -= cheapestNovelQuote.total.value();
                            this.total = Total.of(this.subtotal.value() + cheapestBookQuote.total.value());
                            this.subtotal =  Subtotal.of(this.subtotal.value() + cheapestBookQuote.subtotal.value());

                            count += 1;
                        }
                    }
                }

            } else {
                while (budget > 0) {
                    if (budget - cheapestNovelQuote.total.value() < 0 && isFirstTime) {
                        break;
                    }
                    if (budget - cheapestBookQuote.total.value() < 0 && !isFirstTime) {
                        break;
                    }
                    if (isFirstTime) {

                        TextQuote auxCheapestNovelQuote = new TextQuote(
                                cheapestNovel.getTitle().value(),
                                cheapestNovel.getInitialFloatPrice(),
                                cheapestNovel.getType().value(),
                                DiscountsEnum.NONE);

                        this.bookQuoteList.add(auxCheapestNovelQuote);

                        budget -= auxCheapestNovelQuote.total.value();

                        isFirstTime = false;

                        this.total = Total.of(this.total.value() + auxCheapestNovelQuote.total.value());
                        this.subtotal = Subtotal.of(this.subtotal.value() + auxCheapestNovelQuote.total.value());



                    } else {
                        if (count < 10) {
                            TextQuote auxCheapestBookQuote = new TextQuote(
                                    cheapestBook.getTitle().value(),
                                    cheapestBook.getInitialFloatPrice(),
                                    cheapestBook.getType().value(),
                                    DiscountsEnum.NONE);

                            this.bookQuoteList.add(auxCheapestBookQuote.clone());
                            budget -= auxCheapestBookQuote.total.value();
                            this.total = Total.of(this.total.value() + auxCheapestBookQuote.total.value());

                            this.subtotal =  Subtotal.of(this.subtotal.value() + auxCheapestBookQuote.subtotal.value());

                            count += 1;
                        } else {
                            TextQuote auxCheapestBookQuote = new TextQuote(
                                    cheapestBook.getTitle().value(),
                                    cheapestBook.getInitialFloatPrice(),
                                    cheapestBook.getType().value(),
                                    DiscountsEnum.WHOLESALE);

                            this.bookQuoteList.add(auxCheapestBookQuote);
                            budget -= auxCheapestBookQuote.total.value();
                            this.total = Total.of(this.total.value() + auxCheapestBookQuote.total.value());

                            this.subtotal =  Subtotal.of(this.subtotal.value() + auxCheapestBookQuote.subtotal.value());

                            count += 1;
                        }
                    }
                }
            }

        } else {
            throw new RuntimeException("Error, se necesita por lo menos un libro y una novela " +
                    "para poder hacer esta cotización.");
        }
        this.change = Change.of(budget);




    }

    public Text getCheapestText(List<Text> textList, TextTypeEnum textEnum){
        Text auxText = null;
        float lowestPrice = Float.MAX_VALUE;

        for (Text text : textList){
            if (text.getType().value() == textEnum){
                TextQuote auxTextQuote = new TextQuote(
                        text.getTitle().value(),
                        text.getInitialFloatPrice(),
                        text.getType().value(),
                        DiscountsEnum.WHOLESALE
                );
                if (auxTextQuote.total.value() < lowestPrice){
                    lowestPrice = auxTextQuote.total.value();
                    auxText = text;
                }
            }
        }
        return auxText;
    }

    public boolean containsBookAndNovel(List<Text> texts) {
        boolean hasBook = hasTypeText(TextTypeEnum.BOOK, texts);
        boolean hasNovel = hasTypeText(TextTypeEnum.NOVEL, texts);
        return hasBook && hasNovel;
    }

    private boolean hasTypeText(TextTypeEnum type, List<Text> texts) {
        boolean hasTextType = false;
        for (Text text : texts) {
            if (text.getType().value() == type) {
                hasTextType = true;
                break;
            }
        }
        return hasTextType;
    }









    public List<TextQuote> createNewTextQuote(List<Text> textList){
        List<TextQuote> auxTextQuoteList = new ArrayList<>();
        float totalAux = 0f;
        float subTotalAux = 0f;
        for (int i = 0; i < textList.size(); i++) {

            auxTextQuoteList.add(
                    new TextQuote(
                            textList.get(i).getTitle().value(),
                            textList.get(i).getInitialFloatPrice(),
                            textList.get(i).getType().value(),
                            toWholeSale < 10 ? DiscountsEnum.NONE : DiscountsEnum.WHOLESALE
                    )
            );
            toWholeSale += 1;
            TextQuote lastTextQuote = auxTextQuoteList.getLast();
            subTotalAux += lastTextQuote.total.value();
            totalAux += auxTextQuoteList.getLast().total.value();
        }

        totalAux = totalAux * discountValue;

        this.subtotal = Subtotal.of(subTotalAux);
        this.total = Total.of(totalAux);
        return auxTextQuoteList;
    }


    public static List<TextQuoteResponse> mapToTextQuoteResponses(List<TextQuote> textQuotes) {
        List<TextQuoteResponse> textQuoteResponses = new ArrayList<>();
        for (int i = 0; i < textQuotes.size(); i++) {
            TextQuote textQuote =textQuotes.get(i);
            textQuoteResponses.add(new TextQuoteResponse(
                    textQuote.title.value(),
                    textQuote.type.value().toString(),
                    textQuote.subtotal.value(),
                    textQuote.discount.value().toString(),
                    textQuote.total.value())
            );
        }
        return textQuoteResponses;
    }









    public List<TextQuote> getBookQuoteList() {
        return bookQuoteList;
    }

    public void setBookQuoteList(List<TextQuote> bookQuoteList) {
        this.bookQuoteList = bookQuoteList;
    }

    public List<TextQuote> getNovelQuoteList() {
        return novelQuoteList;
    }

    public void setNovelQuoteList(List<TextQuote> novelQuoteList) {
        this.novelQuoteList = novelQuoteList;
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

    public Change getChange() {
        return change;
    }

    public void setChange(Change change) {
        this.change = change;
    }
}
