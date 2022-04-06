package br.com.vsconsulting.barter.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import br.com.vsconsulting.barter.exception.InvalidProposalStatusException;
import br.com.vsconsulting.barter.model.Item;
import br.com.vsconsulting.barter.model.Proposal;
import br.com.vsconsulting.barter.model.ProposalStatus;
import br.com.vsconsulting.barter.model.User;
import br.com.vsconsulting.barter.repository.ItemRepository;
import br.com.vsconsulting.barter.repository.ProposalRepository;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ProposalServiceTest {

  @Autowired
  private ProposalService service;
  @Autowired
  private MessageSource messageSource;

  @MockBean
  private ProposalRepository proposalRepository;
  @MockBean
  private ItemRepository itemRepository;
  @MockBean
  private UserService userService;

  @Test
  public void shouldThrowExceptionWhenStatusIsAlreadyApproved() {
    // Arrange
    Item offeredItem = Item.builder().build();
    Item targetItem = Item.builder().build();
    Proposal proposal = Proposal.builder()
        .id(new Random().nextInt())
        .status(ProposalStatus.APPROVED)
        .offeredItems(Collections.singleton(offeredItem))
        .requestedItems(Collections.singleton(targetItem))
        .build();
    when(proposalRepository.findById(proposal.getId())).thenReturn(Optional.of(proposal));
    // Act and Assert
    InvalidProposalStatusException expectedException =
        Assertions.assertThrows(InvalidProposalStatusException.class,
            () -> service.approve(proposal.getId()));
    assertThat(expectedException.getMessage(), equalTo(messageSource.getMessage(
        "proposal.invalid.status.other.than.submited", null,
        Locale.getDefault())));
  }

  @Test
  public void shouldThrowExceptionWhenStatusIsAlreadyRejected() {
    // Arrange
    Item offeredItem = Item.builder().build();
    Item targetItem = Item.builder().build();
    Proposal proposal = Proposal.builder()
        .id(new Random().nextInt())
        .status(ProposalStatus.REJECTED)
        .offeredItems(Collections.singleton(offeredItem))
        .requestedItems(Collections.singleton(targetItem))
        .build();
    when(proposalRepository.findById(proposal.getId())).thenReturn(Optional.of(proposal));
    // Act and Assert
    InvalidProposalStatusException expectedException =
        Assertions.assertThrows(InvalidProposalStatusException.class,
            () -> service.approve(proposal.getId()));
    assertThat(expectedException.getMessage(), equalTo(messageSource.getMessage(
        "proposal.invalid.status.other.than.submited", null,
        Locale.getDefault())));
  }

  @Test
  public void shouldThrowExceptionWhenBlankOfferedItems() {
    // Arrange
    Item targetItem = Item.builder().build();
    Proposal proposal = Proposal.builder()
        .id(new Random().nextInt())
        .status(ProposalStatus.SUBMITTED)
        .requestedItems(Collections.singleton(targetItem))
        .build();
    String expectedMessage = messageSource.getMessage(
        "proposal.invalid.status.blank.offered.items", null,
        Locale.getDefault());
    when(userService.loadUserByUsername(anyString())).thenReturn(
        User.builder().id(new Random().nextInt()).name("Dummy User").build());
    // Act and Assert
    InvalidProposalStatusException expectedException =
        Assertions.assertThrows(InvalidProposalStatusException.class,
            () -> service.createOrUpdate(proposal));
    assertThat(expectedException.getMessage(), equalTo(expectedMessage));
  }

  @Test
  public void shouldThrowExceptionWhenBlankTargetItems() {
    // Arrange
    Item offeredItem = Item.builder().build();
    Proposal proposal = Proposal.builder()
        .id(new Random().nextInt())
        .status(ProposalStatus.SUBMITTED)
        .offeredItems(Collections.singleton(offeredItem))
        .build();
    when(proposalRepository.findById(proposal.getId())).thenReturn(Optional.of(proposal));
    // Act and Assert
    InvalidProposalStatusException expectedException =
        Assertions.assertThrows(InvalidProposalStatusException.class,
            () -> service.createOrUpdate(proposal));
    assertThat(expectedException.getMessage(), equalTo(messageSource.getMessage(
        "proposal.invalid.status.blank.target.items", null,
        Locale.getDefault())));
  }

  @Test
  public void shouldThrowExceptionWhenOfferedItemsHasDifferentOwners() {
    // Arrange
    User carlos = User.builder().id(new Random().nextInt()).name("Carlos Test").build();
    User jhonny = User.builder().id(new Random().nextInt()).name("Jhonny Test").build();
    Item appleIphone8 = Item.builder().owner(carlos).build();
    Item appleBrokenAirpods = Item.builder().owner(jhonny).build();

    Proposal proposal = Proposal.builder()
        .id(new Random().nextInt())
        .status(ProposalStatus.SUBMITTED)
        .offeredItems(Sets.newHashSet(appleIphone8, appleBrokenAirpods))
        .requestedItems(Sets.newHashSet(appleIphone8))
        .build();
    // Act and Assert
    InvalidProposalStatusException expectedException = Assertions.assertThrows(
        InvalidProposalStatusException.class, () -> service.createOrUpdate(proposal));
    assertThat(expectedException.getMessage(), equalTo(messageSource.getMessage(
        "proposal.invalid.status.offered.items.with.different.owners", null,
        Locale.getDefault())));
  }


  @Test
  public void shouldThrowExceptionWhenTargetItemsHasDifferentOwners() {
    // Arrange
    User carlos = User.builder().id(new Random().nextInt()).name("Carlos Test").build();
    User jhonny = User.builder().id(new Random().nextInt()).name("Jhonny Test").build();
    Item appleIphone8 = Item.builder().owner(carlos).build();
    Item appleBrokenAirpods = Item.builder().owner(jhonny).build();

    Proposal proposal = Proposal.builder()
        .id(new Random().nextInt())
        .status(ProposalStatus.SUBMITTED)
        .requestedItems(Sets.newHashSet(appleIphone8, appleBrokenAirpods))
        .offeredItems(Sets.newHashSet(appleIphone8))
        .build();
    when(userService.loadUserByUsername(anyString())).thenReturn(
        User.builder().id(new Random().nextInt()).name("Dummy User").build());
    // Act and Assert
    InvalidProposalStatusException expectedException = Assertions.assertThrows(
        InvalidProposalStatusException.class, () -> service.createOrUpdate(proposal));
    assertThat(expectedException.getMessage(), equalTo(messageSource.getMessage(
        "proposal.invalid.status.target.items.with.different.owners", null,
        Locale.getDefault())));
  }

  @Test
  public void shouldThrowExceptionWhenUsersAreTryingTargetItemsHasDifferentOwners() {
    // Arrange
    User carlos = User.builder().id(new Random().nextInt()).name("Carlos Test").build();
    User jhonny = User.builder().id(new Random().nextInt()).name("Jhonny Test").build();
    Item appleIphone8 = Item.builder().owner(carlos).build();
    Item appleBrokenAirpods = Item.builder().owner(jhonny).build();

    Proposal proposal = Proposal.builder()
        .id(new Random().nextInt())
        .status(ProposalStatus.SUBMITTED)
        .requestedItems(Sets.newHashSet(appleIphone8, appleBrokenAirpods))
        .offeredItems(Sets.newHashSet(appleIphone8))
        .build();
    when(userService.loadUserByUsername(anyString())).thenReturn(
        User.builder().id(new Random().nextInt()).name("Dummy User").build());
    // Act and Assert
    InvalidProposalStatusException expectedException = Assertions.assertThrows(
        InvalidProposalStatusException.class, () -> service.createOrUpdate(proposal));
    assertThat(expectedException.getMessage(), equalTo(messageSource.getMessage(
        "proposal.invalid.status.target.items.with.different.owners", null,
        Locale.getDefault())));
  }

}
