package com.bigdata.dis.sdk.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String get(){
        return "Hello world!!";
    }

    @PostMapping
    public String post(){
        return "Hello world!!";
    }

    @DeleteMapping
    public String delete(){
        return "Hello world!!";
    }

    @PutMapping
    public String put(){
        return "Hello world!!";
    }
}
