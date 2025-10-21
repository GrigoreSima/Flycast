package bachelors.flycastserver.utils.mappers;

import bachelors.flycastserver.domain.MeteorologicalStation;
import bachelors.flycastserver.domain.dtos.MeteorologicalStationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeteorologicalStationMapper {
    MeteorologicalStationDTO stationToDto(MeteorologicalStation meteorologicalStation);

    MeteorologicalStation dtoToStation(MeteorologicalStationDTO meteorologicalStationDTO);
}
