package br.com.nelsonssoares.springreview.domain.dtos.request;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EmailRequestDTO {

    private String to;
    private String subject;
    private String body;
}
