package dat3.cars.repository;

import dat3.cars.entity.Car;
import dat3.cars.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

  boolean existsByCarAndRentalDate(Car car, LocalDate rentalDate);


}
