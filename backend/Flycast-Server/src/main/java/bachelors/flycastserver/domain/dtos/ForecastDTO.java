package bachelors.flycastserver.domain.dtos;

import bachelors.flycastserver.utils.enums.ReviewOptions;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class ForecastDTO {
    private UUID id;
    private MeteorologicalStationDTO meteorologicalStation;
    private LocalDate date;
    private Integer cloudiness;
    private Double dewPoint;
    private Double pressure;
    private Integer humidity;
    private Double temperature;
    private Integer windDirection;
    private Double windSpeed;
    private ReviewOptions review;

    private String METAR;


    public ForecastDTO() {
    }

    public ForecastDTO(UUID id, MeteorologicalStationDTO meteorologicalStation, LocalDate date, Integer cloudiness,
                       Double dewPoint, Double pressure, Integer humidity, Double temperature, Integer windDirection,
                       Double windSpeed, ReviewOptions review) {
        this.id = id;
        this.meteorologicalStation = meteorologicalStation;
        this.date = date;
        this.cloudiness = cloudiness;
        this.dewPoint = dewPoint;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temperature = temperature;
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
        this.review = review;
    }

    public ForecastDTO(MeteorologicalStationDTO meteorologicalStation, LocalDate date, Integer cloudiness, Double dewPoint,
                       Double pressure, Integer humidity, Double temperature, Integer windDirection, Double windSpeed,
                       ReviewOptions review) {
        this.meteorologicalStation = meteorologicalStation;
        this.date = date;
        this.cloudiness = cloudiness;
        this.dewPoint = dewPoint;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temperature = temperature;
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
        this.review = review;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(Integer cloudiness) {
        this.cloudiness = cloudiness;
    }

    public Double getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(Double dewPoint) {
        this.dewPoint = dewPoint;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Integer windDirection) {
        this.windDirection = windDirection;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public MeteorologicalStationDTO getMeteorologicalStation() {
        return meteorologicalStation;
    }

    public void setMeteorologicalStation(MeteorologicalStationDTO meteorologicalStation) {
        this.meteorologicalStation = meteorologicalStation;
    }

    public ReviewOptions getReview() {
        return review;
    }

    public void setReview(ReviewOptions review) {
        this.review = review;
    }

    public String getMETAR() {
        return METAR;
    }

    public void setMETAR(String METAR) {
        this.METAR = METAR;
    }

    @Override
    public String toString() {
        return "ForecastDTO{" +
                "id=" + id +
                ", meteorologicalStation=" + meteorologicalStation +
                ", date=" + date +
                ", cloudiness=" + cloudiness +
                ", dewPoint=" + dewPoint +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", temperature=" + temperature +
                ", windDirection=" + windDirection +
                ", windSpeed=" + windSpeed +
                ", review=" + review +
                ", METAR='" + METAR + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ForecastDTO that = (ForecastDTO) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getMeteorologicalStation(), that.getMeteorologicalStation()) && Objects.equals(getDate(), that.getDate()) && Objects.equals(getCloudiness(), that.getCloudiness()) && Objects.equals(getDewPoint(), that.getDewPoint()) && Objects.equals(getPressure(), that.getPressure()) && Objects.equals(getHumidity(), that.getHumidity()) && Objects.equals(getTemperature(), that.getTemperature()) && Objects.equals(getWindDirection(), that.getWindDirection()) && Objects.equals(getWindSpeed(), that.getWindSpeed()) && getReview() == that.getReview() && Objects.equals(getMETAR(), that.getMETAR());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMeteorologicalStation(), getDate(), getCloudiness(), getDewPoint(), getPressure(), getHumidity(), getTemperature(), getWindDirection(), getWindSpeed(), getReview(), getMETAR());
    }
}
