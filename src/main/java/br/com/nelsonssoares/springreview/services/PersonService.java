package br.com.nelsonssoares.springreview.services;

import br.com.nelsonssoares.springreview.controllers.PersonController;
import br.com.nelsonssoares.springreview.models.Person;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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
}
