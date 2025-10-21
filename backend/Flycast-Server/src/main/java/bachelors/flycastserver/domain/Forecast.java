package bachelors.flycastserver.domain;

import bachelors.flycastserver.utils.enums.ReviewOptions;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Forecast extends Identifiable {

    @ManyToOne(optional = false)
    private Client client;

    @ManyToOne(optional = false)
    private MeteorologicalStation meteorologicalStation;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @Column(nullable = false)
    private Integer cloudiness;

    @Column(nullable = false)
    private Double dewPoint;

    @Column(nullable = false)
    private Double pressure;

    @Column(nullable = false)
    private Integer humidity;

    @Column(nullable = false)
    private Double temperature;

    @Column(nullable = false)
    private Integer windDirection;

    @Column(nullable = false)
    private Double windSpeed;

    @Column()
    @Enumerated(EnumType.ORDINAL)
    private ReviewOptions review;

    public Forecast() {
    }

    public Forecast(UUID id, Client client, MeteorologicalStation meteorologicalStation, LocalDate date, Integer cloudiness,
                    Double dewPoint, Double pressure, Integer humidity, Double temperature, Integer windDirection, Double windSpeed,
                    ReviewOptions review) {
        super(id);
        this.client = client;
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

    public Forecast(Client client, MeteorologicalStation meteorologicalStation, LocalDate date, Integer cloudiness, Double dewPoint,
                    Double pressure, Integer humidity, Double temperature, Integer windDirection, Double windSpeed, ReviewOptions review) {
        this.client = client;
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

    public Forecast(UUID id, Client client, MeteorologicalStation meteorologicalStation, LocalDate date, Integer cloudiness, Double dewPoint, Double pressure, Integer humidity, Double temperature, Integer windDirection, Double windSpeed) {
        super(id);
        this.client = client;
        this.meteorologicalStation = meteorologicalStation;
        this.date = date;
        this.cloudiness = cloudiness;
        this.dewPoint = dewPoint;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temperature = temperature;
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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

    public ReviewOptions getReview() {
        return review;
    }

    public void setReview(ReviewOptions review) {
        this.review = review;
    }

    public MeteorologicalStation getMeteorologicalStation() {
        return meteorologicalStation;
    }

    public void setMeteorologicalStation(MeteorologicalStation meteorologicalStation) {
        this.meteorologicalStation = meteorologicalStation;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "client=" + client +
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
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Forecast forecast = (Forecast) o;
        return Objects.equals(getClient(), forecast.getClient()) &&
                Objects.equals(getMeteorologicalStation(), forecast.getMeteorologicalStation()) &&
                Objects.equals(getDate(), forecast.getDate()) &&
                Objects.equals(getCloudiness(), forecast.getCloudiness()) &&
                Objects.equals(getDewPoint(), forecast.getDewPoint()) &&
                Objects.equals(getPressure(), forecast.getPressure()) &&
                Objects.equals(getHumidity(), forecast.getHumidity()) &&
                Objects.equals(getTemperature(), forecast.getTemperature()) &&
                Objects.equals(getWindDirection(), forecast.getWindDirection()) &&
                Objects.equals(getWindSpeed(), forecast.getWindSpeed()) &&
                getReview() == forecast.getReview();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClient(), getMeteorologicalStation(), getDate(), getCloudiness(), getDewPoint(),
                getPressure(), getHumidity(), getTemperature(), getWindDirection(), getWindSpeed(), getReview());
    }
}
