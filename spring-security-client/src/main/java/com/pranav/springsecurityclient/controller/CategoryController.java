package com.pranav.springsecurityclient.controller;

import com.pranav.springsecurityclient.model.CategoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.reactive.function.client.WebClient;

import javax.validation.Valid;


import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
@RequestMapping(value = "/api/category")
public class CategoryController {
    @Autowired
    private WebClient webClient;

    @GetMapping(value = {"/", "/list"})
    public String allCategory(
            @RegisteredOAuth2AuthorizedClient("api-client-authorization-code")
            OAuth2AuthorizedClient client){
        return this.webClient
                .get()
                .uri("http://127.0.0.1:8090/api/category/")
                .attributes(oauth2AuthorizedClient(client))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @PostMapping(value = {"/add", "/add/"})
    public String addCategory(
            @RegisteredOAuth2AuthorizedClient("api-client-authorization-code")
            OAuth2AuthorizedClient client, @RequestBody @Valid CategoryModel categoryModel) {
        return this.webClient
                .post()
                .uri("http://127.0.0.1:8090/api/category/add")
                .attributes(oauth2AuthorizedClient(client))
                .bodyValue(categoryModel)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @PutMapping(value = "/edit/{id}")
    public String editCategory(
            @RegisteredOAuth2AuthorizedClient("api-client-authorization-code")
            OAuth2AuthorizedClient client, @RequestBody @Valid CategoryModel categoryModel) {
        return this.webClient
                .put()
                .uri("http://127.0.0.1:8090/api/category/edit/{id}")
                .bodyValue(categoryModel)
                .attributes(oauth2AuthorizedClient(client))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
    @DeleteMapping(value="/remove/{id}")
    public String deleteCategory(
            @RegisteredOAuth2AuthorizedClient("api-client-authorization-code")
            OAuth2AuthorizedClient client) {
        return this.webClient
                .delete()
                .uri("http://127.0.0.1:8090/api/category/remove/{id}")
                .attributes(oauth2AuthorizedClient(client))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
