package com.reactive.Dtos.authentication.response;


public class AuthenticationResponse {
    private String token;

    private AuthenticationResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private final AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        public Builder token(String token){
            authenticationResponse.token = token;
            return this;
        }
        public AuthenticationResponse build(){
            return authenticationResponse;
        }
    }

}
