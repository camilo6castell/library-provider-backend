package com.pinguinera.provider.model.dto.quote.response.shared;

public class DiscountResponse {
    private String concept;
    private float percentage;

    public DiscountResponse() {
    }

    public DiscountResponse(String concept, float percentage) {
        this.concept = concept;
        this.percentage = percentage;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }


}
