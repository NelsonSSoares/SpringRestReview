package br.com.nelsonssoares.springreview.domain.dtos.v1;

import br.com.nelsonssoares.springreview.utils.serializer.GenderSerializer;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

// Define a ordem dos campos
//@JsonPropertyOrder({"id", "first_name", "last_name", "address","gender"})
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Relation é uma anotação do Spring HATEOAS que define o nome da coleção
@Relation(collectionRelation = "people")
public class PersonDTO  extends RepresentationModel<PersonDTO> implements Serializable  {
                        //superclasse para adicionar links HATEOAS
    private static final long serialVersionUID = 1L;


    private Long id;

    //@JsonProperty("first_name")
    private String firstName;
    // Retorna apenas se não for nulo
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String phoneNumber;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDay;

    private String address;

    //@JsonIgnore
    // GenderSerializer é uma classe que faz a abreveação do gênero
    @JsonSerialize(using = GenderSerializer.class)
    private String gender;

    // Filtra os campos que não devem ser exibidos,
    // com a configuração feita em ObjectMapperConfig
    @JsonFilter("PersonFilter")
    private String sensitiveData;


    private Boolean enabled;


}
