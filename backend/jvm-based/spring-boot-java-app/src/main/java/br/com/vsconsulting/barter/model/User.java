package br.com.vsconsulting.barter.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class User implements UserDetails {

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

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Set.of((GrantedAuthority) () -> Role.USER.name());
  }

  @Override
  public boolean isAccountNonExpired() {
    return disabledOn == null;
  }

  @Override
  public boolean isAccountNonLocked() {
    return disabledOn == null;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return disabledOn == null;
  }

  @Override
  public boolean isEnabled() {
    return disabledOn == null;
  }
}
