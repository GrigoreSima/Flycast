package bachelors.flycastserver.services;

import bachelors.flycastserver.controllers.responses.JwtSignUpResponse;
import bachelors.flycastserver.controllers.responses.JwtSignInResponse;
import bachelors.flycastserver.domain.dtos.ClientDTO;
import bachelors.flycastserver.domain.dtos.SignInDTO;
import bachelors.flycastserver.domain.dtos.SignUpDTO;

public interface AuthService {
    JwtSignInResponse signIn(SignInDTO request);

    JwtSignInResponse signUp(SignUpDTO request);
}
