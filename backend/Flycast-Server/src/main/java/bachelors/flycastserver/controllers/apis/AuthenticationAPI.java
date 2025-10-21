package bachelors.flycastserver.controllers.apis;

import bachelors.flycastserver.controllers.responses.JwtSignInResponse;
import bachelors.flycastserver.domain.dtos.SignInDTO;
import bachelors.flycastserver.domain.dtos.SignUpDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
public interface AuthenticationAPI {

    @PostMapping("/sign-in")
    ResponseEntity<JwtSignInResponse> signIn(@Valid @RequestBody SignInDTO request);

    @PostMapping("/sign-up")
    ResponseEntity<JwtSignInResponse> signUp(@Valid @RequestBody SignUpDTO request);
}
