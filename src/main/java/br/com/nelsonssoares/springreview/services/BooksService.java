package br.com.nelsonssoares.springreview.services;

import br.com.nelsonssoares.springreview.controllers.BookController;
import br.com.nelsonssoares.springreview.controllers.PersonController;
import br.com.nelsonssoares.springreview.domain.dtos.v1.BookDTO;
import br.com.nelsonssoares.springreview.domain.dtos.v1.PersonDTO;
import br.com.nelsonssoares.springreview.domain.models.Book;
import br.com.nelsonssoares.springreview.domain.models.Person;
import br.com.nelsonssoares.springreview.domain.repositories.BooksRepository;
import br.com.nelsonssoares.springreview.domain.repositories.PersonRepository;
import br.com.nelsonssoares.springreview.exceptions.RequiredObjectIsNullException;
import br.com.nelsonssoares.springreview.exceptions.ResourceNotFoundException;
import br.com.nelsonssoares.springreview.utils.mapper.custom.PersonMapperV2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static br.com.nelsonssoares.springreview.utils.mapper.ObjectMapper.parseListObjects;
import static br.com.nelsonssoares.springreview.utils.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BooksService {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(BookController.class.getName());


    @Autowired
    private BooksRepository repository ;

    public BookDTO findById(@PathVariable("id") Long id){

        logger.info("Finding book");

        var book = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        var dto = parseObject(book, BookDTO.class);
        // Adiciona link HATEOAS para o método findById, com o id do objeto
        // e o tipo de requisição
        addHateoasLinks(dto);

        return dto;

        //return Optional.ofNullable(parseObject(repository.findById(id), PersonDTO.class));
        //.orElseThrow(() -> new ResourceNotFoundException("No person found"));
    }

    public List<BookDTO> findAll(){

        var dtos = parseListObjects(repository.findAll(), BookDTO.class);
        //.orElseThrow(() -> new ResourceNotFoundException("No person found"));
        // dtos.forEach(dto -> addHateoasLinks(dto));
        // ou this
        dtos.forEach(BooksService::addHateoasLinks);
        return dtos;
    }


    public BookDTO create(BookDTO person) {

        if(person == null) throw new RequiredObjectIsNullException();

        var entity = parseObject(person, Book.class);
        //repository.save(entity);
        var dto = parseObject(repository.save(entity), BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public BookDTO update(Long id, BookDTO book) {

        if(book == null) throw new RequiredObjectIsNullException();

        logger.info("Updating person: " + book.toString());
        Book entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No books found"));

        var dto = parseObject(repository.save(entity), BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting person with id: " + id);
        Book entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No book found"));
        repository.deleteById(entity.getId());
    }
    // metodo para adicionar links HATEOAS ao response, utilizando cada metodo do controller

    private static void addHateoasLinks(BookDTO dto) {
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(BookController.class).findAllBooks()).withRel("findAllBooks").withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(BookController.class).update(dto.getId(), dto)).withRel("update").withType("PUT"));
    }
}
