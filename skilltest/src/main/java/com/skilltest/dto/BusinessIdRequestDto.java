package com.skilltest.dto;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessIdRequestDto {

    @NotEmpty(message = "Required item is missing or empty.")
    @JsonProperty("id")
    private String id;

}
