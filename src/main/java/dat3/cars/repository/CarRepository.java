package dat3.cars.repository;

import dat3.cars.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

 // Extracted with the plugin JPA Buddy
 @Query("select c from Car c where c.brand = ?1 and c.model = ?2")
 List<Car> findByBrandAndModel(String brand, String model);

 // chatGPT
 @Query("SELECT AVG(c.pricePrDay) FROM Car c")
 double findAvgPricePrDayALlCars();



/* // Find the best discount
 @Query("SELECT c.max_discount FROM Car c")
 Integer findBestDiscount();

 // Find all cars with the best discount
 @Query("select c from Car c where c.max_discount = ?1")
 List<Car> findAllByBestDiscount(Integer bestDiscount);*/

@Query ("SELECT MAX(c.bestDiscount) FROM Car c")
Integer findMaxDiscount();


@Query("select c from Car c where c.bestDiscount = ?1")
List<Car> findCarsByBestDiscount(Integer bestDiscount);


}