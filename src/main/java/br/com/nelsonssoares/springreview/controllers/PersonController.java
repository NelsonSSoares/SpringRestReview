package br.com.nelsonssoares.springreview.controllers;

import br.com.nelsonssoares.springreview.controllers.docs.PersonControllerInterface;
import br.com.nelsonssoares.springreview.domain.dtos.v1.PersonDTO;
import br.com.nelsonssoares.springreview.services.PersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


  //  @CrossOrigin(origins = "http://localhost:8080") // Habilita o CORS apenas para esse endpoint
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
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        var pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
        return ResponseEntity.ok(personService.findAll(pageable));
    }

    //@CrossOrigin(origins = {"http://localhost:8080","http://localhost:4200"})
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

    @Override
    public PersonDTO disablePerson(@PathVariable("id") Long id) {
        return personService.disablePerson(id);
    }
    @Override
    public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findAllByName(
            @PathVariable(value = "firstName") String firstName,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        var pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
        return ResponseEntity.ok(personService.findAByName(firstName, pageable));
    }


    @PostMapping(value = "/massCreation")
    @Override
    public List<PersonDTO> massCreation(@RequestParam("file") MultipartFile file) {
        try {
            return personService.massCreation(file);
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }
    }

//    @PostMapping("/v2")
//    public PersonDTOV2 createV2(@RequestBody PersonDTOV2 person) {
//        return personService.createV2(person);
//    }

}
