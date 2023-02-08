package dat3.cars.repositories;

import dat3.cars.dto.CarResponse;
import dat3.cars.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

  CarResponse getCarById(Integer id);

}