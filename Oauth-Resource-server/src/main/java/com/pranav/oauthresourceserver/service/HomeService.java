package com.pranav.oauthresourceserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HomeService {
    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BookService bookService;

    public Map<String, Long> getTopTilesMap() {
        Map<String, Long> map = new HashMap<>();
        map.put("Total Users", userService.getActiveUserTotalCount());
        map.put("Total Categories", categoryService.getTotalCount());
        map.put("Total Books", bookService.getTotalCount());
        map.put("Total IssuedBooks", bookService.getTotalIssuedBooks());
        return map;
    }
}
