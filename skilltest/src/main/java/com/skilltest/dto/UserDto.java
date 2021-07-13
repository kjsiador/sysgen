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
public class UserDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("profile_url")
    private String profile_url;

    @JsonProperty("image_url")
    private String image_url;

    @JsonProperty("name")
    private String name;

}
