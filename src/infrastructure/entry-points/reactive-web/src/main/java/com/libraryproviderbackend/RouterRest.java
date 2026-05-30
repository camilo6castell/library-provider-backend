package com.libraryproviderbackend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Functional router for the reactive REST API.
 *
 * <p>URL conventions:
 * <ul>
 *   <li>{@code POST /api/v1/users}               — create a user account</li>
 *   <li>{@code POST /api/v1/texts/quote}          — save a text and quote it for a user</li>
 *   <li>{@code POST /api/v1/texts/quote-by-budget}— get the best set of texts within a budget</li>
 * </ul>
 */
@Configuration
public class RouterRest {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST("/api/v1/users"),                    handler::createUser)
                .andRoute(POST("/api/v1/texts/quote"),         handler::saveAndQuoteText)
                .andRoute(POST("/api/v1/texts/quote-by-budget"), handler::quoteTextsByBudget);
    }
}
