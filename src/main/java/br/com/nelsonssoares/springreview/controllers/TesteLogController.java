package br.com.nelsonssoares.springreview.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/log")
public class TesteLogController {

    private Logger logger = LoggerFactory.getLogger(TesteLogController.class.getName());

    @GetMapping
    public String testeLog() {

        logger.info("this is a info message");
        logger.warn("this is a warn message");
        logger.error("this is a error message");
        logger.debug("this is a debug message");

        return "Teste Log";
    }
}
