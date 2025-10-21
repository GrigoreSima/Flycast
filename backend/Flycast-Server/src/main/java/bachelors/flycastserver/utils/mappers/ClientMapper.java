package bachelors.flycastserver.utils.mappers;

import bachelors.flycastserver.domain.Client;
import bachelors.flycastserver.domain.dtos.ClientDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDTO clientToDto(Client client);

    Client dtoToClient(ClientDTO clientDTO);
}
