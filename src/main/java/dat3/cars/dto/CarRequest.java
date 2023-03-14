package dat3.cars.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.cars.entity.Car;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarRequest {
  private Integer id;
  private String brand;
  private String model;
  private double pricePrDay;
  private Integer bestDiscount;


  public static Car getCarEntity(CarRequest c){
    return new Car(c.getBrand(), c.getModel(), c.getPricePrDay(), c.getBestDiscount());
  }

  // Car to CarRequest conversion
  public CarRequest(Car car) {
    this.brand = car.getBrand();
    this.model = car.getModel();
    this.pricePrDay = car.getPricePrDay();
    this.bestDiscount = getBestDiscount();
  }




}
