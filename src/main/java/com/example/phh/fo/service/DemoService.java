package com.example.phh.fo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.phh.fo.model.Todo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class DemoService {
    @Value("${bo.url}")
    private String bo_url;
    private WebClient webClient = WebClient.create();

    public String ccbo_api(String param1, String param2) {
        String result = webClient.get()
        .uri(bo_url + "ccbo/{param1}/{param2}", param1, param2)
        .exchange()
        .flatMap(response -> response.bodyToMono(String.class))
        .block();
        return result;
    }

    public List<Todo> findAll() {
        List<Todo> result = webClient.get()
        .uri(bo_url + "findAll")
        .exchange()
        .flatMap(response -> response.bodyToMono(ArrayList.class))
        .block();
        return result;
    }

    public Todo findById(String id) {
        Todo result = webClient.get()
        .uri(bo_url + "find/{id}", id)
        .exchange()
        .flatMap(response -> response.bodyToMono(Todo.class))
        .block();
        return result;
    }

    public Todo add(Todo todo) {
    	Todo result = webClient.post()
        .uri(bo_url + "add")
        .body(Mono.just(todo), Todo.class)
        .exchange()
        .flatMap(response -> response.bodyToMono(Todo.class))
        .block();
        return result;
    }

    public Todo modify(Todo todo) {
    	Todo result = webClient.put()
        .uri(bo_url + "modify")
        .body(Mono.just(todo), Todo.class)
        .exchange()
        .flatMap(response -> response.bodyToMono(Todo.class))
        .block();
        return result;
    }

    public int remove(String id) throws Exception {
    	int result = webClient.delete()
        .uri(bo_url + "remove/{id}", id)
        .exchange()
        .flatMap(response -> response.bodyToMono(Integer.class))
        .block();
        return result;
    }

    public ResponseEntity<Object> error_api(String module, String code) throws InterruptedException {
        if(module.equals("bo")) {
            String bo_response = webClient.get()
            .uri(bo_url + "error/{code}", code)
            .exchange()
            .flatMap(response -> response.bodyToMono(String.class))
            .block();
            String[] result = bo_response.split(" ");
            return new ResponseEntity<>(result[1], HttpStatus.valueOf(Integer.parseInt(result[0])));
        } else {
            if(code.equals("400"))
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST); 
            else if(code.equals("401"))
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED);
            else if(code.equals("403"))
                return new ResponseEntity<>(HttpStatus.FORBIDDEN.toString(), HttpStatus.FORBIDDEN); 
            else if(code.equals("404"))
                return new ResponseEntity<>(HttpStatus.NOT_FOUND.toString(), HttpStatus.NOT_FOUND); 
            else if(code.equals("500"))
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
            else if(code.equals("502"))
                return new ResponseEntity<>(HttpStatus.BAD_GATEWAY.toString(), HttpStatus.BAD_GATEWAY);
            else if(code.equals("503"))
                return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE.toString(), HttpStatus.SERVICE_UNAVAILABLE);
            else if(code.equals("504")) {
                Thread.sleep(3000);
                return new ResponseEntity<>(HttpStatus.GATEWAY_TIMEOUT.toString(), HttpStatus.GATEWAY_TIMEOUT);    
            }            
            else
                return new ResponseEntity<>("그런 에러는 안남", HttpStatus.OK);
        }
    }
}