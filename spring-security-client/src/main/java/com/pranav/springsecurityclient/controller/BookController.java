package com.pranav.springsecurityclient.controller;

import com.pranav.springsecurityclient.model.BookModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import javax.validation.Valid;


import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
@RequestMapping(value = "/api/book")
public class BookController {

    @Autowired
    private WebClient webClient;

    @GetMapping(value = {"/", "/list"})
    public String allBooks(
            @RegisteredOAuth2AuthorizedClient("api-client-authorization-code")
            OAuth2AuthorizedClient client){
        return this.webClient
                .get()
                .uri("http://127.0.0.1:8090/api/book/")
                .attributes(oauth2AuthorizedClient(client))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @GetMapping(value = "/{id}/list")
    public String allBooksForACategory(
            @RegisteredOAuth2AuthorizedClient("api-client-authorization-code")
            OAuth2AuthorizedClient client){
        return this.webClient
                .get()
                .uri("http://127.0.0.1:8090/api/book/{id}/list")
                .attributes(oauth2AuthorizedClient(client))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @GetMapping(value = "/{id}/available")
    public String allAvailableBooksForACategory(
            @RegisteredOAuth2AuthorizedClient("api-client-authorization-code")
            OAuth2AuthorizedClient client){
        return this.webClient
                .get()
                .uri("http://127.0.0.1:8090/api/book/{id}/available")
                .attributes(oauth2AuthorizedClient(client))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @PostMapping(value = {"/add", "/add/"})
    public String addBook(
            @RegisteredOAuth2AuthorizedClient("api-client-authorization-code")
            OAuth2AuthorizedClient client, @RequestBody @Valid BookModel bookModel) {
        return this.webClient
                .post()
                .uri("http://127.0.0.1:8090/api/book/add")
                .attributes(oauth2AuthorizedClient(client))
                .bodyValue(bookModel)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @PutMapping(value = "/edit/{id}")
    public String editCategory(
            @RegisteredOAuth2AuthorizedClient("api-client-authorization-code")
            OAuth2AuthorizedClient client, @RequestBody @Valid BookModel bookModel) {
        return this.webClient
                .put()
                .uri("http://127.0.0.1:8090/api/book/edit/{id}")
                .bodyValue(bookModel)
                .attributes(oauth2AuthorizedClient(client))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @DeleteMapping(value = "/remove/{id}")
    public String deleteCategory(
            @RegisteredOAuth2AuthorizedClient("api-client-authorization-code")
            OAuth2AuthorizedClient client) {
        return this.webClient
                .delete()
                .uri("http://127.0.0.1:8090/api/book/remove/{id}")
                .attributes(oauth2AuthorizedClient(client))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
