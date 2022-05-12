package com.skilltest.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.skilltest.api.SysgenSkillTestController;
import com.skilltest.error.ErrorDto;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice(assignableTypes = SysgenSkillTestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class SysgenSkillTestExceptionHandler {

    private static final String LOG_TEMPLATE = "{}::{}() - {}";

    /**
     * handle Json format exceptions
     *
     * @param Exception ex
     *
     * @return ErrorDto
     */
    @ExceptionHandler({ JsonParseException.class, JsonMappingException.class, MismatchedInputException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleJsonParseMappingException(Exception ex) {
        final ErrorDto error = new ErrorDto();
        error.setError_message("Invalid JSON Format.");

        LOGGER.error(LOG_TEMPLATE,
                getClass().getSimpleName(),
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                ex.getMessage());

        return error;
    }

    /**
     * handle constraint validation exceptions
     *
     * @param MethodArgumentNotValidException ex
     *
     * @return ErrorDto
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        final ErrorDto error = new ErrorDto();

        for (final FieldError field : ex.getBindingResult().getFieldErrors()) {

            error.setError_message(field.getDefaultMessage());
        }

        LOGGER.error(LOG_TEMPLATE,
                getClass().getSimpleName(),
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                ex.getMessage());

        return error;
    }

    /**
     * handle custom defined exception
     *
     * @param SysgenSkillTestException ex
     *
     * @return ErrorDto
     */
    @ExceptionHandler(SysgenSkillTestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleSysgenSkillTestException(
            SysgenSkillTestException ex) {

        final ErrorDto error = new ErrorDto();
        error.setError_message(ex.getErrorMessage());

        LOGGER.error(LOG_TEMPLATE,
                getClass().getSimpleName(),
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                ex.getErrorMessage());

        return error;
    }
}
