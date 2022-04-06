package br.com.vsconsulting.barter.api;

import br.com.vsconsulting.barter.model.auth.AuthRequestModel;
import br.com.vsconsulting.barter.model.auth.JwtTokenModel;
import br.com.vsconsulting.barter.service.UserService;
import br.com.vsconsulting.barter.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

  @Autowired
  private UserService userService;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtTokenUtil tokenManager;

  @PostMapping("/login")
  public ResponseEntity<JwtTokenModel> createToken(@RequestBody AuthRequestModel
      request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getUsername(),
            request.getPassword())
    );
    final UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
    final String jwtToken = tokenManager.generateJwtToken(userDetails);
    return ResponseEntity.ok(new JwtTokenModel(jwtToken));
  }
}

