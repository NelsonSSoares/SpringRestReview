package br.com.nelsonssoares.springreview.services;

import br.com.nelsonssoares.springreview.domain.models.Person;
import br.com.nelsonssoares.springreview.domain.repositories.PersonRepository;
import br.com.nelsonssoares.springreview.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

// TestInstance.Lifecycle.PER_CLASS cria uma única instância da classe de teste e a reutiliza para todos os métodos de teste da classe.
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    MockPerson input;

    @InjectMocks
    private PersonService services;

    @Mock
    PersonRepository repository;


    @BeforeEach
    void setUp() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Person person = input.mockEntity(1);
        person.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(person));

        var result = services.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("self")
                && link.getHref().equals("/api/person/v1/1") && link.getType().equals("GET"))
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("findAll")
                        && link.getHref().equals("/api/person/v1/") && link.getType().equals("GET"))
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("create")
                        && link.getHref().equals("/api/person/v1") && link.getType().equals("POST"))
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("update")
                        && link.getHref().equals("/api/person/v1/1") && link.getType().equals("PUT"))
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().equals("delete")
                        && link.getHref().equals("/api/person/v1/1") && link.getType().equals("DELETE"))
        );

        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Address Test1", result.getAddress());

    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findAll() {
    }
}