package dat3.cars.repository;

import dat3.cars.dto.CarResponse;
import dat3.cars.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

 List<Car> findByBrandAndModel(String brand, String model);

}