package com.pranav.springsecurityclient.controller;

import com.pranav.springsecurityclient.model.IssueModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import javax.validation.Valid;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
@RequestMapping(value = "/api/issue")
public class IssueController {
    @Autowired
    private WebClient webClient;

    @GetMapping(value = {"/", "/list"})
    public String all(
            @RegisteredOAuth2AuthorizedClient("api-client-authorization-code")
            OAuth2AuthorizedClient client){
        return this.webClient
                .get()
                .uri("http://127.0.0.1:8090/api/issue/")
                .attributes(oauth2AuthorizedClient(client))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @PostMapping(value="/save")
    public String saveIssue(
            @RegisteredOAuth2AuthorizedClient("api-client-authorization-code")
            OAuth2AuthorizedClient client, @RequestBody @Valid IssueModel issueModel) {
        return this.webClient
                .post()
                .uri("http://127.0.0.1:8090/api/issue/save")
                .attributes(oauth2AuthorizedClient(client))
                .bodyValue(issueModel)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @GetMapping(value = "/{id}/return/all")
    public String returnAll(
            @RegisteredOAuth2AuthorizedClient("api-client-authorization-code")
            OAuth2AuthorizedClient client){
        return this.webClient
                .get()
                .uri("http://127.0.0.1:8090/api/issue/{id}/return/all")
                .attributes(oauth2AuthorizedClient(client))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @PostMapping(value="/{id}/return")
    public String returnSelected(
            @RegisteredOAuth2AuthorizedClient("api-client-authorization-code")
            OAuth2AuthorizedClient client, @RequestBody @Valid IssueModel issueModel) {
        return this.webClient
                .post()
                .uri("http://127.0.0.1:8090/api/issue/{id}/return")
                .attributes(oauth2AuthorizedClient(client))
                .bodyValue(issueModel)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
