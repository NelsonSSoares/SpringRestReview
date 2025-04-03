package br.com.nelsonssoares.springreview.config.tests;

public interface TestConfigs {
    int SERVER_PORT = 8080;
    String HEADER_PARAM_AUTHORIZATION = "Authorization";
    String HEADER_PARAM_ORIGIN = "Origin";
    String HEADER_PARAM_ORIGIN_LOCAL = "http://localhost:8080";
    String HEADER_PARAM_ORIGIN_ANGULAR = "http://localhost:4200";
    String HEADER_PARAM_UNKOWN_ORIGIN = "http://localhost:8888";

}
