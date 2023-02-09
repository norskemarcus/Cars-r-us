package dat3.cars.service;

import dat3.cars.dto.MemberRequest;
import dat3.cars.dto.MemberResponse;
import dat3.cars.entity.Member;
import dat3.cars.repositories.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


//Transactional (rollback)
//Includes what is needed for JPA/Hibernate - leaves everything else out
//Uses an in-memory database (H2)
@DataJpaTest
class MemberServiceMockWithH2Test {


    @Autowired
    public MemberRepository memberRepository;

    MemberService memberService;

    boolean dataIsReady = false;
    @BeforeEach
    void setUp() {
      if(!dataIsReady){  //Explain this
        memberRepository.save(new Member("m1", "test12", "m1@a.dk",  "bb",
            "Olsen", "xx vej 34", "Lyngby", "2800"));
        memberRepository.save(new Member("m2", "test12", "m2@a.dk", "aa",
            "hansen", "xx vej 34", "Lyngby", "2800"));
        dataIsReady = true;
        memberService = new MemberService(memberRepository); //Real DB is mocked away with H2
      }
    }



    @Test
    void addMember() {
      MemberRequest memberRequest = new MemberRequest();
      memberRequest.setUsername("hh1980");
      memberRequest.setEmail("hh@gmail.com");
      memberRequest.setFirstName("Håvard");
      memberRequest.setLastName("Holje");
      memberRequest.setStreet("Haugbro Terrasse 40");
      memberRequest.setCity("Langhus");
      memberRequest.setZip("4000");
      memberService.addMember(memberRequest);
      assertEquals("Holje", memberRequest.getLastName());
    }

    @Test
    void getMembersAdmin() {
      List<MemberResponse> members = memberService.getMembers(true);
      assertEquals(2,members.size());
      assertNotNull(members.get(0).getCreated());
    }


    @Test
    void setRankingForUser(){
      // Member member = new Member("eva2023", "eva2023", "eva@gmail.dk",  "Eva",
      // "Amundsen", "Test vej 34", "Søborg", "2860");
      // memberService.setRankingForUser("m1", 100);
      //memberRepository.save(member);
      // assertEquals(100, member.getRanking());
    }


    @Test
    void deleteMemberByUsername() {
      // Det burde være 2 members i repo på forhånd fra setup
      memberService.deleteMemberByUsername("m1");
      assertEquals(1, memberRepository.findAll().size());
    }


  }


