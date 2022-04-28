package br.com.vsconsulting.barter.api;

import static br.com.vsconsulting.barter.util.TestFixtures.FAKE_REQUEST_MODEL;
import static br.com.vsconsulting.barter.util.TestFixtures.FAKE_USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.vsconsulting.barter.model.auth.JwtTokenModel;
import br.com.vsconsulting.barter.repository.UserRepository;
import br.com.vsconsulting.barter.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerIT {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private JwtTokenUtil jwtTokenUtil;
  @MockBean
  private UserRepository userRepository;
  @MockBean
  private AuthenticationManager authenticationManager;

  @Test
  public void testAuthenticationBadCredentials() throws Exception {
    String json = objectMapper.writeValueAsString(FAKE_REQUEST_MODEL);
    when(authenticationManager.authenticate(any())).thenThrow(
        new BadCredentialsException("Bad credentials"));
    mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isForbidden());
  }

  @Test
  public void testAuthenticationSuccess() throws Exception {
    String json = objectMapper.writeValueAsString(FAKE_REQUEST_MODEL);
    String expectedJson = objectMapper.writeValueAsString(
        JwtTokenModel.builder()
            .token(jwtTokenUtil.generateJwtToken(FAKE_USER))
            .build());
    when(authenticationManager.authenticate(any())).thenReturn(
        new UsernamePasswordAuthenticationToken(FAKE_USER, null));
    when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(FAKE_USER));
    mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("token").isNotEmpty())
        .andExpect(content().json(expectedJson));

  }


}
