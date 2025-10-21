package bachelors.flycastserver.services;

import bachelors.flycastserver.domain.MeteorologicalStation;
import bachelors.flycastserver.domain.dtos.MeteorologicalStationDTO;
import bachelors.flycastserver.repositories.MeteorologicalStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MeteorologicalStationsServiceTest {

    @Autowired
    private MeteorologicalStationsService meteorologicalStationsService;

    @Autowired
    private MeteorologicalStationRepository meteorologicalStationRepository;

    @BeforeEach
    void setUp() {
        for (MeteorologicalStation meteorologicalStation : meteorologicalStationRepository.findAll()) {
            if (meteorologicalStation.getName().equals("AT BAIA MARE")) {
                continue;
            }

            meteorologicalStationRepository.delete(meteorologicalStation);
        }
    }

    @Test
    @Order(1)
    void getAllMeteorologicalStations() {
        List<MeteorologicalStationDTO> stations = meteorologicalStationsService.getAllMeteorologicalStations();

        assertEquals(1, stations.size());
    }

    @Test
    @Order(2)
    void getMeteorologicalStationByName() {
        MeteorologicalStationDTO station = meteorologicalStationsService.getMeteorologicalStationByName("AT BAIA MARE");

        assertEquals("AT BAIA MARE", station.getName());

        assertThrows(IllegalArgumentException.class, () -> meteorologicalStationsService.getMeteorologicalStationByName("Greg"));
    }

    @Test
    @Order(3)
    void addMeteorologicalStation() {
        List<MeteorologicalStationDTO> stations = meteorologicalStationsService.getAllMeteorologicalStations();

        assertEquals(1, stations.size());

        MeteorologicalStationDTO station = new MeteorologicalStationDTO("AT CLUJ", "LRCJ", 11.11, 22.22);

        station = meteorologicalStationsService.addMeteorologicalStation(station);


        stations = meteorologicalStationsService.getAllMeteorologicalStations();
        assertEquals(2, stations.size());
        assertEquals(station, stations.get(1));
    }
}