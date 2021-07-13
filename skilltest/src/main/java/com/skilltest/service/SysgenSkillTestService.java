package com.skilltest.service;

import com.skilltest.dto.BusinessIdRequestDto;
import com.skilltest.dto.ReviewsDto;

public interface SysgenSkillTestService {

    ReviewsDto getReviews(BusinessIdRequestDto request);
}
