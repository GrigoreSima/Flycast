package bachelors.flycastserver.services;

import bachelors.flycastserver.domain.Client;
import bachelors.flycastserver.domain.dtos.ClientDTO;
import bachelors.flycastserver.domain.dtos.ForecastDTO;
import bachelors.flycastserver.domain.dtos.SignUpDTO;
import bachelors.flycastserver.repositories.ClientRepository;
import bachelors.flycastserver.utils.enums.AppUseMotives;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ForecastService forecastService;

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
    void getClientById() {
        ClientDTO client = clientService.getClientByUsername("Greg");
        ClientDTO found = clientService.getClientById(client.getId().toString());

        assertEquals(client, found);

        assertThrows(UsernameNotFoundException.class, () -> clientService.getClientById("212209a8-1111-4bf0-a8d7-dec248ad5b51"));
    }

    @Test
    void getClientByUsername() {
        ClientDTO client = clientService.getClientByUsername("Greg");
        ClientDTO found = clientService.getClientById(client.getId().toString());

        assertEquals(client, found);

        assertThrows(UsernameNotFoundException.class, () -> clientService.getClientByUsername("Testing"));
    }

    @Test
    void updateClient() {
        ClientDTO client = clientService.getClientByUsername("Greg");

        Integer photoID = 1 + (int) (Math.random() * 10) % 3;
        client.setProfilePhoto(photoID);

        ClientDTO found = clientService.updateClient(new SignUpDTO(
                client.getId(),
                client.getUsername(),
                client.getProfilePhoto(),
                client.getEmail(),
                client.getMotive(),
                ""
        ));

        assertEquals(photoID, found.getProfilePhoto());

        assertThrows(IllegalArgumentException.class, () -> clientService.updateClient(
                new SignUpDTO(
                        UUID.fromString("212209a8-1111-4bf0-a8d7-dec248ad5b51"),
                        client.getUsername(),
                        client.getProfilePhoto(),
                        client.getEmail(),
                        client.getMotive(),
                        ""
                ))
        );
    }

    @Test
    void getForecastsByClientId() {
        ClientDTO client = clientService.getClientByUsername("Greg");

        List<ForecastDTO> forecasts = clientService.getForecastsByClientId(client.getId().toString());

        ForecastDTO found = forecastService.getForecastById("212209a8-1b49-4bf0-a8d7-dec248ad5b51");

        forecasts.getFirst().setMETAR(found.getMETAR());

        assertEquals(1, forecasts.size());
        assertEquals(found, forecasts.getFirst());
    }
}