package br.com.nelsonssoares.springreview.controllers.withjson;

import br.com.nelsonssoares.springreview.config.tests.TestConfigs;
import br.com.nelsonssoares.springreview.integrationtests.AbstractIntegrationTest;
import br.com.nelsonssoares.springreview.integrationtests.dto.PersonDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static PersonDTO personDTO;


    @BeforeAll
     static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        personDTO = new PersonDTO();
    }
    private void mockPerson(){

        personDTO.setFirstName("Richard");
        personDTO.setLastName("Nixon");
        personDTO.setAddress("White House");
        personDTO.setGender("Male");
    }

    @Order(1)
    @Test
    void create() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.HEADER_PARAM_ORIGIN_LOCAL)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)

                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(personDTO)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonDTO created = objectMapper.readValue(content, PersonDTO.class);
        personDTO = created;

        assertNotNull(created.getId());
        assertNotNull(created.getFirstName());
        assertNotNull(created.getLastName());
        assertNotNull(created.getAddress());
        assertNotNull(created.getGender());

        assertTrue(created.getId() > 0);

        assertEquals("Richard", created.getFirstName());
        assertEquals("Nixon", created.getLastName());
        assertEquals("White House" , created.getAddress());
        assertEquals("Male", created.getGender());
    }

    @Order(2)
    @Test
    void createWithWrongOrigin() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.HEADER_PARAM_UNKOWN_ORIGIN)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)

                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(personDTO)
                .when()
                .post()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request",content);
    }

    @Order(3)
    @Test
    void findById() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.HEADER_PARAM_ORIGIN_LOCAL)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)

                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", personDTO.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonDTO created = objectMapper.readValue(content, PersonDTO.class);
        personDTO = created;


        assertNotNull(created.getFirstName());
        assertNotNull(created.getLastName());
        assertNotNull(created.getAddress());
        assertNotNull(created.getGender());

        assertTrue(created.getId() > 0);

        assertEquals("Richard", created.getFirstName());
        assertEquals("Nixon", created.getLastName());
        assertEquals("White House" , created.getAddress());
        assertEquals("Male", created.getGender());


    }

    @Order(4)
    @Test
    void findByIdWithWrongOrigin() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.HEADER_PARAM_UNKOWN_ORIGIN)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)

                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", personDTO.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request",content);
    }


}