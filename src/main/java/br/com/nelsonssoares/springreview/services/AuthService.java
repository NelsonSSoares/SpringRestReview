package br.com.nelsonssoares.springreview.services;

import br.com.nelsonssoares.springreview.domain.auth.dto.AccountCredentialsDTO;
import br.com.nelsonssoares.springreview.domain.auth.dto.TokenDTO;
import br.com.nelsonssoares.springreview.domain.repositories.UserRepository;
import br.com.nelsonssoares.springreview.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository repository;


    public ResponseEntity<TokenDTO> signin (AccountCredentialsDTO credentials){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getUsername(),
                        credentials.getPassword()
                )
        );
        var user = repository.findByUsername(credentials.getUsername());

        if(user == null) throw new UsernameNotFoundException("Username not found");

        var token = jwtTokenProvider.createAccessToken(user.getUsername(), user.getRoles());
        return ResponseEntity.ok(token);
    }

    public ResponseEntity<TokenDTO> refreshToken (String username, String refreshToken){
        TokenDTO token;
        var user = repository.findByUsername(username);
        if(user != null) {
            token = jwtTokenProvider.refreshToken(refreshToken);
        }else {
            throw new UsernameNotFoundException("Username not found");
        }

        return ResponseEntity.ok(token);

    }
}
