package dat3.cars.config;

import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import dat3.cars.repositories.CarRepository;
import dat3.cars.repositories.MemberRepository;
import dat3.cars.repositories.ReservationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.*;


@Controller
public class DeveloperData implements CommandLineRunner {

  CarRepository carRepository;
  MemberRepository memberRepository;
  ReservationRepository reservationRepository;

  public DeveloperData(CarRepository carRepository, MemberRepository memberRepository,
                       ReservationRepository reservationRepository) {
    this.carRepository = carRepository;
    this.memberRepository = memberRepository;
    this.reservationRepository = reservationRepository;
  }

  void makeTestData() {
    memberRepository.save(new Member("kurt-w", "kw@a.dk", "test12", "Kurt", "Wonnegut", "Lyngbyvej 34", "Lyngby", "2800"));
    memberRepository.save(new Member("hanne-w", "hw@a.dk", "test12", "Hanne", "Wonnegut", "Lyngbyvej 34", "Lyngby", "2800"));

    Member demoMember = new Member("demo", "demo@a.dk", "test12", "Demo", "Wonnegut", "Lyngbyvej 34", "Lyngby", "2800");
    Map<String,String> phoneNumbers = new HashMap<>();
    phoneNumbers.put("private","12345");
    phoneNumbers.put("work","54321");
    memberRepository.save(demoMember);

    // Reservation 1
    Car car = Car.builder().brand("Volvo").model("V70").pricePrDay(500).bestDiscount(10).build();
    LocalDate startDate = LocalDate.parse("2023-02-14");
    carRepository.save(car);

    // Car car, Member member, LocalDate reservationDate yyyy-MM-dd
    Reservation reservation = new Reservation(car, demoMember, startDate);
    reservationRepository.save(reservation);
    

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
