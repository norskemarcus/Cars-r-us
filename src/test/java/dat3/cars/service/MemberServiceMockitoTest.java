package dat3.cars.service;

import dat3.cars.dto.MemberRequest;
import dat3.cars.dto.MemberResponse;
import dat3.cars.entity.Member;
import dat3.cars.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class MemberServiceMockitoTest {

  @Mock
  MemberRepository memberRepository;

  MemberService memberService;


  @BeforeEach
  void setUp(){
    memberService = new MemberService(memberRepository);
  }


  @Test
  void getMembersAdmin() {
    Member m1 = new Member("m1", "m1@a.dk", "test12", "bb", "Olsen", "xx vej 34", "Lyngby", "2800");
    Member m2 = new Member("m2", "m2@a.dk", "test12", "aa", "hansen", "xx vej 34", "Lyngby", "2800");
    // Tidsstempel
    m1.setCreated(LocalDateTime.now());
    m2.setCreated(LocalDateTime.now());
    // Mockito, hvis nogen bruger findAll-metoden, så returneres listen af m1 og m2
    Mockito.when(memberRepository.findAll()).thenReturn(List.of(m1,m2));

    // Testen:
    List<MemberResponse> members = memberService.getMembers(true);
    assertEquals(2,members.size());
    assertNotNull(members.get(0).getCreated());
  }


  @Test
  void getMembersNotAdmin() {
    Member m1 = new Member("m1", "m1@a.dk", "test12", "bb", "Olsen", "xx vej 34", "Lyngby", "2800");
    Member m2 = new Member("m2", "m2@a.dk", "test12", "aa", "hansen", "xx vej 34", "Lyngby", "2800");
    // Tidsstempel
    m1.setCreated(LocalDateTime.now());
    m2.setCreated(LocalDateTime.now());
    // Mockito, hvis nogen bruger findAll-metoden, så returneres listen af m1 og m2
    Mockito.when(memberRepository.findAll()).thenReturn(List.of(m1,m2));

    // Testen:
    List<MemberResponse> members = memberService.getMembers(false);
    assertEquals(2,members.size());
    assertNull(members.get(0).getCreated()); // getCreated fra MemberResponse kommer ikke med fordi det ikke er en admin
  }



  @Test
  void addMember() {
    Member m1 = new Member("m1", "test12", "m1@a.dk", "bb", "Olsen", "xx vej 34", "Lyngby", "2800");
    Mockito.when(memberRepository.save(any(Member.class))).thenReturn(m1); // any: vælge mockito framework
    // Kan også teste en exception
    //Quick way to get a MemberRequest (remember eventually values come via a incoming JSON object)
    MemberRequest request = new MemberRequest(m1);

    // Det er servicemetoden vi tester!
    MemberResponse response = memberService.addMember(request);
    assertEquals("m1@a.dk",response.getEmail());
  }


  @Test
  void findMemberByUsernameAsAdmin() {
    Member m1 = new Member("m1", "test12", "m1@a.dk", "bb", "Olsen", "xx vej 34", "Lyngby", "2800");
    m1.setRanking(100);
    Mockito.when(memberRepository.findById("m1")).thenReturn(java.util.Optional.of(m1));
    MemberResponse response = memberService.findMemberByUsernameAsAdmin("m1");
    assertEquals(100,response.getRanking());
  }


  @Test
  void findMemberByUsernameAsMember() {
    Member m1 = new Member("m1", "test12", "m1@a.dk", "bb", "Olsen", "xx vej 34", "Lyngby", "2800");
    m1.setRanking(100);
    Mockito.when(memberRepository.findById("m1")).thenReturn(java.util.Optional.of(m1));

    MemberResponse response = memberService.findMemberByUsernameAsMember("m1");
    // A member should not get the ranking
    assertNull(response.getRanking());
  }


  @Test
  void editMember(){
    Member m1 = new Member("m1", "test12", "m1@a.dk", "bb",
        "Olsen", "xx vej 34", "Lyngby", "2800");
    m1.setCreated(LocalDateTime.now());
    Mockito.when(memberRepository.findById("m1")).thenReturn(java.util.Optional.of(m1));
    MemberRequest request = new MemberRequest();
    request.setFirstName("Anders");
    request.setPassword("ThisIsAveryStrongPassword!");
    request.setEmail(m1.getEmail());
    request.setLastName(m1.getLastName());
    request.setStreet(m1.getStreet());
    request.setCity(m1.getCity());
    request.setZip(m1.getZip());
    memberService.editMember(request, "m1");
    assertEquals("Anders", m1.getFirstName());
  }



  @Test
  void setRankingForUser(){
    Member m1 = new Member("m1", "test12", "m1@a.dk", "bb",
        "Olsen", "xx vej 34", "Lyngby", "2800");
    m1.setCreated(LocalDateTime.now());
    MemberRequest request = new MemberRequest(m1);
    Mockito.when(memberRepository.findById(request.getUsername())).thenReturn(java.util.Optional.of(m1));
    Mockito.when(memberRepository.save(any(Member.class))).thenReturn(m1);
    memberService.setRankingForUser("m1", 100);
    memberRepository.save(m1);
    assertEquals(100, m1.getRanking());
  }


}
