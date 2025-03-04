package br.com.nelsonssoares.springreview.services;

import br.com.nelsonssoares.springreview.controllers.PersonController;
import br.com.nelsonssoares.springreview.exceptions.ResourceNotFoundException;
import br.com.nelsonssoares.springreview.domain.models.Person;
import br.com.nelsonssoares.springreview.domain.repositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;


//@RequiredArgsConstructor
@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(PersonController.class.getName());

    @Autowired
    private PersonRepository repository ;

    public Optional<Person> findById(@PathVariable("id") Long id){

        logger.info("Finding person");

        return repository.findById(id);
    }

    public List<Person> findAll(){
       return repository.findAll();
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

    public Person create(Person person) {
        logger.info("Creating person: " + person);
        return repository.save(person);
    }

    public Person update(Long id, Person person) {
        logger.info("Updating person: " + person);
        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No person found"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return repository.save(entity);
    }

    public void delete(Long id) {
        logger.info("Deleting person with id: " + id);
        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No person found"));
        repository.deleteById(entity.getId());
    }
}
