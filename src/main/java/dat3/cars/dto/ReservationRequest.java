package dat3.cars.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {
  private int carId;
  private String username;
  @JsonFormat(pattern= "yyyy-MM-dd")
  private LocalDate rentalDate; // den ønskede lejedato








}
