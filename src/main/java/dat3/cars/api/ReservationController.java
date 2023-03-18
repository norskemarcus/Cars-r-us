package dat3.cars.api;


import dat3.cars.dto.MemberResponse;
import dat3.cars.dto.ReservationRequest;
import dat3.cars.dto.ReservationResponse;
import dat3.cars.service.ReservationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("api/reservations")
public class ReservationController {

  private final ReservationService reservationService;


  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
  @PostMapping
  ReservationResponse makeReservation (@RequestBody ReservationRequest body){
    return reservationService.makeReservation(body);
  }


  @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
  @GetMapping
  List<ReservationResponse> getReservations(){
    return reservationService.getReservations();
  }

/*
  @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
  @GetMapping("/{username}")
  List<ReservationResponse> getReservationsByMember(@PathVariable String username){
    return reservationService.findReservationsByMember(username);
  }*/


/*  @GetMapping("/{id}")
  ReservationResponse getReservationById(@PathVariable Integer id){
    return reservationService.findReservationById(id);
  }*/

}
