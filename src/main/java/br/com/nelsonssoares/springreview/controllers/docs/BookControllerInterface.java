package br.com.nelsonssoares.springreview.controllers.docs;

import br.com.nelsonssoares.springreview.domain.dtos.v1.BookDTO;
import br.com.nelsonssoares.springreview.domain.dtos.v1.PersonDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.*;

public interface BookControllerInterface {
    @Operation(summary = "Find a BookDTO",
            tags = {"People"}, responses = {
            @ApiResponse(description = "Find a specific person by tour ID", responseCode = "200", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = BookDTO.class)),
                    @Content(mediaType = APPLICATION_XML_VALUE),
                    @Content(mediaType = APPLICATION_YAML_VALUE)
            }),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "People not found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
    })
    @GetMapping("/{id}")
    BookDTO findById(@PathVariable("id") Long id);

    @Operation(summary = "Find all books",
            tags = {"Books"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = BookDTO.class))),
                    @Content(mediaType = APPLICATION_XML_VALUE),
                    @Content(mediaType = APPLICATION_YAML_VALUE)
            }),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "People not found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
    })
    @GetMapping
    List<BookDTO> findAllBooks();

    @Operation(summary = "create successfully",
            tags = {"People"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = BookDTO.class))),
                    @Content(mediaType = APPLICATION_XML_VALUE),
                    @Content(mediaType = APPLICATION_YAML_VALUE)
            }),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "People not found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
    })
    @PostMapping
    BookDTO create(@RequestBody BookDTO person);

    @Operation(summary = "update successfully",
            tags = {"People"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = BookDTO.class))),
                    @Content(mediaType = APPLICATION_XML_VALUE),
                    @Content(mediaType = APPLICATION_YAML_VALUE)
            }),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "People not found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
    })
    @PutMapping("/{id}")
    BookDTO update(@PathVariable("id") Long id, @RequestBody BookDTO person);

    @Operation(summary = "Find all people",
            tags = {"People"}, responses = {
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "People not found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
    })
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable("id") Long id);
}
