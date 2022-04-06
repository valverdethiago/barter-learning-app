package br.com.vsconsulting.barter.repository;

import br.com.vsconsulting.barter.model.Proposal;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProposalRepository extends PagingAndSortingRepository<Proposal, Integer>  {

}
