package br.com.nelsonssoares.springreview.config.contentNeg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Value("${cors.originPatterns:default}")
    private String corsOriginPatterns = "";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        var aloowedOrigins = corsOriginPatterns.split(",");
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")//"*"
                .allowedOrigins(aloowedOrigins)
                .allowCredentials(true);
                //.maxAge(3600);
    }

    //PARAMETROS VIA HEADER ACCEPT

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

        configurer.favorParameter(false)
                .ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("yaml", MediaType.APPLICATION_YAML);

    }


}
//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        // via EXTENSION  _.json ou _.xml deprecated in Spring 2.6 not work/ NAO FUNCIONA MAIS
//
//        // METODO A SER IMPLEMENTADO
//        // VIA QUERY PARAM /api/person/v1/1?mediaType=xml OU /api/person/v1/1?mediaType=json
//
//        configurer.favorParameter(true)
//                .parameterName("mediaType")
//                .ignoreAcceptHeader(true)
//                .useRegisteredExtensionsOnly(false)
//                .defaultContentType(MediaType.APPLICATION_JSON)
//                .mediaType("json", MediaType.APPLICATION_JSON)
//                .mediaType("xml", MediaType.APPLICATION_XML);
//
//    }