package dat3.cars.repository;

import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

 // Extracted with the plugin JPA Buddy
 // Find all cars with a certain brand and model
 @Query("select c from Car c where c.brand = ?1 and c.model = ?2")
 List<Car> findByBrandAndModel(String brand, String model);


 // Find the average price per day of all cars in the system
 @Query("SELECT AVG(c.pricePrDay) FROM Car c")
 double findAvgPricePrDayALlCars();


// Find the best discount
@Query ("SELECT MAX(c.bestDiscount) FROM Car c")
Integer findMaxDiscount();

 // Find all cars with the best discount
@Query("select c from Car c where c.bestDiscount = ?1")
List<Car> findCarsByBestDiscount(Integer bestDiscount);


// Find all cars that have not been reserved
 @Query("SELECT c FROM Car c WHERE c.id NOT IN (SELECT DISTINCT r.car.id FROM Reservation r)")
 List<Car> getCarsWithoutReservation();

}