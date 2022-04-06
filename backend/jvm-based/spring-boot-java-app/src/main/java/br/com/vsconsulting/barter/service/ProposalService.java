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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProposalService {

  private final ProposalRepository repository;
  private final ItemRepository itemRepository;
  private final MessageSource messageSource;
  private final UserService userService;

  public void createOrUpdate(Proposal proposal) {
      validateItems(proposal);
      this.validateItemsOwner(proposal.getOfferedItems(),
          "proposal.invalid.status.offered.items.with.different.owners");
      this.validateItemsAreFromTheOriginalUser(proposal);
      this.validateItemsOwner(proposal.getRequestedItems(),
          "proposal.invalid.status.target.items.with.different.owners");
      validateProposalBelongsToUserLoggedIn(proposal);
  }

  public void approve(Integer id) {
    Proposal proposal = findById(id);
    validateProposalStatus(proposal);
    validateProposalBelongsToUserLoggedIn(proposal);
    persistDatabaseOperations(proposal);
  }

  private void persistDatabaseOperations(Proposal proposal) {
    changeItemOwners(proposal);
    changeProposalStatus(proposal);
  }

  private void validateProposalBelongsToUserLoggedIn(Proposal proposal) {
    User userLoggedIn = userService.getUserLoggedIn().get();
    if (!userLoggedIn.equals(proposal.getOwner())) {
      throw new InvalidProposalStatusException(messageSource
          .getMessage("proposal.invalid.status.proposal.does.not.belong.to.the.user",
              null, Locale.getDefault()));
    }
  }

  private void validateItemsAreFromTheOriginalUser(Proposal proposal) {
    proposal.getRequestedItems().stream().filter(item -> !item.getOwner().equals(proposal.getRequestedItems()))
        .findAny().ifPresent(s->
        new InvalidProposalStatusException(messageSource
            .getMessage("proposal.invalid.status.item.does.not.belong.to.the.user",
                new Object[]{s.getName()}, Locale.getDefault())));
  }

  private void validateItems(Proposal proposal) {
    if (CollectionUtils.isEmpty(proposal.getOfferedItems())) {
      throw new InvalidProposalStatusException(messageSource
          .getMessage("proposal.invalid.status.blank.offered.items", null,
              Locale.getDefault()));
    }
    if (CollectionUtils.isEmpty(proposal.getRequestedItems())) {
      throw new InvalidProposalStatusException(
          messageSource.getMessage("proposal.invalid.status.blank.target.items", null,
              Locale.getDefault()));
    }

  }

  private void validateItemsOwner(Set<Item> items, String exceptionMessageCode) {
    Map<User, List<Item>> itemsByOwner = items.stream()
        .collect(Collectors.groupingBy(Item::getOwner));
    if (itemsByOwner.entrySet().size() > 1) {
      throw new InvalidProposalStatusException(
          messageSource.getMessage(exceptionMessageCode,
              null, Locale.getDefault()));
    }
  }

  private Proposal findById(Integer id) {
    return repository.findById(id).orElseThrow(
        () -> new EntityNotFoundException()
    );
  }

  private void changeProposalStatus(Proposal proposal) {
    this.changeProposalStatus(proposal, ProposalStatus.DONE);
  }

  private void changeItemOwners(Proposal proposal) {
    proposal.getOfferedItems().stream().forEach( item -> item.setOwner(proposal.getRequestingUser()));
    proposal.getRequestedItems().stream().forEach( item -> item.setOwner(proposal.getOwner()));
    Set<Item> allItems = Stream.of(proposal.getRequestedItems(), proposal.getOfferedItems())
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
