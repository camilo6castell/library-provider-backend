package com.libraryproviderbackend;

import com.libraryproviderbackend.usecase.CreateTextUseCase;
import com.libraryproviderbackend.usecase.CreateUserUseCase;
import com.libraryproviderbackend.usecase.QuoteTextsByBudgetUseCase;
import com.libraryproviderbackend.usecase.SaveAndQuoteTextUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HandlerConfig {

    @Bean
    public Handler handler(
            CreateUserUseCase createUserUseCase,
            SaveAndQuoteTextUseCase saveAndQuoteTextUseCase,
            QuoteTextsByBudgetUseCase quoteTextsByBudgetUseCase,
            CreateTextUseCase createTextUseCase
    ) {
        return new Handler(
                createUserUseCase,
                saveAndQuoteTextUseCase,
                quoteTextsByBudgetUseCase,
                createTextUseCase
        );
    }
}