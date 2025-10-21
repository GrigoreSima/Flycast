package bachelors.flycastserver.controllers;

import bachelors.flycastserver.controllers.apis.ClientAPI;
import bachelors.flycastserver.domain.dtos.ClientDTO;
import bachelors.flycastserver.domain.dtos.ForecastDTO;
import bachelors.flycastserver.domain.dtos.SignUpDTO;
import bachelors.flycastserver.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController implements ClientAPI {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public ResponseEntity<ClientDTO> getClientById(String id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

//    @Override
//    public ResponseEntity<ClientDTO> getClient(String username) {
//        return ResponseEntity.ok(clientService.getClientByUsername(username));
//    }

    @Override
    public ResponseEntity<ClientDTO> updateClient(SignUpDTO clientDTO) {
        return ResponseEntity.ok(clientService.updateClient(clientDTO));
    }

    @Override
    public ResponseEntity<Iterable<ForecastDTO>> getForecastsByClientId(String id) {
        return ResponseEntity.ok(clientService.getForecastsByClientId(id));
    }
}
