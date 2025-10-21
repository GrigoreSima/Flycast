package bachelors.flycastserver.services.implementations;

import bachelors.flycastserver.domain.Client;
import bachelors.flycastserver.domain.dtos.ClientDTO;
import bachelors.flycastserver.domain.dtos.ForecastDTO;
import bachelors.flycastserver.domain.dtos.SignUpDTO;
import bachelors.flycastserver.repositories.ClientRepository;
import bachelors.flycastserver.repositories.ForecastRepository;
import bachelors.flycastserver.services.ClientService;
import bachelors.flycastserver.utils.mappers.ClientMapper;
import bachelors.flycastserver.utils.mappers.ForecastMapper;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final ForecastRepository forecastRepository;
    private final ForecastMapper forecastMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper, ForecastRepository forecastRepository, ForecastMapper forecastMapper, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.forecastRepository = forecastRepository;
        this.forecastMapper = forecastMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ClientDTO getClientById(String id) {
        Optional<Client> client = clientRepository.findById(UUID.fromString(id));

        if (client.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return clientMapper.clientToDto(client.get());
    }

    @Override
    public ClientDTO getClientByUsername(String username) {
        Optional<Client> client = clientRepository.findByUsername(username);

        if (client.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return clientMapper.clientToDto(client.get());
    }

    @Override
    public ClientDTO updateClient(SignUpDTO clientDTO) {
        Optional<Client> client = clientRepository.findById(clientDTO.getId());

        if (client.isEmpty()) {
            throw new IllegalArgumentException("Client with ID " + clientDTO.getId() + " not found");
        }

        Client editedClient = client.get();

        try {
            customMapper(SignUpDTO.class, editedClient, clientDTO);
            customMapper(ClientDTO.class, editedClient, clientDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return clientMapper.clientToDto(
                clientRepository.save(editedClient)
        );
    }

    private <T extends ClientDTO> void customMapper(Class<T> objClass, Client editedClient, SignUpDTO clientDTO) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        for (Field declaredField : objClass.getDeclaredFields()) {
            String fieldName = declaredField.getName();
            if (fieldName.equals("id") || fieldName.equals("username")) {
                continue;
            }

            fieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);

            Object oldValue = Client.class.getDeclaredMethod("get" + fieldName).invoke(editedClient);
            Object newValue = objClass.getDeclaredMethod("get" + fieldName).invoke(clientDTO);

            if (!oldValue.equals(newValue) && newValue != null) {

                if (fieldName.equals("Password") && newValue.equals("")) {
                    continue;
                }

                Method method = Client.class.getDeclaredMethod("set" + fieldName, declaredField.getType());

                if (fieldName.equals("Password")) {
                    method.invoke(editedClient, passwordEncoder.encode((String) newValue));
                } else {
                    method.invoke(editedClient, newValue);
                }
            }
        }
    }

    @Override
    public List<ForecastDTO> getForecastsByClientId(String id) {
        Optional<Client> client = clientRepository.findById(UUID.fromString(id));

        if (client.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return forecastRepository.findByClientIdOrderByDateDesc(client.get().getId()).stream()
                .map(forecastMapper::forecastToDto).collect(Collectors.toList());
    }
}
