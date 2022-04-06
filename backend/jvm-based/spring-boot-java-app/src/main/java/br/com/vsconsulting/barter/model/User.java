package br.com.vsconsulting.barter.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String username;
  private String name;
  private String password;
  private String email;
  @Column(name = "created_on", columnDefinition = "TIMESTAMP")
  private LocalDateTime createdOn;
  @Column(name = "updated_on", columnDefinition = "TIMESTAMP")
  private LocalDateTime updatedOn;
  @Column(name = "last_login_on", columnDefinition = "TIMESTAMP")
  private LocalDateTime lastLoginOn;
  @Column(name = "disabled_on", columnDefinition = "TIMESTAMP")
  private LocalDateTime disabledOn;

}
