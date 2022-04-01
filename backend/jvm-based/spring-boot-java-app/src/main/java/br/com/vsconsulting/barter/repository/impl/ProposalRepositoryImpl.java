package br.com.vsconsulting.barter.repository.impl;

import br.com.vsconsulting.barter.model.Proposal;
import java.util.Optional;
import br.com.vsconsulting.barter.repository.ProposalRepository;
import org.springframework.stereotype.Component;

@Component
public class ProposalRepositoryImpl implements ProposalRepository {

  @Override
  public Proposal save(Proposal proposal) {
    return proposal;
  }

  @Override
  public Optional<Proposal> findById(String id) {
    return Optional.empty();
  }
}
