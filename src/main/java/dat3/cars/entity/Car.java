package dat3.cars.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class Car {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;

  @Column(name="car_brand",length = 50, nullable = false)
  String brand;

  @Column(name="car_model",length = 60, nullable = false)
  String model;

  @Column(name="rental_price-day")
  double pricePrDay;

  @Column(name="max_discount")
  int bestDiscount;

  @CreationTimestamp
  LocalDateTime created;

  @UpdateTimestamp
  LocalDateTime lastEdited;


  // TODO: Skal denne constructor med???
  public Car(Integer id, String brand, String model, double pricePrDay) {
    this.id = id;
    this.brand = brand;
    this.model = model;
    this.pricePrDay = pricePrDay;
  }

  public Car(String brand, String model, double pricePrDay) {
    this.brand = brand;
    this.model = model;
    this.pricePrDay = pricePrDay;
  }

  @Override
  public String toString() {
    return "Car{" +
        "id=" + id +
        ", brand='" + brand + '\'' +
        ", model='" + model + '\'' +
        '}';
  }
}