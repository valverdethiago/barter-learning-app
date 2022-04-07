package br.com.vsconsulting.barter.service;

import br.com.vsconsulting.barter.model.Proposal;

public interface ProposalService {

  void createOrUpdate(Proposal proposal);

  void approve(Integer id);
}
