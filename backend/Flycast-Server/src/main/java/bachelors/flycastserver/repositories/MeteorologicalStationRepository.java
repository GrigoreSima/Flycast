package bachelors.flycastserver.repositories;

import bachelors.flycastserver.domain.MeteorologicalStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MeteorologicalStationRepository extends JpaRepository<MeteorologicalStation, UUID> {
    Optional<MeteorologicalStation> findByName(String name);
}
