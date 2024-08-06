package com.pinguinera.provider.models.objects.response;

public class ResponseObject {
    private boolean succeed;
    private String message;

    public ResponseObject(boolean succeed, String message) {
        this.succeed = succeed;
        this.message = message;
    }

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
