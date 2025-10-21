package bachelors.flycastserver.controllers.apis;

import bachelors.flycastserver.domain.dtos.ForecastDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("forecasts")
public interface ForecastAPI {
    @GetMapping("/{id}")
    ResponseEntity<ForecastDTO> getForecastById(@PathVariable String id);

    @PutMapping("")
    ResponseEntity<ForecastDTO> updateForecast(@Valid @RequestBody ForecastDTO forecastDTO);

    @GetMapping("/prediction")
    ResponseEntity<ForecastDTO> generateForecast(
            @RequestParam(value = "client") String username,
            @RequestParam(value = "station") String stationName
    );
}
