package br.com.nelsonssoares.springreview.controllers;

import br.com.nelsonssoares.springreview.controllers.docs.PersonControllerInterface;
import br.com.nelsonssoares.springreview.domain.dtos.v1.PersonDTO;
import br.com.nelsonssoares.springreview.services.PersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.http.MediaType.*;

//@CrossOrigin Habilita o CORS de forma global
//@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping(value="api/person/v1", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, APPLICATION_YAML_VALUE})
@Tag(name = "People", description = "Endpoint for people management")
public class PersonController implements PersonControllerInterface {


    @Autowired
    private PersonService personService;


    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/{id}")
    @Override
    public PersonDTO findById(@PathVariable("id") Long id) {
        var person = personService.findById(id);
        person.setBirthDay(new Date());
        person.setPhoneNumber("+55 (11) 4002-8922");
        person.setSensitiveData("This is a sensitive data");
        return person;
    }


    @Override
    public List<PersonDTO> findAll(){
        return personService.findAll();
    }

    @CrossOrigin(origins = {"http://localhost:8080","http://localhost:4200"})
    @Override
    public PersonDTO create(@RequestBody PersonDTO person) {
        return personService.create(person);
    }


    @Override
    public PersonDTO update(@PathVariable("id") Long id, @RequestBody PersonDTO person) {
        return personService.update(id, person);
    }


    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/v2")
//    public PersonDTOV2 createV2(@RequestBody PersonDTOV2 person) {
//        return personService.createV2(person);
//    }

}
