package com.bigdata.dis.sdk.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String get(){
        return "[GET] Hello world!!";
    }

    @PostMapping
    public String post(){
        return "[POST] Hello world!!";
    }

    @DeleteMapping
    public String delete(){
        return "[DELETE] Hello world!!";
    }

    @PutMapping
    public String put(){
        return "[PUT] Hello world!!";
    }
}
