package dat3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan
@EnableJpaRepositories
@SpringBootApplication
//@SpringBootApplication( exclude = {SecurityAutoConfiguration.class} )
public class CarsApplication {
  public static void main(String[] args) {
    SpringApplication.run(CarsApplication.class, args);
  }
}
