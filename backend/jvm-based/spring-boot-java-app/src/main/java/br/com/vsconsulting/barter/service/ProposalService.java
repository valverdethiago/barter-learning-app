package br.com.vsconsulting.barter.service;

import br.com.vsconsulting.barter.model.Proposal;
import br.com.vsconsulting.barter.model.User;
import java.util.Set;

public interface ProposalService {

  void createOrUpdate(Proposal proposal);

  void approve(Integer id);

  Set<Proposal> getByUser(User user);
}
