package br.com.vsconsulting.barter.repository;

import br.com.vsconsulting.barter.model.Proposal;
import java.util.Optional;

public interface ProposalRepository {

  Proposal save(Proposal proposal);

  Optional<Proposal> findById(String id);

}
