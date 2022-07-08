package com.devsu.demo.expose;

import com.devsu.demo.expose.request.MessageDTO;
import com.devsu.demo.expose.response.MessageResponse;
import io.swagger.annotations.ApiOperation;
import java.util.Objects;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Validated

public class MessageController {

    @PostMapping("/DevOps")
    @ApiOperation(value = "", notes = "", response = MessageResponse.class)
    public Mono<ResponseEntity<MessageResponse>> sendMessage(@RequestHeader("api-key") String apiKey,
                                                             @RequestBody @Valid MessageDTO messageDTO) throws Exception {
  
      if (!Objects.equals(apiKey, "2f5ae96c-b558-4c7b-a590-a501ae1c3f6c")) throw new Exception();
      return Mono.just(new ResponseEntity<>(MessageResponse.builder()
          .message("Hello " + messageDTO.getFrom() + " your message will be send")
          .build(), HttpStatus.OK));
    }
  
  }