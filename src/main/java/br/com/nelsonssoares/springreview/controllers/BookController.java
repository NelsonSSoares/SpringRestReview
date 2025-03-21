package br.com.nelsonssoares.springreview.controllers;

import br.com.nelsonssoares.springreview.controllers.docs.BookControllerInterface;
import br.com.nelsonssoares.springreview.domain.dtos.v1.BookDTO;
import br.com.nelsonssoares.springreview.domain.dtos.v1.PersonDTO;
import br.com.nelsonssoares.springreview.services.BooksService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping(value="api/books/v1", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, APPLICATION_YAML_VALUE})
@Tag(name = "Books", description = "Endpoint for Books management")
public class BookController implements BookControllerInterface {

    @Autowired
    private BooksService booksService;


    @Override
    public BookDTO findById(@PathVariable("id") Long id) {
        return booksService.findById(id);
    }



    @Override
    public List<BookDTO> findAllBooks(){
        return booksService.findAll();
    }



    @Override
    public BookDTO create(@RequestBody BookDTO book) {
        return booksService.create(book);
    }



    @Override
    public BookDTO update(@PathVariable("id") Long id, @RequestBody BookDTO book) {
        return booksService.update(id, book);
    }



    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        booksService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
