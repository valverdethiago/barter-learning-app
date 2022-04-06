package br.com.vsconsulting.barter.repository;

import br.com.vsconsulting.barter.model.User;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

  Optional<User> findByUsername(String username);
}
