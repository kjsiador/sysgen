package com.skilltest.config;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@ConfigurationProperties(prefix = "yelp")
@Component
@Validated
@Data
public class YelpApiConfig {

    @NotBlank
    private String apiPath;

    @NotBlank
    private String apiKey;
}
