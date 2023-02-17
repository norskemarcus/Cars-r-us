package dat3.cars.repository;


import dat3.cars.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

  boolean existsByEmail(String email);

  @Query("SELECT DISTINCT m FROM Member m JOIN m.reservations r")
  List<Member> findMembersWithReservation();

  // Made with help from chatGPT
  @Query("SELECT m FROM Member m WHERE m.username NOT IN (SELECT DISTINCT r.member.username FROM Reservation r)")
  List<Member> findMembersWithoutReservation();

}