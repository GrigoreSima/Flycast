package bachelors.flycastserver.services.implementations;

import bachelors.flycastserver.domain.Client;
import bachelors.flycastserver.domain.Forecast;
import bachelors.flycastserver.domain.MeteorologicalStation;
import bachelors.flycastserver.domain.dtos.ForecastDTO;
import bachelors.flycastserver.repositories.ClientRepository;
import bachelors.flycastserver.repositories.ForecastRepository;
import bachelors.flycastserver.repositories.MeteorologicalStationRepository;
import bachelors.flycastserver.services.ForecastService;
import bachelors.flycastserver.utils.mappers.ForecastMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;

import static java.lang.Thread.sleep;

@Service
public class ForecastServiceImpl implements ForecastService {
    private final ForecastRepository forecastRepository;
    private final ForecastMapper forecastMapper;
    private final MeteorologicalStationRepository meteorologicalStationRepository;
    private final ClientRepository clientRepository;

    public ForecastServiceImpl(ForecastRepository forecastRepository, ForecastMapper forecastMapper, MeteorologicalStationRepository meteorologicalStationRepository, ClientRepository clientRepository) {
        this.forecastMapper = forecastMapper;
        this.clientRepository = clientRepository;
        this.meteorologicalStationRepository = meteorologicalStationRepository;
        this.forecastRepository = forecastRepository;
    }

    @Override
    public ForecastDTO getForecastById(String id) {
        Optional<Forecast> forecast = forecastRepository.findById(UUID.fromString(id));

        if (forecast.isEmpty()) {
            throw new IllegalArgumentException("Forecast with ID " + id + " not found");
        }

        ForecastDTO forecastDTO = forecastMapper.forecastToDto(forecast.get());
        forecastDTO.setMETAR(getMETAR(forecast.get()));

        return forecastDTO;
    }

    @Override
    public ForecastDTO updateForecast(ForecastDTO forecastDTO) {
        Optional<Forecast> optionalForecast = forecastRepository.findById(forecastDTO.getId());

        if (optionalForecast.isEmpty()) {
            throw new IllegalArgumentException("Forecast with ID " + forecastDTO.getId() + " not found");
        }

        Forecast forecast = optionalForecast.get();
        forecast.setReview(forecastDTO.getReview());

        forecast = forecastRepository.save(forecast);

        forecastDTO = forecastMapper.forecastToDto(forecast);
        forecastDTO.setMETAR(getMETAR(forecast));

        return forecastDTO;
    }

    @Override
    public ForecastDTO generateForecast(String username, String stationName) {
        try {
            Optional<Client> client = clientRepository.findByUsername(username);

            if (client.isEmpty()) {
                throw new IllegalArgumentException("Client with name " + username + " not found");
            }

            Optional<MeteorologicalStation> station = meteorologicalStationRepository.findByName(stationName);

            if (station.isEmpty()) {
                throw new IllegalArgumentException("Meteorological station with name " + stationName + " not found");
            }

            Forecast forecast = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .readValue(getPrediction(LocalDate.now().plusDays(1), station.get().getCode()), Forecast.class);

            forecast.setClient(client.get());
            forecast.setMeteorologicalStation(station.get());

            forecast = forecastRepository.save(forecast);

            ForecastDTO forecastDTO = forecastMapper.forecastToDto(forecast);
            forecastDTO.setMETAR(getMETAR(forecast));

            return forecastDTO;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Could not generate forecast");
        }
    }

    @Override
    public void trainModels() {

        for (MeteorologicalStation station : meteorologicalStationRepository.findAll()) {

            ProcessBuilder getData = new ProcessBuilder("python3.10",
                    "get_data.py",
                    station.getCode(),
                    station.getLatitude().toString(),
                    station.getLongitude().toString()
            );
            getData.directory(new File("../predictor/preprocessing"));

            try {
                getData.start();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        ProcessBuilder preprocessing = new ProcessBuilder("python3.10",
                "preprocessing.py"
        );
        preprocessing.directory(new File("../predictor/preprocessing"));

        ProcessBuilder train = new ProcessBuilder("python3.10",
                "train.py"
        );
        train.directory(new File("../predictor"));

        try {
            sleep(10000);
            preprocessing.start();

            sleep(10000);
            train.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getPrediction(LocalDate now, String stationCode) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("python3.10",
                "predict.py",
                String.valueOf(now.getYear()),
                String.valueOf(now.getMonthValue()),
                String.valueOf(now.getDayOfMonth()),
                stationCode
        );
        pb.directory(new File("../predictor"));

        Process p = pb.start();
        BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()));

        String line = error.readLine();
        while (line != null) {
            System.out.println(line);
            line = error.readLine();
        }

        return bf.readLine();
    }

    private static String getMETAR(Forecast forecast) {
        StringBuilder builder = new StringBuilder();

        builder.append("METAR");
        builder.append(" ");

        builder.append(forecast.getMeteorologicalStation().getCode());
        builder.append(" ");

        builder.append(forecast.getDate().getDayOfMonth());
        builder.append("1200Z");
        builder.append(" ");

        builder.append(String.format("%03d", forecast.getWindDirection()));
        builder.append(String.format("%02d", Math.max(1, Math.round(forecast.getWindSpeed()))));
        builder.append("MPS");
        builder.append(" ");

        String clouds = switch (forecast.getCloudiness()) {
            case 1, 2 -> "FEW";
            case 3, 4 -> "SCT";
            case 5, 6, 7 -> "BKN";
            case 8 -> "OVC";
            default -> "SKC";
        };

        builder.append(clouds);
        builder.append("030");
        builder.append(" ");

        long temperature = Math.round(forecast.getTemperature());
        if (temperature < 0) builder.append("M");
        builder.append(String.format("%02d", temperature));
        builder.append("/");
        long dewPoint = Math.round(forecast.getDewPoint());
        if (dewPoint < 0) builder.append("M");
        builder.append(String.format("%02d", dewPoint));
        builder.append(" ");

        builder.append("Q");
        builder.append(Math.round(forecast.getPressure()));

        return builder.toString();
    }
}
