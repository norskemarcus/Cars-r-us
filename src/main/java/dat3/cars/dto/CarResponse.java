package dat3.cars.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.cars.entity.Car;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // ignoring null fields or attribute
public class CarResponse {
  private Integer id; // primary key
  private String brand;
  private String model;
  private double pricePrDay;
  @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss",shape = JsonFormat.Shape.STRING)
  LocalDateTime created;

  @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss",shape = JsonFormat.Shape.STRING)
  LocalDateTime edited;

  private Integer bestDiscount;

  //Convert Car Entity to Car DTO
  public CarResponse (Car car, boolean includeAll){
    this.id = car.getId();
    this.brand = car.getBrand();
    this.model = car.getModel();
    this.pricePrDay = car.getPricePrDay();
    if (includeAll){
      this.created = car.getCreated();
      this.edited = car.getLastEdited();
      this.bestDiscount = car.getBestDiscount();
    }
  }

}
