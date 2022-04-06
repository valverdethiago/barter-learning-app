package br.com.vsconsulting.barter.service;

import br.com.vsconsulting.barter.exception.EntityNotFoundException;
import br.com.vsconsulting.barter.exception.InvalidProposalStatusException;
import br.com.vsconsulting.barter.model.Item;
import br.com.vsconsulting.barter.model.Proposal;
import br.com.vsconsulting.barter.model.ProposalStatus;
import br.com.vsconsulting.barter.model.User;
import br.com.vsconsulting.barter.repository.ItemRepository;
import br.com.vsconsulting.barter.repository.ProposalRepository;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ProposalService {

  @Autowired
  private ProposalRepository repository;
  @Autowired
  private ItemRepository itemRepository;
  @Autowired
  private MessageSource messageSource;


  public void approve(Integer id) {
    Proposal proposal = findById(id);
    validateProposalStatus(proposal);
    validateItems(proposal);
    User offeredUser = this.validateAndGetItemsOwner(proposal.getOfferedItems(),
        "proposal.invalid.status.offered.items.with.different.owners");
    User targetUser = this.validateAndGetItemsOwner(proposal.getTargetItems(),
        "proposal.invalid.status.target.items.with.different.owners");
    changeItemOwners(proposal, offeredUser, targetUser);
    changeProposalStatus(proposal);
  }

  private void validateItems(Proposal proposal) {
    if (CollectionUtils.isEmpty(proposal.getOfferedItems())) {
      throw new InvalidProposalStatusException(messageSource
          .getMessage("proposal.invalid.status.blank.offered.items", null,
              Locale.getDefault()));
    }
    if (CollectionUtils.isEmpty(proposal.getTargetItems())) {
      throw new InvalidProposalStatusException(
          messageSource.getMessage("proposal.invalid.status.blank.target.items", null,
              Locale.getDefault()));
    }

  }

  private User validateAndGetItemsOwner(Set<Item> items, String exceptionMessageCode) {
    Map<User, List<Item>> offeredItemsByOwner = items.stream()
        .collect(Collectors.groupingBy(Item::getOwner));
    if (offeredItemsByOwner.entrySet().size() > 1) {
      throw new InvalidProposalStatusException(
          messageSource.getMessage(exceptionMessageCode,
              null, Locale.getDefault()));
    }
    return offeredItemsByOwner.entrySet().stream().findFirst().orElseThrow(() ->
        new InvalidProposalStatusException(
            messageSource.getMessage(exceptionMessageCode,null, Locale.getDefault())))
        .getKey();
  }

  private Proposal findById(Integer id) {
    return repository.findById(id).orElseThrow(
        () -> new EntityNotFoundException()
    );
  }

  private void changeProposalStatus(Proposal proposal) {
    this.changeProposalStatus(proposal, ProposalStatus.DONE);
  }

  private void changeItemOwners(Proposal proposal, User offeringUser, User targetUser) {
    proposal.getOfferedItems().stream().forEach( item -> item.setOwner(targetUser));
    proposal.getTargetItems().stream().forEach( item -> item.setOwner(offeringUser));
    Set<Item> allItems = Stream.of(proposal.getTargetItems(), proposal.getOfferedItems())
        .flatMap(x -> x.stream())
        .collect(Collectors.toSet());
    itemRepository.saveAll(allItems);
  }

  private void validateProposalStatus(Proposal proposal) {
    if (proposal.getStatus() != ProposalStatus.SUBMITTED) {
      throw new InvalidProposalStatusException(messageSource
          .getMessage("proposal.invalid.status.other.than.submited", null,
              Locale.getDefault()));
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
