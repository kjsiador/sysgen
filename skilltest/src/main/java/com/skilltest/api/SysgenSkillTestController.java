package com.skilltest.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.skilltest.dto.BusinessIdRequestDto;
import com.skilltest.dto.ReviewsDto;
import com.skilltest.service.SysgenSkillTestService;

@RestController
public class SysgenSkillTestController {

    @Autowired
    SysgenSkillTestService service;

    @PostMapping(value = "/sysgen/skilltest/reviews",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ReviewsDto getReviews(@Valid @RequestBody BusinessIdRequestDto request) {
        return service.getReviews(request);
    }
}
