package com.wiliam.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: liwiliam
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/demo")
    public String demo() {
        return "success";

    }
}
