package br.com.vsconsulting.barter.service;

import static br.com.vsconsulting.barter.util.TestFixtures.FAKE_USER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.when;

import br.com.vsconsulting.barter.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceTest {

  @Autowired
  private UserService userService;
  @MockBean
  private UserRepository userRepository;

  @Test
  public void testUsernameNotFound() {
    // Arrange
    when(userRepository.findByUsername(FAKE_USER.getUsername())).thenReturn(Optional.empty());
    // Act and Assert
    Assertions.assertThrows(UsernameNotFoundException.class,
        () -> userService.loadUserByUsername(FAKE_USER.getUsername()));
  }

  @Test
  public void testUserFound() {
    // Arrange
    when(userRepository.findByUsername(FAKE_USER.getUsername())).thenReturn(Optional.of(FAKE_USER));
    // Act
    UserDetails userDetails = userService.loadUserByUsername(FAKE_USER.getUsername());
    // Assert
    assertThat(userDetails, equalTo(FAKE_USER));
  }

}
