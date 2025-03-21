package br.com.nelsonssoares.springreview.config.mapper;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
      ObjectMapper mapper = new ObjectMapper();
        // Add your custom configuration here
        // Filtra os campos que não devem ser exibidos
        SimpleFilterProvider filters = new SimpleFilterProvider()
                .addFilter("PersonFilter", SimpleBeanPropertyFilter
                        .serializeAllExcept("phoneNumber", "birthDay"));

        mapper.setFilterProvider(filters);
      return mapper;
    }
}
