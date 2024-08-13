package com.libraryproviderbackend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(
                POST("/api/v1/createUser"),
                handler::listenPOSTCreateUserUseCase
        )
//                .andRoute(
//                POST("/api/v1/saveAndQuoteText"),
//                handler::listenPOSTSaveAndQuoteTextUseCase
//        ).andRoute(
//                POST("/api/v1/QuoteVariousTexts"),
//                handler::listenPOSTQuoteVariousTextsUseCase
//        ).andRoute(
//                POST("/api/v1/QuoteTextsByBudget"),
//                handler::listenPOSTQuoteTextsByBudgetUseCase
//        )
//                .andRoute(
//                POST("/api/v1/QuoteBatchQuote"),
//                handler::listenPOSTQuoteBatchQuoteUseCase
//        )
                ;
    }
}
