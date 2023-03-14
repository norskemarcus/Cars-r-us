package dat3.cars.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Builder
@NoArgsConstructor
@Setter
@Getter

@Entity
public class Car {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name="car_brand",length = 50, nullable = false)
  private String brand;

  @Column(name="car_model",length = 60, nullable = false)
  private String model;

  @Column(name="rental_price_day")
  private double pricePrDay;

  @Column(name="max_discount")
  private int bestDiscount;

  @CreationTimestamp
  LocalDateTime created;

  @UpdateTimestamp
  LocalDateTime lastEdited;

  /* chatGPT:
  Note that I have added cascade and orphanRemoval properties
  to the @OneToMany relationships in the Car and Member entities.
  This will ensure that when a reservation is deleted, it will also be
  removed from the list of reservations in the corresponding car and member entities.
 */
  // a Car can be reserved for many days by different Members
  @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Reservation> reservations = new ArrayList<>();


  public Car(String brand, String model, double pricePrDay, Integer bestDiscount) {
    this.brand = brand;
    this.model = model;
    this.pricePrDay = pricePrDay;
    this.bestDiscount = bestDiscount;
  }


}