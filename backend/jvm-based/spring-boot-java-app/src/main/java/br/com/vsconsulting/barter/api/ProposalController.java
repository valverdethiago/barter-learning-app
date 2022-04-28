package br.com.vsconsulting.barter.api;

import br.com.vsconsulting.barter.exception.InvalidProposalStatusException;
import br.com.vsconsulting.barter.model.Proposal;
import br.com.vsconsulting.barter.service.UserService;
import br.com.vsconsulting.barter.service.impl.ProposalServiceImpl;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/proposal")
public class ProposalController {

  private final ProposalServiceImpl service;
  private final UserService userService;


  @PostMapping("/{id}/approve")
  public ResponseEntity approve(@PathVariable("id") Integer id) {
    try {
      service.approve(id);
      return ResponseEntity.ok().build();
    }
    catch (InvalidProposalStatusException ex) {
      return ResponseEntity.unprocessableEntity().build();
    }
  }

  @GetMapping
  public ResponseEntity list() {
    Set<Proposal> proposals = service.getByUser(userService.getUserLoggedIn().get());
    if(CollectionUtils.isEmpty(proposals))
      return ResponseEntity.noContent().build();
    return new ResponseEntity<>(proposals, HttpStatus.OK);
  }


}
