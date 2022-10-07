package org.abcstore.trades.controller;

import org.abcstore.trades.exception.InvalidTradeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class TradeControllerAdvice extends ResponseEntityExceptionHandler {
	@ExceptionHandler(InvalidTradeException.class)
	public ResponseEntity<Object> notFoundException(Exception ex, WebRequest request) {
		return new ResponseEntity<Object>("Not Acceptable", new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
	}

}
