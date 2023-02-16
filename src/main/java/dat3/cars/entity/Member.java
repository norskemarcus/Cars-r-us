package dat3.cars.entity;

import dat3.security.entity.UserWithRoles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_TYPE")
public class Member extends UserWithRoles {
  String firstName;
  String lastName;
  String street;
  String city;
  String zip;
  boolean approved;
  int ranking;

  //  a Member can make many reservations
   /* chatGPT:  Added cascade and orphanRemoval properties to the @OneToMany
   relationships in the Car and Member entities. This will ensure that when a
   reservation is deleted, it will also be removed from the list of reservations
   in the corresponding car and member  entities. */
  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Reservation> reservations = new ArrayList<>();

  @CreationTimestamp
  LocalDateTime created;

  @UpdateTimestamp
  LocalDateTime lastEdited;

  public Member(String user, String password, String email,
                String firstName, String lastName, String street, String city, String zip) {
    super(user, password, email);
    this.firstName = firstName;
    this.lastName = lastName;
    this.street = street;
    this.city = city;
    this.zip = zip;
  }

}