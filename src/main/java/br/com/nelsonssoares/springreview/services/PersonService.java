package br.com.nelsonssoares.springreview.services;

import br.com.nelsonssoares.springreview.controllers.PersonController;
import br.com.nelsonssoares.springreview.models.Person;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonController.class.getName());


    public Person findById(@PathVariable("id") String id){

        logger.info("Finding person");
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Nelson");
        person.setLastName("Soares");
        person.setAddress("Rua 1");
        person.setGender("Male");

        return person;
    }

    public List<Person> findAll(){
        List<Person> persons = new ArrayList<Person>();

        for(int i =0 ; i < 8; i++){

            Person person = mockPerson(i);
            persons.add(person);
        }
        return persons;
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
        return person;
    }

    public Person update(Person person) {
        logger.info("Updating person: " + person);
        return person;
    }

    public void delete(String id) {
        logger.info("Deleting person with id: " + id);
    }
}
