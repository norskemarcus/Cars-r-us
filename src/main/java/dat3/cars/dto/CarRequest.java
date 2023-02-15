package dat3.cars.dto;


import dat3.cars.entity.Car;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarRequest {
  private Integer id;
  private String brand;
  private String model;
  private double pricePrDay;



// Skal id være med her?
  public static Car getCarEntity(CarRequest c){
    return new Car(c.getBrand(), c.getModel(), c.getPricePrDay());
  }

  // Car to CarRequest conversion
  public CarRequest(Car car) {
    this.id = car.getId();
    this.brand = car.getBrand();
    this.model = car.getModel();
    this.pricePrDay = car.getPricePrDay();
  }




}
