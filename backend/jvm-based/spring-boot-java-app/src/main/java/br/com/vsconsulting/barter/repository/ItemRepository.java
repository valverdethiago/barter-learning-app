package br.com.vsconsulting.barter.repository;

import br.com.vsconsulting.barter.model.Item;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemRepository extends PagingAndSortingRepository<Item, Integer> {
}
