package bachelors.flycastserver.controllers;

import bachelors.flycastserver.controllers.apis.AuthenticationAPI;
import bachelors.flycastserver.controllers.responses.JwtSignUpResponse;
import bachelors.flycastserver.controllers.responses.JwtSignInResponse;
import bachelors.flycastserver.domain.dtos.SignInDTO;
import bachelors.flycastserver.domain.dtos.ClientDTO;
import bachelors.flycastserver.domain.dtos.SignUpDTO;
import bachelors.flycastserver.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController implements AuthenticationAPI {

    private final AuthService authService;

    @Autowired
    public AuthenticationController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<JwtSignInResponse> signIn(SignInDTO request) {
        return ResponseEntity.ok(authService.signIn(request));
    }

    @Override
    public ResponseEntity<JwtSignInResponse> signUp(SignUpDTO request) {
        return ResponseEntity.ok(authService.signUp(request));
    }
}
