package dat3.cars.api;


import dat3.cars.dto.ReservationRequest;
import dat3.cars.dto.ReservationResponse;
import dat3.cars.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/reservations")
public class ReservationController {
  private final ReservationService reservationService;


  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @PostMapping
  ReservationResponse makeReservation (@RequestBody ReservationRequest body){
    return reservationService.makeReservation(body);
  }


  @GetMapping
  List<ReservationResponse> getReservations(){
    return reservationService.getReservations();
  }

}
