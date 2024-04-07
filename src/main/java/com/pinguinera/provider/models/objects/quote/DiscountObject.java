package com.pinguinera.provider.models.objects.quote;

public class DiscountObject {
    private String concept;
    private float percentage;

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

    public DiscountObject(String concept, float percentage) {
        this.concept = concept;
        this.percentage = percentage;
    }
}
