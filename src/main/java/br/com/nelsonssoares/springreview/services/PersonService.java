package br.com.nelsonssoares.springreview.services;

import br.com.nelsonssoares.springreview.controllers.PersonController;
import br.com.nelsonssoares.springreview.domain.dtos.v1.PersonDTO;
import br.com.nelsonssoares.springreview.domain.models.Person;
import br.com.nelsonssoares.springreview.domain.repositories.PersonRepository;
import br.com.nelsonssoares.springreview.exceptions.RequiredObjectIsNullException;
import br.com.nelsonssoares.springreview.exceptions.ResourceNotFoundException;
import br.com.nelsonssoares.springreview.utils.mapper.custom.PersonMapperV2;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.atomic.AtomicLong;

import static br.com.nelsonssoares.springreview.utils.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


//@RequiredArgsConstructor
@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(PersonController.class.getName());

    @Autowired
    private PersonMapperV2 converter;

    @Autowired
    private PersonRepository repository ;

    @Autowired
    PagedResourcesAssembler <PersonDTO> assembler;

    public PersonDTO findById(@PathVariable("id") Long id){

        logger.info("Finding person");

        var person = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        var dto = parseObject(person, PersonDTO.class);
        // Adiciona link HATEOAS para o método findById, com o id do objeto
        // e o tipo de requisição
        addHateoasLinks(dto);

        return dto;

        //return Optional.ofNullable(parseObject(repository.findById(id), PersonDTO.class));
                //.orElseThrow(() -> new ResourceNotFoundException("No person found"));
    }

    public PagedModel<EntityModel<PersonDTO>> findAll(Pageable pageable) {

        var people = repository.findAll(pageable);

        return buildPagedModel(pageable, people);
    }


    public PagedModel<EntityModel<PersonDTO>> findAByName(String firstName, Pageable pageable) {

        var people = repository.findPeopleByName(firstName,pageable);

        return buildPagedModel(pageable, people);
    }

    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("First Name" + i);
        person.setLastName("Last Name" + i);
        person.setAddress("Some Address in Brasil" + i);
        person.setGender("Male");
        return person;
    }

    public PersonDTO create(PersonDTO person) {

        if(person == null) throw new RequiredObjectIsNullException();

        logger.info("Creating person: " + person);
        var entity = parseObject(person, Person.class);
        //repository.save(entity);
        var dto = parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public PersonDTO update(Long id, PersonDTO person) {

        if(person == null) throw new RequiredObjectIsNullException();

        logger.info("Updating person: " + person.toString());
        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No person found"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var dto = parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    @Transactional
    public PersonDTO disablePerson(Long id) {
        logger.info("Desabling a person with id: " + id);
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No person found"));
        repository.disablePerson(id);

        var entity = repository.findById(id).get();

        var dto = parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting person with id: " + id);
        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No person found"));
        repository.deleteById(entity.getId());
    }



    // metodo para adicionar links HATEOAS ao response, utilizando cada metodo do controller

    private static void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(PersonController.class).findAll(1,12,"asc")).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto.getId(), dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).disablePerson(dto.getId())).withRel("disable").withType("PATCH"));
        dto.add(linkTo(methodOn(PersonController.class).findAllByName(dto.getFirstName(), 1, 12, "asc")).withRel("findAByName").withType("GET"));
    }

    private PagedModel<EntityModel<PersonDTO>> buildPagedModel(Pageable pageable, Page<Person> people) {
        var peopleWithLinks = people.map(
                person -> {
                    var dto = parseObject(person, PersonDTO.class);
                    addHateoasLinks(dto);
                    return dto;
                }
        );
        // Adiciona link HATEOAS para o método findAll, com o número da página, o tamanho da página e a ordenação
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().toString())).withSelfRel();

        return assembler.toModel(peopleWithLinks, link);
    }

}
//    public PersonDTOV2 createV2(PersonDTOV2 person) {
//        logger.info("Creating person V2: " + person.toString());
//        var entity = parseObject(person, Person.class);
//        //repository.save(entity);
//        return converter.convertEntityToDTO(repository.save(entity));
//    }