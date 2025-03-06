package br.com.nelsonssoares.springreview.controllers;

import br.com.nelsonssoares.springreview.domain.dtos.v1.PersonDTO;
import br.com.nelsonssoares.springreview.domain.dtos.v2.PersonDTOV2;
import br.com.nelsonssoares.springreview.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
@RequestMapping(value="api/person/v1", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
public class PersonController {


    @Autowired
    private PersonService personService;

    @GetMapping("/{id}")
    public PersonDTO findById(@PathVariable("id") Long id) {
        var person = personService.findById(id);
        person.setBirthDay(new Date());
        person.setPhoneNumber("+55 (11) 4002-8922");
        person.setSensitiveData("This is a sensitive data");
        return person;
    }

    @GetMapping
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

//    @PostMapping("/v2")
//    public PersonDTOV2 createV2(@RequestBody PersonDTOV2 person) {
//        return personService.createV2(person);
//    }

}
