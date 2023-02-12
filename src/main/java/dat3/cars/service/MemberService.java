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
import java.util.stream.Collectors;

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
    List<MemberResponse> responses = members.stream().map(member -> new MemberResponse(member,includeAll)).collect(Collectors.toList());
    return responses;
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