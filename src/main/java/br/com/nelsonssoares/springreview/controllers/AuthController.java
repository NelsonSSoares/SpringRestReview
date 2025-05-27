package br.com.nelsonssoares.springreview.controllers;

import br.com.nelsonssoares.springreview.domain.auth.dto.AccountCredentialsDTO;
import br.com.nelsonssoares.springreview.domain.auth.dto.TokenDTO;
import br.com.nelsonssoares.springreview.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Endpoints for user authentication")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;


    @Operation(summary = "Authenticates a user and returns a token")
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody AccountCredentialsDTO credentials){
        if (credentialsIsInvalid(credentials)) 
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client Request");

        var token = service.signin(credentials);
        if (token == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");

        return ResponseEntity.ok().body(token);
    }

    private static boolean credentialsIsInvalid(AccountCredentialsDTO credentials) {
        return credentials == null ||
                StringUtils.isBlank(credentials.getUsername()) ||
                StringUtils.isBlank(credentials.getPassword());
    }
}
