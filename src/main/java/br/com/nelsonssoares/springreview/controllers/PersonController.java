package br.com.nelsonssoares.springreview.controllers;

import br.com.nelsonssoares.springreview.models.Person;
import br.com.nelsonssoares.springreview.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/person", produces = "application/json", consumes = "application/json")
public class PersonController {


    @Autowired
    private PersonService personService;

    @GetMapping("/{id}")
    public Person findById(@PathVariable("id") String id) {
        return personService.findById(id);
    }

    @GetMapping
    public List<Person> findAll(){
        return personService.findAll();
    }

    @PostMapping
    public Person create(@RequestBody Person person) {
        return personService.create(person);
    }

    @PutMapping("/{id}")
    public Person update(@PathVariable("id") String id, @RequestBody Person person) {
        return personService.update(person);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        personService.delete(id);
    }

}
