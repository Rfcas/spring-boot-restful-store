package com.example.store.impl.logic.exceptions.handler;

import com.example.store.impl.logic.exceptions.JsonError;
import com.example.store.impl.logic.exceptions.ProductBadRequestException;
import com.example.store.impl.logic.exceptions.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice(annotations = RestController.class)
@Slf4j
public class ExceptionHandlerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    protected JsonError productNotFound(final Exception exception) {
        return getJsonException(exception, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProductBadRequestException.class)
    protected JsonError productBadRequest(final Exception exception) {
        return getJsonException(exception, HttpStatus.NOT_FOUND);
    }

    private JsonError getJsonException(Exception exception, HttpStatus httpStatus) {
        log.warn("Translating exception", exception);
        JsonError ex = new JsonError();
        ex.setCode(httpStatus.value());
        ex.setStatus(httpStatus.getReasonPhrase());
        ex.setStackTrace(exception.getStackTrace());
        return ex;
    }

}
