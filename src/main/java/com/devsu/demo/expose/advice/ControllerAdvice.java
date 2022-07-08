package com.devsu.demo.expose.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class ControllerAdvice {

  @ExceptionHandler(value = {Exception.class})
  public Mono<ResponseEntity<String>> badRequest() {
    return Mono.just(new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST));
  }

}
