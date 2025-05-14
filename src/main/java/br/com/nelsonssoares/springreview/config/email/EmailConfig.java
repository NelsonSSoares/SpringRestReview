package br.com.nelsonssoares.springreview.config.email;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.email")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EmailConfig {

    private String host;
    private String port;
    private String username;
    private String password;
    private String from;
    private boolean ssl;



}
