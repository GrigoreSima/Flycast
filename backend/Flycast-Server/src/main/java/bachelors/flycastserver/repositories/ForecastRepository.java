package bachelors.flycastserver.repositories;

import bachelors.flycastserver.domain.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ForecastRepository extends JpaRepository<Forecast, UUID> {
    List<Forecast> findByClientIdOrderByDateDesc(UUID id);
}