package bachelors.flycastserver.controllers.apis;

import bachelors.flycastserver.domain.dtos.MeteorologicalStationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/stations")
public interface MeteorologicalStationsAPI {
    @GetMapping("")
    ResponseEntity<Iterable<MeteorologicalStationDTO>> getAllMeteorologicalStations();

    @GetMapping("/{name}")
    ResponseEntity<MeteorologicalStationDTO> getMeteorologicalStationByName(@PathVariable String name);
}
