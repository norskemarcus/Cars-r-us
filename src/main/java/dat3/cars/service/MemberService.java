package dat3.cars.service;

import dat3.cars.dto.MemberRequest;
import dat3.cars.dto.MemberResponse;
import dat3.cars.entity.Member;
import dat3.cars.repositories.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class MemberService {
  MemberRepository memberRepository;

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public MemberResponse addMember(MemberRequest memberRequest){
    if(memberRepository.existsById(memberRequest.getUsername())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Member with this ID already exist");
    }
    if(memberRepository.existsByEmail(memberRequest.getEmail())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Member with this Email already exist");
    }

    Member newMember = MemberRequest.getMemberEntity(memberRequest);
    newMember = memberRepository.save(newMember);
    return new MemberResponse(newMember, false); //False since anonymous uses can create "themself"
  }

  public List<MemberResponse> getMembers(boolean includeAll) {
    List<Member> members = memberRepository.findAll();
    return members.stream().map(m -> new MemberResponse(m, includeAll)).toList();
  }


  public MemberResponse findMemberByUsernameAsAdmin(String username) {
    Member found = memberRepository.findById(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    return new MemberResponse(found, true);
  }


  // Does not include created, edited, ranking, approved;
  public MemberResponse findMemberByUsernameAsMember(String username) {
    Member found = memberRepository.findById(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    return new MemberResponse(found, false);
  }


// Kun ændre den hvis den er tilstede: Mark sit forslag fra Lars
// Optional.ofNullable(body.getEmail()).ifPresent(memberToEdit::setEmail);
  // I postman skal man ellers sende alle værdier med, selv om de ikke ændres!
  public ResponseEntity<Boolean> editMember(MemberRequest body, String username){

   Member memberToEdit = memberRepository.findById(username).orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, "Member with this ID does not exist"));

    memberToEdit.setPassword(body.getPassword());
    memberToEdit.setEmail(body.getEmail());
    memberToEdit.setFirstName(body.getFirstName());
    memberToEdit.setLastName(body.getLastName());
    memberToEdit.setStreet(body.getStreet());
    memberToEdit.setCity(body.getCity());
    memberToEdit.setZip(body.getZip());
    memberRepository.save(memberToEdit);
    // Made with help from Tommy and Sebastian :)
    return new ResponseEntity<>(true, HttpStatus.OK);
  }

// This is a patch mapping, change just one parameter of the object
  public void setRankingForUser(String username, int value) {
    Member member = memberRepository.findById(username).orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, "Member with this ID does not exist"));
    member.setRanking(value);
    memberRepository.save(member);
  }

  public void deleteMemberByUsername(String username) {
    memberRepository.findById(username).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
    memberRepository.deleteById(username);
  }
}