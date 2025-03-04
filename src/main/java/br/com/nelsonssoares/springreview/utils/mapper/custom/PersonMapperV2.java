package br.com.nelsonssoares.springreview.utils.mapper.custom;

import br.com.nelsonssoares.springreview.domain.dtos.v2.PersonDTOV2;
import br.com.nelsonssoares.springreview.domain.models.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapperV2 {

    public PersonDTOV2 convertEntityToDTO(Person person){
        PersonDTOV2 dto = new PersonDTOV2();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setAddress(person.getAddress());
        dto.setGender(person.getGender());
        dto.setBirthDate(new Date());
        return dto;
    }

    public PersonDTOV2 convertDTOToEntity(PersonDTOV2 person){
        PersonDTOV2 dto = new PersonDTOV2();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setAddress(person.getAddress());
        dto.setGender(person.getGender());
//        dto.setBirthDate(new Date());
        return dto;
    }
}
