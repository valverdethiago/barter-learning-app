package br.com.vsconsulting.barter.service;

import br.com.vsconsulting.barter.exception.EntityNotFoundException;
import br.com.vsconsulting.barter.exception.InvalidProposalStatusException;
import br.com.vsconsulting.barter.model.Item;
import br.com.vsconsulting.barter.model.Proposal;
import br.com.vsconsulting.barter.model.ProposalStatus;
import br.com.vsconsulting.barter.model.User;
import br.com.vsconsulting.barter.repository.ItemRepository;
import br.com.vsconsulting.barter.repository.ProposalRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProposalService {

  @Autowired
  private ProposalRepository repository;
  @Autowired
  private ItemRepository itemRepository;


  public void approve(String id) {
    Proposal proposal = findById(id);
    validateProposalStatus(proposal);
    validateItemOwner(proposal);
    changeItemOwners(proposal);
    changeProposalStatus(proposal);
  }

  private void validateItemOwner(Proposal proposal) {
    // TODO implement
  }

  private Proposal findById(String id) {
    Optional<Proposal> proposal = repository.findById(id);
    if(proposal.isEmpty()) {
      throw new EntityNotFoundException();
    }
    return proposal.get();
  }

  private void changeProposalStatus(Proposal proposal) {
    this.changeProposalStatus(proposal, ProposalStatus.DONE);
  }

  private void changeItemOwners(Proposal proposal) {
    this.changeOwner(proposal.getOfferedItem(), proposal.getTargetItem().getOwner());
    this.changeOwner(proposal.getTargetItem(), proposal.getOfferedItem().getOwner());
  }

  private void validateProposalStatus(Proposal proposal) {
    if(proposal.getStatus() != ProposalStatus.SUBMITTED) {
      throw new InvalidProposalStatusException();
    }
  }

  private void changeProposalStatus(Proposal proposal, ProposalStatus status) {
    proposal.setStatus(status);
    repository.save(proposal);
  }

  private void changeOwner(Item item, User owner) {
    item.setOwner(owner);
    itemRepository.save(item);
  }

}
