package dat3.cars.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.cars.entity.Car;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarRequest {
  private Integer id;
  private String brand;
  private String model;
  private double pricePrDay;


  // Hvis jeg ikke lavet en constructor til denne brokket den sig...
  public static Car getCarEntity(CarRequest c){
    return new Car(c.id, c.getBrand(), c.getModel(), c.getPricePrDay());
  }

  // Car to CarRequest conversion
  public CarRequest(Car car) {
    this.id = car.getId();
    this.brand = car.getBrand();
    this.model = car.getModel();
    this.pricePrDay = car.getPricePrDay();
  }




}
