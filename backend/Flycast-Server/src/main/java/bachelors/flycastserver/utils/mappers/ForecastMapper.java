package bachelors.flycastserver.utils.mappers;

import bachelors.flycastserver.domain.Forecast;
import bachelors.flycastserver.domain.dtos.ForecastDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ForecastMapper {
    ForecastDTO forecastToDto(Forecast forecast);

    Forecast dtoToForecast(ForecastDTO forecastDTO);
}
