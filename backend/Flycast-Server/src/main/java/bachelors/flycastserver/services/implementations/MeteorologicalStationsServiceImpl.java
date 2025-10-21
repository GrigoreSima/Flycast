package bachelors.flycastserver.services.implementations;

import bachelors.flycastserver.domain.MeteorologicalStation;
import bachelors.flycastserver.domain.dtos.MeteorologicalStationDTO;
import bachelors.flycastserver.repositories.MeteorologicalStationRepository;
import bachelors.flycastserver.services.MeteorologicalStationsService;
import bachelors.flycastserver.utils.mappers.MeteorologicalStationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MeteorologicalStationsServiceImpl implements MeteorologicalStationsService {

    private final MeteorologicalStationRepository meteorologicalStationRepository;
    private final MeteorologicalStationMapper meteorologicalStationMapper;


    @Autowired
    public MeteorologicalStationsServiceImpl(MeteorologicalStationRepository meteorologicalStationRepository, MeteorologicalStationMapper meteorologicalStationMapper) {
        this.meteorologicalStationRepository = meteorologicalStationRepository;
        this.meteorologicalStationMapper = meteorologicalStationMapper;
    }

    @Override
    public List<MeteorologicalStationDTO> getAllMeteorologicalStations() {
        return meteorologicalStationRepository.findAll().stream().map(meteorologicalStationMapper::stationToDto).collect(Collectors.toList());
    }

    @Override
    public MeteorologicalStationDTO getMeteorologicalStationByName(String name) {
        Optional<MeteorologicalStation> station = meteorologicalStationRepository.findByName(name);

        if (station.isEmpty()) {
            throw new IllegalArgumentException("Station not found");
        }

        return meteorologicalStationMapper.stationToDto(station.get());
    }

    @Override
    public MeteorologicalStationDTO addMeteorologicalStation(MeteorologicalStationDTO meteorologicalStation) {
        return meteorologicalStationMapper.stationToDto(meteorologicalStationRepository.save(meteorologicalStationMapper.dtoToStation(meteorologicalStation)));
    }

    @Override
    public void initMeteorologicalStations() {
//        meteorologicalStationRepository.save(new MeteorologicalStation("AT BAIA MARE", "LRBM", 47.6584, 23.47));
        meteorologicalStationRepository.save(new MeteorologicalStation("Otopeni", "LROP", 44.55, 26.0667));
        meteorologicalStationRepository.save(new MeteorologicalStation("Arad", "LRAR", 46.1765, 21.262));
        meteorologicalStationRepository.save(new MeteorologicalStation("Iasi", "LRIA", 47.1785, 27.6206));
        meteorologicalStationRepository.save(new MeteorologicalStation("Brasov", "LRBV", 45.7035, 25.5209));
        meteorologicalStationRepository.save(new MeteorologicalStation("Cluj", "LRCL", 46.7852, 23.6862));
        meteorologicalStationRepository.save(new MeteorologicalStation("Targu Mures", "LRTM", 46.4677, 24.4125));
        meteorologicalStationRepository.save(new MeteorologicalStation("Constanta", "LRCK", 44.3622, 28.4883));
    }
}
