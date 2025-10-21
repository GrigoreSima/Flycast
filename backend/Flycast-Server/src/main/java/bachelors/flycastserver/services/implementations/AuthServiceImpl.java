package bachelors.flycastserver.services.implementations;

import bachelors.flycastserver.controllers.responses.JwtSignInResponse;
import bachelors.flycastserver.domain.Client;
import bachelors.flycastserver.domain.dtos.SignInDTO;
import bachelors.flycastserver.domain.dtos.SignUpDTO;
import bachelors.flycastserver.repositories.ClientRepository;
import bachelors.flycastserver.services.AuthService;
import bachelors.flycastserver.services.JwtService;
import bachelors.flycastserver.utils.mappers.ClientMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final ClientRepository clientRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, ClientRepository clientRepository,
                           JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.clientRepository = clientRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public JwtSignInResponse signIn(SignInDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        Optional<Client> client = clientRepository.findByUsername(request.getUsername());

        if (client.isEmpty()) {
            throw new IllegalArgumentException("The email and password combination is incorrect");
        }

        return new JwtSignInResponse(client.get().getId(), jwtService.generateToken(client.get()));
    }

    @Override
    public JwtSignInResponse signUp(SignUpDTO request) {

        if (clientRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Client username is already taken");
        }

        Client client = Mappers.getMapper(ClientMapper.class).dtoToClient(request);
        client.setPassword(passwordEncoder.encode(request.getPassword()));

        client = clientRepository.save(client);
        return new JwtSignInResponse(client.getId(), jwtService.generateToken(client));
    }
}
