package dat3.cars.config;

import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.repositories.CarRepository;
import dat3.cars.repositories.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.*;


@Controller
public class DeveloperData implements CommandLineRunner {

  CarRepository carRepository;
  MemberRepository memberRepository;

  public DeveloperData(CarRepository carRepository, MemberRepository memberRepository) {
    this.carRepository = carRepository;
    this.memberRepository = memberRepository;
  }

  void makeTestData() {
    memberRepository.save(new Member("kurt-w", "kw@a.dk", "test12", "Kurt", "Wonnegut", "Lyngbyvej 34", "Lyngby", "2800"));
    memberRepository.save(new Member("hanne-w", "hw@a.dk", "test12", "Hanne", "Wonnegut", "Lyngbyvej 34", "Lyngby", "2800"));

    /*You can remove the following when we get to week2 if you like, they were only include to demonstrate
    collections of basic type*/
    Member demoMember = new Member("demo", "demo@a.dk", "test12", "Demo", "Wonnegut", "Lyngbyvej 34", "Lyngby", "2800");
    demoMember.setFavoriteCarColors(new ArrayList<>(Arrays.asList("blue","silver","black")));
    Map<String,String> phoneNumbers = new HashMap<>();
    phoneNumbers.put("private","12345");
    phoneNumbers.put("work","54321");
    demoMember.setPhones(phoneNumbers);
    memberRepository.save(demoMember);


    //Obviously the cars below are created by chat-GPT :-)
    List<Car> newCars = new ArrayList<>(Arrays.asList(
        Car.builder().brand("Volvo").model("V70").pricePrDay(500).bestDiscount(10).build(),
        Car.builder().brand("Suzuki").model("Swift").pricePrDay(350).bestDiscount(6).build(),
        Car.builder().brand("Kia").model("Optima").pricePrDay(450).bestDiscount(18).build(),
        Car.builder().brand("WW").model("Wagon").pricePrDay(400).bestDiscount(20).build(),
        Car.builder().brand("Volvo").model("S80").pricePrDay(600).bestDiscount(12).build(),
        Car.builder().brand("Suzuki").model("SX4").pricePrDay(400).bestDiscount(16).build(),
        Car.builder().brand("Suzuki").model("SX4").pricePrDay(400).bestDiscount(16).build(),
        Car.builder().brand("Suzuki").model("SX4").pricePrDay(400).bestDiscount(16).build(),
        Car.builder().brand("Kia").model("Sorento").pricePrDay(500).bestDiscount(22).build(),
        Car.builder().brand("WW").model("Pickup").pricePrDay(450).bestDiscount(28).build(),
        Car.builder().brand("Volvo").model("V60").pricePrDay(700).bestDiscount(15).build(),
        Car.builder().brand("Suzuki").model("Grand Vitara").pricePrDay(450).bestDiscount(12).build(),
        Car.builder().brand("Kia").model("Sportage").pricePrDay(500).bestDiscount(20).build(),
        Car.builder().brand("WW").model("SUV").pricePrDay(400).bestDiscount(18).build(),
        Car.builder().brand("Volvo").model("XC90").pricePrDay(800).bestDiscount(25).build(),
        Car.builder().brand("Volvo").model("XC90").pricePrDay(800).bestDiscount(25).build(),
        Car.builder().brand("Volvo").model("XC90").pricePrDay(800).bestDiscount(25).build(),
        Car.builder().brand("Suzuki").model("Baleno").pricePrDay(450).bestDiscount(15).build(),
        Car.builder().brand("Kia").model("Stinger").pricePrDay(600).bestDiscount(12).build(),
        Car.builder().brand("WW").model("Sedan").pricePrDay(400).bestDiscount(20).build(),
        Car.builder().brand("Volvo").model("XC40").pricePrDay(700).bestDiscount(30).build(),
        Car.builder().brand("Volvo").model("XC40").pricePrDay(700).bestDiscount(30).build(),
        Car.builder().brand("Volvo").model("XC40").pricePrDay(700).bestDiscount(30).build(),
        Car.builder().brand("Suzuki").model("Ignis").pricePrDay(400).bestDiscount(14).build(),
        Car.builder().brand("Kia").model("Rio").pricePrDay(450).bestDiscount(12).build(),
        Car.builder().brand("WW").model("Hatchback").pricePrDay(450).bestDiscount(16).build()
    ));
    carRepository.saveAll(newCars);
  }

  @Override
  public void run(String... args) throws Exception {
    makeTestData();
  }
}
