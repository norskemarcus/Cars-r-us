package dat3.cars.config;

import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import dat3.cars.repository.CarRepository;
import dat3.cars.repository.MemberRepository;
import dat3.cars.repository.ReservationRepository;
import dat3.security.entity.Role;
import dat3.security.entity.UserWithRoles;
import dat3.security.repository.UserWithRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;



import java.time.LocalDate;
import java.util.*;

@Configuration
public class DeveloperData implements ApplicationRunner {

  @Autowired
  UserWithRolesRepository userWithRolesRepository;

  CarRepository carRepository;
  MemberRepository memberRepository;
  ReservationRepository reservationRepository;


  public DeveloperData(CarRepository carRepository, MemberRepository memberRepository,
                       ReservationRepository reservationRepository) {
    this.carRepository = carRepository;
    this.memberRepository = memberRepository;
    this.reservationRepository = reservationRepository;
  }


  @Override
  public void run(ApplicationArguments args) throws Exception {
    makeTestData();
    setupUserWithRoleUsers();
  }


  void makeTestData() {
    Member kurt = memberRepository.save(new Member("kurt", "test12", "kw@a.dk", "Kurt", "Wonnegut", "Lyngbyvej 34", "Lyngby", "2800"));

    Member hanne = memberRepository.save(new Member("hanne", "test12", "hw@a.dk", "Hanne", "Wonnegut", "Lyngbyvej 34", "Lyngby", "2800"));

    Member admin = memberRepository.save(new Member("admin", "test12", "demo@a.dk", "Demo-admin", "Wonnegut", "Lyngbyvej 34", "Lyngby", "2800"));

    Member user = memberRepository.save(new Member("user", "test12", "user@gmail.dk", "Demo-user", "Wonnegut", "Lyngbyvej 34", "Lyngby", "2800"));

    Member useradmin = memberRepository.save(new Member("useradmin", "test12", "useradmin@a.dk", "Demo-admin-and-user", "Wonnegut", "Lyngbyvej 34", "Lyngby", "2800"));


    kurt.addRole(Role.USER);
    kurt.addRole(Role.ADMIN);
    hanne.addRole(Role.USER);
    admin.addRole(Role.ADMIN);
    user.addRole(Role.USER);
    user.addRole(Role.USER);
    useradmin.addRole(Role.ADMIN);

    /*
    userWithRolesRepository.save(kurt);

    userWithRolesRepository.save(hanne);
    userWithRolesRepository.save(admin);
    userWithRolesRepository.save(user);
    userWithRolesRepository.save(useradmin);
*/
    // Reservation 1
    Car car = Car.builder().brand("Volvo").model("V70").pricePrDay(500).bestDiscount(10).build();
    LocalDate startDate = LocalDate.parse("2023-02-14");
    carRepository.save(car);
    Reservation reservation = new Reservation(car, admin, startDate);
    reservationRepository.save(reservation);

    // Reservation 2
    Car car2 = Car.builder().brand("Tesla").model("Model Y").pricePrDay(700).bestDiscount(10).build();
    LocalDate startDate2 = LocalDate.parse("2023-02-16");
    carRepository.save(car2);
    Reservation reservation2 = new Reservation(car2, hanne, startDate2);
    reservationRepository.save(reservation2);

    // The cars below are created by chat-GPT
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
        Car.builder().brand("Volvo").model("XC90").pricePrDay(800).bestDiscount(25).build()
    ));
    carRepository.saveAll(newCars);
  }



/*****************************************************************************************
 NEVER  COMMIT/PUSH CODE WITH DEFAULT CREDENTIALS FOR REAL
 iT'S ONE OF THE TOP SECURITY FLAWS YOU CAN DO
 ****************************************************************************************
 * */
    private void setupUserWithRoleUsers() {
      UserWithRoles user1 = new UserWithRoles("user1", "test12", "user1@a.dk");
      UserWithRoles user2 = new UserWithRoles("user2", "test12", "user2@a.dk");
      UserWithRoles user3 = new UserWithRoles("user3", "test12", "user3@a.dk");
      UserWithRoles user4 = new UserWithRoles("user4", "test12", "user4@a.dk");

      user1.addRole(Role.USER);
      user1.addRole(Role.ADMIN);
      user2.addRole(Role.USER);
      user3.addRole(Role.ADMIN);

      //No Role assigned to user4
      userWithRolesRepository.save(user1);
      userWithRolesRepository.save(user2);
      userWithRolesRepository.save(user3);
      userWithRolesRepository.save(user4);
    }
}
