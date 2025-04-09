package br.com.nelsonssoares.springreview.controllers.withYaml.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.type.TypeFactory;

public class YAMLMapper implements ObjectMapper {

    private com.fasterxml.jackson.databind.ObjectMapper mapper;

    protected TypeFactory typeFactory;

    public YAMLMapper() {
        this.mapper = new com.fasterxml.jackson.dataformat.yaml.YAMLMapper(new YAMLFactory())
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.typeFactory = TypeFactory.defaultInstance();
    }

    @Override
    public Object deserialize(ObjectMapperDeserializationContext context) {

        var data = context.getDataToDeserialize().asString();
        Class<?> type = (Class<?>) context.getType();

        try {
            return mapper.readValue(data, type);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error deserializing YAML content ", e);
        }
    }

    @Override
    public Object serialize(ObjectMapperSerializationContext context) {
        try {
            return mapper.writeValueAsString(context.getObjectToSerialize());
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error serializing YAML content ", e);
        }
    }
}
