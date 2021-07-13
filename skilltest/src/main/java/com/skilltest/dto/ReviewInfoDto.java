package com.skilltest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewInfoDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("rating")
    private Integer rating;

    @JsonProperty("user")
    private UserDto user;

    @JsonProperty("text")
    private String text;

    @JsonProperty("time_created")
    private String time_created;

    @JsonProperty("url")
    private String url;

}
