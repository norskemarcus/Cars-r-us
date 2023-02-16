package dat3.cars.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.cars.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationResponse {
  private Integer id;
  private String memberUsername;
  private int carId;
  @JsonFormat(pattern= "yyyy-MM-dd")
  private LocalDate rentalDate;


  public ReservationResponse(Reservation reservation) {
    this.id = reservation.getId();
    this.memberUsername = reservation.getMember().getUsername();
    this.carId = reservation.getCar().getId();
    this.rentalDate = reservation.getRentalDate();
  }
}
