package br.com.nelsonssoares.springreview.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsuportedMathOperation extends RuntimeException {


    public UnsuportedMathOperation(String message) {

        super(message);
    }
}
