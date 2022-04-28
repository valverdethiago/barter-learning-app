package br.com.vsconsulting.barter.repository;

import br.com.vsconsulting.barter.model.Proposal;
import br.com.vsconsulting.barter.model.User;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ProposalRepository extends PagingAndSortingRepository<Proposal, Integer>  {

  @Query("select e from Proposal e "
      + "  where e.requestingUser = :user "
      + "     or e.owner = :user")
  Set<Proposal> getByUser(@Param("user") User user);
}
