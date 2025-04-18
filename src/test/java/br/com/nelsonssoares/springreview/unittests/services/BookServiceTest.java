package br.com.nelsonssoares.springreview.unittests.services;


import br.com.nelsonssoares.springreview.domain.dtos.v1.BookDTO;
import br.com.nelsonssoares.springreview.domain.models.Book;
import br.com.nelsonssoares.springreview.domain.repositories.BooksRepository;
import br.com.nelsonssoares.springreview.exceptions.RequiredObjectIsNullException;
import br.com.nelsonssoares.springreview.mocks.MockBook;
import br.com.nelsonssoares.springreview.services.BooksService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    MockBook input;

    @InjectMocks
    BooksService services;

    @Mock
    BooksRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    // Tests here
    @Test
    void findById() {
        Book book = input.mockEntity(1);
        book.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        var result = services.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("self")
                        && link.getHref().equals("/api/book/v1/1") && link.getType().equals("GET"))
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("findAll")
                        && link.getHref().equals("/api/books/v1/") && link.getType().equals("GET"))
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("create")
                        && link.getHref().equals("/api/books/v1") && link.getType().equals("POST"))
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("update")
                        && link.getHref().equals("/api/books/v1/1") && link.getType().equals("PUT"))
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("delete")
                        && link.getHref().equals("/api/books/v1/1") && link.getType().equals("DELETE"))
        );

        assertEquals("Author Test1", result.getAuthor());
        assertEquals("Title Test1", result.getTitle());


    }

    @Test
    void create() {
        Book books = input.mockEntity(1);
        Book persisted = books;
        persisted.setId(1L);

        BookDTO dto = input.mockDTO(1);


        when(repository.save(any(Book.class))).thenReturn(persisted);


        var result = services.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("self")
                        && link.getHref().equals("/api/books/v1/1") && link.getType().equals("GET"))
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("findAll")
                        && link.getHref().equals("/api/books/v1/") && link.getType().equals("GET"))
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("create")
                        && link.getHref().equals("/api/books/v1") && link.getType().equals("POST"))
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("update")
                        && link.getHref().equals("/api/books/v1/1") && link.getType().equals("PUT"))
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("delete")
                        && link.getHref().equals("/api/books/v1/1") && link.getType().equals("DELETE"))
        );

        assertEquals("Author Test1", result.getAuthor());
        assertEquals("Title Test1", result.getTitle());
    }

    @Test
    void update() {
        Book books = input.mockEntity(1);
        Book persisted = books;
        persisted.setId(1L);

        BookDTO dto = input.mockDTO(1);

        when(repository.findById(1L)).thenReturn(Optional.of(books));
        when(repository.save(books)).thenReturn(persisted);

        var result = services.update(1L,dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("self")
                        && link.getHref().equals("/api/books/v1/1") && link.getType().equals("GET"))
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("findAll")
                        && link.getHref().equals("/api/books/v1/") && link.getType().equals("GET"))
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("create")
                        && link.getHref().equals("/api/books/v1") && link.getType().equals("POST"))
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("update")
                        && link.getHref().equals("/api/books/v1/1") && link.getType().equals("PUT"))
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("delete")
                        && link.getHref().equals("/api/books/v1/1") && link.getType().equals("DELETE"))
        );

        assertEquals("Author Test1", result.getAuthor());
        assertEquals("Title Test1", result.getTitle());
    }

    @Test
    void delete() {
        Book book = input.mockEntity(1);
        book.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(book));
        services.delete(1L);
        //verifica se o método findById foi chamado uma vez
        verify(repository, times(1)).findById(anyLong());
        //verifica se o método delete foi chamado uma vez e any verifica se foi passado qualquer objeto do tipo Person
        //verify(repository, times(1)).delete(any(Person.class));
        //verifyNoMoreInteractions(repository);
    }

    @Test
    @Disabled
    void findAll() {

        List<Book> list = input.mockEntityList();
        when(repository.findAll()).thenReturn(list);
        List<BookDTO> books = services.findAll();

        assertNotNull(books);
        assertEquals(14, books.size());

        var books1 = books.get(1);

        assertNotNull(books1);
        assertNotNull(books1.getId());
        assertNotNull(books1.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("self")
                        && link.getHref().equals("/api/books/v1/1") && link.getType().equals("GET"))
        );

        assertNotNull(books1.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("findAll")
                        && link.getHref().equals("/api/books/v1/") && link.getType().equals("GET"))
        );

        assertNotNull(books1.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("create")
                        && link.getHref().equals("/api/books/v1") && link.getType().equals("POST"))
        );

        assertNotNull(books1.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("update")
                        && link.getHref().equals("/api/books/v1/1") && link.getType().equals("PUT"))
        );

        assertNotNull(books1.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("delete")
                        && link.getHref().equals("/api/books/v1/1") && link.getType().equals("DELETE"))
        );
        assertEquals("Author Test1", books1.getAuthor());
        assertEquals("Title Test1", books1.getTitle());

        var books4= books.get(4);

        assertNotNull(books4);
        assertNotNull(books4.getId());
        assertNotNull(books4.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("self")
                        && link.getHref().equals("/api/books/v1/1") && link.getType().equals("GET"))
        );

        assertNotNull(books4.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("findAll")
                        && link.getHref().equals("/api/books/v1/") && link.getType().equals("GET"))
        );

        assertNotNull(books4.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("create")
                        && link.getHref().equals("/api/books/v1") && link.getType().equals("POST"))
        );

        assertNotNull(books4.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("update")
                        && link.getHref().equals("/api/books/v1/1") && link.getType().equals("PUT"))
        );

        assertNotNull(books4.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("delete")
                        && link.getHref().equals("/api/books/v1/1") && link.getType().equals("DELETE"))
        );

        assertEquals("Author Test4", books4.getAuthor());
        assertEquals("Title Test4", books4.getTitle());
    }

    @Test
    public  void testUpdateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> services.update(1L,null));

        String expectedMessage = "it is not allowed to persist a null object";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public  void testCreateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> services.create(null));

        String expectedMessage = "it is not allowed to persist a null object";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}
