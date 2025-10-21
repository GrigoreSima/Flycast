package bachelors.flycastserver.services;

import bachelors.flycastserver.domain.dtos.ForecastDTO;

public interface ForecastService {
    ForecastDTO getForecastById(String id);

    ForecastDTO updateForecast(ForecastDTO forecastDTO);

    ForecastDTO generateForecast(String username, String stationName);

    void trainModels();
}
