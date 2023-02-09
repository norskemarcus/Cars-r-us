package dat3.cars.dto;


import dat3.cars.entity.Car;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarRequest {
  private Integer id;
  private String brand;
  private String model;
  private double pricePrDay;



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
