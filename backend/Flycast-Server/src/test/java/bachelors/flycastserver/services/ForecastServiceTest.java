package bachelors.flycastserver.services;

import bachelors.flycastserver.domain.Client;
import bachelors.flycastserver.domain.Forecast;
import bachelors.flycastserver.domain.MeteorologicalStation;
import bachelors.flycastserver.domain.dtos.ForecastDTO;
import bachelors.flycastserver.domain.dtos.MeteorologicalStationDTO;
import bachelors.flycastserver.repositories.ClientRepository;
import bachelors.flycastserver.repositories.ForecastRepository;
import bachelors.flycastserver.repositories.MeteorologicalStationRepository;
import bachelors.flycastserver.utils.enums.ReviewOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ForecastServiceTest {

    @Autowired
    private ForecastService forecastService;

    @Autowired
    private MeteorologicalStationsService meteorologicalStationsService;

    @Autowired
    private MeteorologicalStationRepository meteorologicalStationRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ForecastRepository forecastRepository;

    @BeforeEach
    void setUp() {
        for (Forecast forecast : forecastRepository.findAll()) {
            if (forecast.getId().equals(UUID.fromString("212209a8-1b49-4bf0-a8d7-dec248ad5b51")) ||
                    forecast.getId().equals(UUID.fromString("212209a8-1c49-4bf0-a8d7-dec248ad5b51"))) {
                continue;
            }

            forecastRepository.delete(forecast);
        }

        Optional<Client> client = clientRepository.findByUsername("Greg");
        Optional<Client> client2 = clientRepository.findByUsername("Alex");
        Optional<MeteorologicalStation> station = meteorologicalStationRepository.findByName("AT BAIA MARE");

        if (client.isEmpty() || client2.isEmpty() || station.isEmpty()) {
            throw new IllegalArgumentException("Client or Meteorological station not found");
        }


        forecastRepository.save(new Forecast(
                UUID.fromString("212209a8-1b49-4bf0-a8d7-dec248ad5b51"),
                client.get(),
                station.get(),
                LocalDate.now(),
                5,
                5.1,
                1016.7,
                80,
                10.5,
                289,
                0.22
        ));


        forecastRepository.save(new Forecast(
                UUID.fromString("212209a8-1c49-4bf0-a8d7-dec248ad5b51"),
                client2.get(),
                station.get(),
                LocalDate.now(),
                5,
                5.1,
                1016.7,
                80,
                10.5,
                289,
                0.22
        ));
    }


    @Test
    void getForecastById() {
        ForecastDTO forecast = forecastService.getForecastById("212209a8-1b49-4bf0-a8d7-dec248ad5b51");

        MeteorologicalStationDTO station = meteorologicalStationsService.getMeteorologicalStationByName("AT BAIA MARE");

        assertEquals(UUID.fromString("212209a8-1b49-4bf0-a8d7-dec248ad5b51"), forecast.getId());
        assertEquals(station, forecast.getMeteorologicalStation());

        assertThrows(IllegalArgumentException.class, () -> forecastService.getForecastById("212209a8-1111-4bf0-a8d7-dec248ad5b51"));
    }

    @Test
    void updateForecast() {
        ForecastDTO forecast = forecastService.getForecastById("212209a8-1c49-4bf0-a8d7-dec248ad5b51");

        MeteorologicalStationDTO station = meteorologicalStationsService.getMeteorologicalStationByName("AT BAIA MARE");

        forecast.setReview(ReviewOptions.WORST);

        ForecastDTO found = forecastService.updateForecast(forecast);

        assertEquals(UUID.fromString("212209a8-1c49-4bf0-a8d7-dec248ad5b51"), found.getId());
        assertEquals(station, found.getMeteorologicalStation());
        assertEquals(ReviewOptions.WORST, found.getReview());

        forecast.setId(UUID.fromString("212209a8-1111-4bf0-a8d7-dec248ad5b51"));

        assertThrows(IllegalArgumentException.class, () -> forecastService.updateForecast(forecast));
    }

    @Test
    void generateForecast() {
        ForecastDTO forecast = forecastService.generateForecast("Alex", "AT BAIA MARE");

        MeteorologicalStationDTO station = meteorologicalStationsService.getMeteorologicalStationByName("AT BAIA MARE");

        assertEquals(station, forecast.getMeteorologicalStation());

        assertThrows(IllegalArgumentException.class, () -> forecastService.generateForecast("Alex", "Greg"));
    }
}