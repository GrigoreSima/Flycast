package bachelors.flycastserver.services;

import bachelors.flycastserver.domain.dtos.ClientDTO;
import bachelors.flycastserver.domain.dtos.ForecastDTO;
import bachelors.flycastserver.domain.dtos.SignUpDTO;

import java.util.List;

public interface ClientService {

    ClientDTO getClientById(String id);

    ClientDTO getClientByUsername(String username);

    ClientDTO updateClient(SignUpDTO clientDTO);

    List<ForecastDTO> getForecastsByClientId(String id);
}
