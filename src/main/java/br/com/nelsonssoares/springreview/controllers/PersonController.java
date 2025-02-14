package br.com.nelsonssoares.springreview.controllers;

import br.com.nelsonssoares.springreview.models.Person;
import br.com.nelsonssoares.springreview.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/person", produces = "application/json")
public class PersonController {


    @Autowired
    private PersonService personService;

    @PostMapping("/{id}")
    public Person findById(@PathVariable("id") String id) {
        return personService.findById(id);
    }

}
