package com.shoppertrak.exception;
  
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class TimeError extends RuntimeException {

        private static final long serialVersionUID = 1L;
        private static final String MESSAGE = "Time parameter is not correct (%s)";

        public TimeError(String time) {
        super(String.format(MESSAGE, time));
    }
}
