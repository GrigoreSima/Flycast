package bachelors.flycastserver.services;

import bachelors.flycastserver.domain.MeteorologicalStation;
import bachelors.flycastserver.domain.dtos.MeteorologicalStationDTO;

import java.util.List;

public interface MeteorologicalStationsService {
    List<MeteorologicalStationDTO> getAllMeteorologicalStations();

    MeteorologicalStationDTO getMeteorologicalStationByName(String name);

    MeteorologicalStationDTO addMeteorologicalStation(MeteorologicalStationDTO meteorologicalStation);

    void initMeteorologicalStations();
}
