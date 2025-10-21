package bachelors.flycastserver.services;

import bachelors.flycastserver.controllers.responses.JwtSignInResponse;
import bachelors.flycastserver.domain.Client;
import bachelors.flycastserver.domain.dtos.ClientDTO;
import bachelors.flycastserver.domain.dtos.SignInDTO;
import bachelors.flycastserver.domain.dtos.SignUpDTO;
import bachelors.flycastserver.repositories.ClientRepository;
import bachelors.flycastserver.utils.enums.AppUseMotives;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        for (Client client : clientRepository.findAll()) {
            if (client.getUsername().equals("Greg") ||
                    client.getUsername().equals("Alex")) {
                continue;
            }

            clientRepository.delete(client);
        }

        clientRepository.save(new Client(
                UUID.fromString("d57410f0-93f1-4fbe-8447-5e3f40d09a1f"),
                "Greg",
                passwordEncoder.encode("flying"),
                2,
                "greg@flycast.com",
                AppUseMotives.FORECAST_FOR_FLYING
        ));

        clientRepository.save(new Client(
                UUID.fromString("bbe5b4c9-a7b9-4bfe-911f-6a6d7dacaa23"),
                "Alex",
                passwordEncoder.encode("lexutz"),
                1,
                "alex@flycast.com",
                AppUseMotives.LEARNING_METAR
        ));
    }

    @Test
    void signIn() {
        ClientDTO client = clientService.getClientByUsername("Greg");

        JwtSignInResponse response = authService.signIn(new SignInDTO("Greg", "flying"));

        assertEquals(client.getId(), response.getId());
        assertNotNull(response.getToken());
        assertFalse(response.getToken().isEmpty());

        assertThrows(BadCredentialsException.class, () -> authService.signIn(new SignInDTO("Testing", "testing")));
    }

    @Test
    void signUp() {
        JwtSignInResponse response = authService.signUp(
                new SignUpDTO(
                        "Profile",
                        1,
                        "profile@testing.com",
                        AppUseMotives.LEARNING_METAR,
                        "flying"
                )
        );

        ClientDTO client = clientService.getClientByUsername("Profile");

        assertEquals(client.getId(), response.getId());
        assertNotNull(response.getToken());
        assertFalse(response.getToken().isEmpty());

        assertThrows(IllegalArgumentException.class, () -> {
            authService.signUp(
                    new SignUpDTO(
                            "Greg",
                            1,
                            "profile@testing.com",
                            AppUseMotives.LEARNING_METAR,
                            "flying"
                    )
            );
        });

    }
}