package br.com.nelsonssoares.springreview.services;

import br.com.nelsonssoares.springreview.controllers.PersonController;
import br.com.nelsonssoares.springreview.domain.dtos.v1.PersonDTO;
import br.com.nelsonssoares.springreview.domain.models.Person;
import br.com.nelsonssoares.springreview.domain.repositories.PersonRepository;
import br.com.nelsonssoares.springreview.exceptions.ResourceNotFoundException;
import br.com.nelsonssoares.springreview.utils.mapper.custom.PersonMapperV2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static br.com.nelsonssoares.springreview.utils.mapper.ObjectMapper.parseListObjects;
import static br.com.nelsonssoares.springreview.utils.mapper.ObjectMapper.parseObject;
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

    public List<PersonDTO> findAll(){

       var dtos = parseListObjects(repository.findAll(), PersonDTO.class);
               //.orElseThrow(() -> new ResourceNotFoundException("No person found"));
        // dtos.forEach(dto -> addHateoasLinks(dto));
                    // ou this
        dtos.forEach(PersonService::addHateoasLinks);
        return dtos;
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
        logger.info("Creating person: " + person.toString());
        var entity = parseObject(person, Person.class);
        //repository.save(entity);
        var dto = parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public PersonDTO update(Long id, PersonDTO person) {
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
        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto.getId(), dto)).withRel("update").withType("PUT"));

    }

//    public PersonDTOV2 createV2(PersonDTOV2 person) {
//        logger.info("Creating person V2: " + person.toString());
//        var entity = parseObject(person, Person.class);
//        //repository.save(entity);
//        return converter.convertEntityToDTO(repository.save(entity));
//    }
}
