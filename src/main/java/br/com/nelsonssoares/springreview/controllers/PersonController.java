package br.com.nelsonssoares.springreview.controllers;

import br.com.nelsonssoares.springreview.domain.dto.PersonDTO;
import br.com.nelsonssoares.springreview.domain.models.Person;
import br.com.nelsonssoares.springreview.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/person", produces = "application/json")
public class PersonController {


    @Autowired
    private PersonService personService;

    @GetMapping("/{id}")
    public Optional<PersonDTO> findById(@PathVariable("id") Long id) {
        return personService.findById(id);
    }

    @GetMapping()
    public List<PersonDTO> findAll(){
        return personService.findAll();
    }

    @PostMapping
    public PersonDTO create(@RequestBody PersonDTO person) {
        return personService.create(person);
    }

    @PutMapping("/{id}")
    public PersonDTO update(@PathVariable("id") Long id, @RequestBody PersonDTO person) {
        return personService.update(id, person);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
