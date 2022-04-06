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
import br.com.vsconsulting.barter.service.impl.ProposalServiceImpl;
import br.com.vsconsulting.barter.service.impl.UserServiceImpl;
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

import static br.com.vsconsulting.barter.util.TestFixtures.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ProposalServiceTest {

  @Autowired
  private ProposalServiceImpl service;
  @Autowired
  private MessageSource messageSource;

  @MockBean
  private ProposalRepository proposalRepository;
  @MockBean
  private ItemRepository itemRepository;
  @MockBean
  private UserServiceImpl userService;

  @Test
  public void shouldThrowExceptionWhenStatusIsAlreadyApproved() {
    // Arrange
    Item offeredItem = Item.builder().build();
    Item targetItem = Item.builder().build();
    Proposal proposal = Proposal.builder()
        .id(new Random().nextInt())
        .status(ProposalStatus.APPROVED)
        .owner(USER_CARLOS)
        .offeredItems(Collections.singleton(offeredItem))
        .requestedItems(Collections.singleton(targetItem))
        .build();
    when(userService.getUserLoggedIn()).thenReturn(Optional.of(USER_CARLOS));
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
        .owner(USER_CARLOS)
        .status(ProposalStatus.REJECTED)
        .offeredItems(Collections.singleton(offeredItem))
        .requestedItems(Collections.singleton(targetItem))
        .build();
    when(userService.getUserLoggedIn()).thenReturn(Optional.of(USER_CARLOS));
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
    Proposal proposal = Proposal.builder()
        .id(new Random().nextInt())
        .owner(USER_CARLOS)
        .status(ProposalStatus.SUBMITTED)
        .requestedItems(Collections.singleton(CARLOS_BROKEN_IPHONE_8))
        .build();
    String expectedMessage = messageSource.getMessage(
        "proposal.invalid.status.blank.offered.items", null,
        Locale.getDefault());
    when(userService.getUserLoggedIn()).thenReturn(Optional.of(USER_CARLOS));
    // Act and Assert
    InvalidProposalStatusException expectedException =
        Assertions.assertThrows(InvalidProposalStatusException.class,
            () -> service.createOrUpdate(proposal));
    assertThat(expectedException.getMessage(), equalTo(expectedMessage));
  }

  @Test
  public void shouldThrowExceptionWhenBlankTargetItems() {
    // Arrange
    Proposal proposal = Proposal.builder()
        .id(new Random().nextInt())
        .owner(USER_CARLOS)
        .status(ProposalStatus.SUBMITTED)
        .offeredItems(Collections.singleton(CARLOS_BROKEN_IPHONE_8))
        .build();
    when(userService.getUserLoggedIn()).thenReturn(Optional.of(USER_CARLOS));
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
    Proposal proposal = Proposal.builder()
        .id(new Random().nextInt())
        .owner(USER_JOAO)
        .requestingUser(USER_CARLOS)
        .status(ProposalStatus.SUBMITTED)
        .offeredItems(Sets.newHashSet(CARLOS_BROKEN_IPHONE_8, JOAO_APPLE_AIRPODS))
        .requestedItems(Sets.newHashSet(CARLOS_BROKEN_IPHONE_8))
        .build();
    when(userService.getUserLoggedIn()).thenReturn(Optional.of(USER_JOAO));
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
    Proposal proposal = Proposal.builder()
        .id(new Random().nextInt())
        .owner(USER_CARLOS)
        .status(ProposalStatus.SUBMITTED)
        .requestedItems(Sets.newHashSet(CARLOS_BROKEN_IPHONE_8, JOAO_APPLE_AIRPODS))
        .offeredItems(Sets.newHashSet(CARLOS_BROKEN_IPHONE_8))
        .build();
    when(userService.getUserLoggedIn()).thenReturn(Optional.of(USER_CARLOS));
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
    Proposal proposal = Proposal.builder()
        .id(new Random().nextInt())
        .owner(USER_CARLOS)
        .status(ProposalStatus.SUBMITTED)
        .requestedItems(Sets.newHashSet(CARLOS_BROKEN_IPHONE_8, JOAO_APPLE_AIRPODS))
        .offeredItems(Sets.newHashSet(CARLOS_BROKEN_IPHONE_8))
        .build();
    when(userService.getUserLoggedIn()).thenReturn(Optional.of(USER_CARLOS));
    // Act and Assert
    InvalidProposalStatusException expectedException = Assertions.assertThrows(
        InvalidProposalStatusException.class, () -> service.createOrUpdate(proposal));
    assertThat(expectedException.getMessage(), equalTo(messageSource.getMessage(
        "proposal.invalid.status.target.items.with.different.owners", null,
        Locale.getDefault())));
  }

  @Test
  public void shouldThrowExceptionWhenUserIsTryingToApproveAProposalThatDoesNotBelongsToHim() {
    // Arrange

    Proposal proposal = Proposal.builder()
        .id(new Random().nextInt())
        .owner(USER_JOAO)
        .status(ProposalStatus.SUBMITTED)
        .requestedItems(Sets.newHashSet(CARLOS_BROKEN_IPHONE_8))
        .offeredItems(Sets.newHashSet(JOAO_APPLE_AIRPODS))
        .build();
    when(userService.getUserLoggedIn()).thenReturn(Optional.of(USER_CARLOS));
    // Act and Assert
    InvalidProposalStatusException expectedException = Assertions.assertThrows(
        InvalidProposalStatusException.class, () -> service.createOrUpdate(proposal));
    assertThat(expectedException.getMessage(), equalTo(messageSource.getMessage(
        "proposal.invalid.status.proposal.does.not.belong.to.the.user", null,
        Locale.getDefault())));
  }
  @Test
  public void shouldThrowExceptionWhenItemDoesNotBelongToTheUser() {
    // Arrange
    Proposal proposal = Proposal.builder()
        .id(new Random().nextInt())
        .owner(USER_CARLOS)
        .status(ProposalStatus.SUBMITTED)
        .requestedItems(Sets.newHashSet(CARLOS_BROKEN_IPHONE_8))
        .offeredItems(Sets.newHashSet(JOAO_APPLE_AIRPODS))
        .build();
    when(userService.getUserLoggedIn()).thenReturn(Optional.of(USER_CARLOS));
    // Act and Assert
    InvalidProposalStatusException expectedException = Assertions.assertThrows(
        InvalidProposalStatusException.class, () -> service.createOrUpdate(proposal));
    assertThat(expectedException.getMessage(), equalTo(messageSource.getMessage(
        "proposal.invalid.status.item.does.not.belong.to.the.user",
        new Object[]{JOAO_APPLE_AIRPODS.getName()},
        Locale.getDefault())));
  }

}
