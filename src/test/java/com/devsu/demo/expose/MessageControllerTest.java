package com.devsu.demo.expose;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.devsu.demo.expose.request.MessageDTO;

import reactor.core.publisher.Mono;

@AutoConfigureWebTestClient(timeout = "50000")
@WebFluxTest(controllers = MessageController.class)

public class MessageControllerTest {
    @Autowired
    private WebTestClient client;
    @Test
    void test1(){
        client.post()
        .uri("/DevOps")
        .header("X-Parse-REST-API-Key", "2f5ae96c-b558-4c7b-a590-a501ae1c3f6c")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .body(Mono.just(MessageDTO.builder()
            .to("asd")
            .from("asd")
            .message("message").build()),MessageDTO.class)
        .exchange().expectStatus().isOk();
    }
}
