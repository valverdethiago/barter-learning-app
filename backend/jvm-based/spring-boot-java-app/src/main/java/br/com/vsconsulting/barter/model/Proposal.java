package br.com.vsconsulting.barter.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Proposal {

  private String id;
  private Item offeredItem;
  private Item targetItem;
  private ProposalStatus status;

}
