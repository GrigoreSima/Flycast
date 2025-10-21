package bachelors.flycastserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class FlycastServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlycastServerApplication.class, args);
    }

}
