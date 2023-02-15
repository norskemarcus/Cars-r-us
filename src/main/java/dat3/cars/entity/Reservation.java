package dat3.cars.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Reservation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  // a Car can be reserved for many days by different Members
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "car_id")
  private Car car;

  // I have used FetchType.LAZY for the @ManyToOne relationships in the
  // Reservation entity to avoid unnecessary fetching of data from the database.
  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate rentalDate;

  @CreationTimestamp
  LocalDateTime rentalCreated;

  public Reservation(Car car, Member member, LocalDate rentalDate) {
    this.car = car;
    this.member = member;
    this.rentalDate = rentalDate;
  }

// chatGPT
/*
  public boolean overlaps(LocalDate otherStartDate, LocalDate otherEndDate) {
    return !(endDate.isBefore(otherStartDate) || otherEndDate.isBefore(rentalDate));
  }
*/

  // Calculate the Total Cost here in Reservation?
/*
  public double calculateTotalCost() {
    long days = startDate.until(endDate, java.time.temporal.ChronoUnit.DAYS);
    return days * dailyRate;
  }
*/

}