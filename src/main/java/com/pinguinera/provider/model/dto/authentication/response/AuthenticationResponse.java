package com.pinguinera.provider.model.dto.authentication.response;


import com.pinguinera.provider.model.persistence.UserEntity;

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
        return new UserEntity.Builder();
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
