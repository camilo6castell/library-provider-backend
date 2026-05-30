package com.libraryproviderbackend.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * Configures component scanning for the use case layer.
 * Only classes whose simple name ends with "UseCase" are picked up,
 * keeping the application context minimal and explicit.
 */
@Configuration
@ComponentScan(
        basePackages = "com.libraryproviderbackend.usecase",
        includeFilters = @ComponentScan.Filter(
                type = FilterType.REGEX,
                pattern = "^.+UseCase$"
        )
)
public class UseCaseConfig {
}
