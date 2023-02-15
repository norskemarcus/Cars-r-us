package dat3.cars.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
public class Member {
  @Id
  String username;
  @NonNull
  String password;
  @NonNull
  String email;
  String firstName;
  @NonNull
  String lastName;
  @NonNull
  String street;
  @NonNull
  String city;
  @NonNull
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
    this.username = user;
    this.password = password;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.street = street;
    this.city = city;
    this.zip = zip;
  }

  /*
  You can remove the following when we get to week2 if you like, they were only include to demonstrate
  collestions of basic type
   */
 /* @ElementCollection
  List<String> favoriteCarColors = new ArrayList<>();

  @ElementCollection
  @MapKeyColumn(name = "description")
  @Column(name = "phone_number")
  Map<String,String> phones = new HashMap<>();
*/

}