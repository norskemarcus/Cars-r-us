package dat3.cars.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class DeveloperData implements CommandLineRunner {


  void makeTestData() {
     //Setup data for development purposes here
  }

  @Override
  public void run(String... args) throws Exception {
    makeTestData();
  }
}

