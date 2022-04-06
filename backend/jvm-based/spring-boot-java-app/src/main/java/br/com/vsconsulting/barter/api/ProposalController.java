package br.com.vsconsulting.barter.api;

import br.com.vsconsulting.barter.exception.InvalidProposalStatusException;
import br.com.vsconsulting.barter.service.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

public class ProposalController {

  @Autowired
  private ProposalService service;

  @PostMapping("/proposal/${id}/approve")
  public ResponseEntity approve(Integer id) {
    try {
      service.approve(id);
      return ResponseEntity.ok().build();
    }
    catch (InvalidProposalStatusException ex) {
      return ResponseEntity.unprocessableEntity().build();
    }
  }


}
