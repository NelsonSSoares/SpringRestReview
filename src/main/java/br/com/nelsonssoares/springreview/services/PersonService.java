package br.com.nelsonssoares.springreview.services;

import br.com.nelsonssoares.springreview.controllers.PersonController;
import br.com.nelsonssoares.springreview.domain.dto.PersonDTO;
import br.com.nelsonssoares.springreview.exceptions.ResourceNotFoundException;
import br.com.nelsonssoares.springreview.domain.models.Person;
import br.com.nelsonssoares.springreview.domain.repositories.PersonRepository;
import br.com.nelsonssoares.springreview.utils.mapper.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static br.com.nelsonssoares.springreview.utils.mapper.ObjectMapper.parseListObjects;
import static br.com.nelsonssoares.springreview.utils.mapper.ObjectMapper.parseObject;


//@RequiredArgsConstructor
@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(PersonController.class.getName());



    @Autowired
    private PersonRepository repository ;

    public Optional<PersonDTO> findById(@PathVariable("id") Long id){

        logger.info("Finding person");

        var person = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        return Optional.ofNullable(parseObject(person, PersonDTO.class));

        //return Optional.ofNullable(parseObject(repository.findById(id), PersonDTO.class));
                //.orElseThrow(() -> new ResourceNotFoundException("No person found"));
    }

    public List<PersonDTO> findAll(){

       return parseListObjects(repository.findAll(), PersonDTO.class);
               //.orElseThrow(() -> new ResourceNotFoundException("No person found"));
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
        logger.info("Creating person: " + person);
        var entity = parseObject(person, Person.class);
        //repository.save(entity);
        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public PersonDTO update(Long id, PersonDTO person) {
        logger.info("Updating person: " + person);
        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No person found"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public void delete(Long id) {
        logger.info("Deleting person with id: " + id);
        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No person found"));
        repository.deleteById(entity.getId());
    }
}
