package dat3.cars.api;

import dat3.cars.dto.MemberRequest;
import dat3.cars.dto.MemberResponse;
import dat3.cars.entity.Member;
import dat3.cars.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequestMapping("/api/members")
@RestController
public class MemberController {

  final MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  //Security ADMIN ONLY
  @GetMapping
  public List<MemberResponse> getMembers(){
    return memberService.getMembers(true); //True --> Return all, since this is ADMIN only
  }

  //Security ADMIN ONLY
  @GetMapping(path = "/{username}")
  public MemberResponse getMemberById(@PathVariable String username) {
    return memberService.findMemberByUsername(username);
  }

  //Security ANONYMOUS
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public MemberResponse addMember(@RequestBody MemberRequest body){
    return memberService.addMember(body);
  }

  //Security Admin? (eventually we will change it to use the currently log in user)
  @PutMapping("/{username}")
  public ResponseEntity<Boolean> editMember(@RequestBody MemberRequest body, @PathVariable String username){
    return memberService.editMember(body, username);
  }

  //Security ADMIN ONLY
  // Patch changes parts of the object
  @PatchMapping("/ranking/{username}/{value}")
  public void setRankingForUser(@PathVariable String username, @PathVariable int value) {
     memberService.setRankingForUser(username, value);
  }


  // Security ADMIN ONLY
  @DeleteMapping( "/{username}")
  public void deleteMemberByUsername(@PathVariable String username) {
    memberService.deleteMemberByUsername(username);
  }





}
