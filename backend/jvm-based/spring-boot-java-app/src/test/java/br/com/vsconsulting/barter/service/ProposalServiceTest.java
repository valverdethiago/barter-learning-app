package br.com.vsconsulting.barter.service;

import br.com.vsconsulting.barter.exception.InvalidProposalStatusException;
import br.com.vsconsulting.barter.model.Item;
import br.com.vsconsulting.barter.model.Proposal;
import br.com.vsconsulting.barter.model.ProposalStatus;
import br.com.vsconsulting.barter.repository.ItemRepository;
import br.com.vsconsulting.barter.repository.ProposalRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ProposalServiceTest {

  @Autowired
  private ProposalService service;

  @MockBean
  private ProposalRepository proposalRepository;

  @MockBean
  private ItemRepository itemRepository;

  @Test
  public void shouldThrowExceptionWhenStatusIsAlreadyApproved() {
    // Arrange
    Item offeredItem = Item.builder().build();
    Item targetItem = Item.builder().build();
    Proposal proposal = Proposal.builder()
        .id(UUID.randomUUID().toString())
        .status(ProposalStatus.APPROVED)
        .offeredItem(offeredItem)
        .targetItem(targetItem)
        .build();
    when(proposalRepository.findById(proposal.getId())).thenReturn(Optional.of(proposal));
    // Act and Assert
    Assertions.assertThrows(InvalidProposalStatusException.class, () -> service.approve(proposal.getId()) );
  }

}
