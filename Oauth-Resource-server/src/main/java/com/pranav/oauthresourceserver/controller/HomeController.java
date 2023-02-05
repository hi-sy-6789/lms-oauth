package com.pranav.oauthresourceserver.controller;

import com.pranav.oauthresourceserver.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Map<String, Long> homePage() {
        return homeService.getTopTilesMap();
    }
}
