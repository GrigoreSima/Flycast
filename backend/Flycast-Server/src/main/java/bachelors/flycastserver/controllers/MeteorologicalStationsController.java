package bachelors.flycastserver.controllers;

import bachelors.flycastserver.controllers.apis.MeteorologicalStationsAPI;
import bachelors.flycastserver.domain.dtos.MeteorologicalStationDTO;
import bachelors.flycastserver.services.MeteorologicalStationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeteorologicalStationsController implements MeteorologicalStationsAPI {

    private final MeteorologicalStationsService meteorologicalStationsService;

    @Autowired
    public MeteorologicalStationsController(MeteorologicalStationsService meteorologicalStationsService) {
        this.meteorologicalStationsService = meteorologicalStationsService;
    }

    @Override
    public ResponseEntity<Iterable<MeteorologicalStationDTO>> getAllMeteorologicalStations() {
        return ResponseEntity.ok(meteorologicalStationsService.getAllMeteorologicalStations());
    }

    @Override
    public ResponseEntity<MeteorologicalStationDTO> getMeteorologicalStationByName(String name) {
        return ResponseEntity.ok(meteorologicalStationsService.getMeteorologicalStationByName(name));
    }
}
