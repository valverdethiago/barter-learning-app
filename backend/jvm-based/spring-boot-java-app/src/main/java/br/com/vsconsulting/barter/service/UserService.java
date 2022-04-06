package br.com.vsconsulting.barter.service;

import br.com.vsconsulting.barter.model.User;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  Authentication getAuthentication();

  Optional<User> getUserLoggedIn();
}
