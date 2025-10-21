package bachelors.flycastserver.controllers.apis;

import bachelors.flycastserver.domain.dtos.ClientDTO;
import bachelors.flycastserver.domain.dtos.ForecastDTO;
import bachelors.flycastserver.domain.dtos.SignUpDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("clients")
public interface ClientAPI {
    @GetMapping("/{id}")
    ResponseEntity<ClientDTO> getClientById(@PathVariable String id);

//    @GetMapping("/{username}")
//    ResponseEntity<ClientDTO> getClient(@PathVariable String username);

    @PutMapping("")
    ResponseEntity<ClientDTO> updateClient(@Valid @RequestBody SignUpDTO clientDTO);

    @GetMapping("/{id}/forecasts")
    ResponseEntity<Iterable<ForecastDTO>> getForecastsByClientId(@PathVariable String id);
}
