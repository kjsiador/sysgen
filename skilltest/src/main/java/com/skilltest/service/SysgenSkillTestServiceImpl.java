package com.skilltest.service;

import org.apache.commons.lang3.ObjectUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.skilltest.config.ImaggaApiConfig;
import com.skilltest.config.YelpApiConfig;
import com.skilltest.domain.Result;
import com.skilltest.dto.BusinessIdRequestDto;
import com.skilltest.dto.ReviewInfoDto;
import com.skilltest.dto.ReviewsDto;
import com.skilltest.exceptions.SysgenSkillTestException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SysgenSkillTestServiceImpl implements SysgenSkillTestService {

    @Autowired
    private YelpApiConfig yelp;

    @Autowired
    private ImaggaApiConfig imagga;

    private static final String EXTERNAL_API_ERROR = "Error calling %s external api.";

    private static final String LOG_TEMPLATE = "{}::{}() - {}";

    private static final String YELP = "yelp";

    private static final String IMAGGA = "imagga";

    @Override
    public ReviewsDto getReviews(BusinessIdRequestDto request) {

        final ReviewsDto reviews = callYelpApi(request.getId());
        for (final ReviewInfoDto info : reviews.getReviews()) {
            final Result result = callImaggaApi(info.getUser().getImage_url());
            if (!ObjectUtils.isEmpty(result)) {
                info.setImageRecognitionResult(result);
            }
        }

        return reviews;
    }

    /**
     * Call Yelp Api to fetch review details
     *
     * @param String id (business)
     *
     * @return ReviewsDto
     */
    private <T> ReviewsDto callYelpApi(String id) {

        ResponseEntity<ReviewsDto> responseEntity = null;
        try {
            final String apiHostUrl = yelp.getApiPath() + id + "/reviews";

            final RestTemplate restTemplate = new RestTemplate();
            responseEntity = restTemplate.exchange(apiHostUrl,
                    HttpMethod.GET,
                    new HttpEntity<T>(createHeader(yelp.getApiKey())),
                    ReviewsDto.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {

                LOGGER.info(LOG_TEMPLATE,
                        getClass().getSimpleName(),
                        Thread.currentThread().getStackTrace()[1].getMethodName(),
                        "Yelp Api Response:" + responseEntity);

            } else {
                throw new SysgenSkillTestException(String.format(EXTERNAL_API_ERROR, YELP));
            }

        } catch (final RestClientException rce) {
            throw new SysgenSkillTestException(String.format(EXTERNAL_API_ERROR, YELP));
        }

        return responseEntity.getBody();
    }

    /**
     * Call Imagga Api to fetch image recognition details
     *
     * @param String image url from yelp data
     *
     * @return Result
     */
    private <T> Result callImaggaApi(String imageUrl) {

        ResponseEntity<Result> responseEntity = null;
        try {
            final String apiHostUrl = imagga.getApiPath() + "?image_url=" + imageUrl;

            final RestTemplate restTemplate = new RestTemplate();
            responseEntity = restTemplate.exchange(apiHostUrl,
                    HttpMethod.GET,
                    new HttpEntity<T>(createHeader(imagga.getApiKey())),
                    Result.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {

                LOGGER.info(LOG_TEMPLATE,
                        getClass().getSimpleName(),
                        Thread.currentThread().getStackTrace()[1].getMethodName(),
                        "Imagga Api Response:" + responseEntity);

            } else {
                throw new SysgenSkillTestException(String.format(EXTERNAL_API_ERROR, IMAGGA));
            }

        } catch (final RestClientException rce) {
            throw new SysgenSkillTestException(String.format(EXTERNAL_API_ERROR, IMAGGA));
        }

        return responseEntity.getBody();

    }

    /**
     * Setup http headers for calling externap apis
     *
     * @param api key
     *
     * @return HttpHeaders
     */
    private HttpHeaders createHeader(String apiKey) {
        return new HttpHeaders() {
            {
                set("Authorization", apiKey);
            }
        };
    }
}
