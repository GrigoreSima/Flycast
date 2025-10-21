package bachelors.flycastserver.controllers;

import bachelors.flycastserver.controllers.apis.ForecastAPI;
import bachelors.flycastserver.domain.dtos.ForecastDTO;
import bachelors.flycastserver.services.ForecastService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ForecastController implements ForecastAPI {

    private final ForecastService forecastService;

    ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @Override
    public ResponseEntity<ForecastDTO> getForecastById(String id) {
        return ResponseEntity.ok(forecastService.getForecastById(id));
    }

    @Override
    public ResponseEntity<ForecastDTO> updateForecast(ForecastDTO forecastDTO) {
        return ResponseEntity.ok(forecastService.updateForecast(forecastDTO));
    }

    @Override
    public ResponseEntity<ForecastDTO> generateForecast(String username, String stationName) {
        return ResponseEntity.ok(forecastService.generateForecast(username, stationName));
    }
}
