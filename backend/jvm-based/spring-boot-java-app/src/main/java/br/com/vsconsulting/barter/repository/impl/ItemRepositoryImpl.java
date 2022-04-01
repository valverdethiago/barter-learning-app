package br.com.vsconsulting.barter.repository.impl;

import br.com.vsconsulting.barter.model.Item;
import br.com.vsconsulting.barter.repository.ItemRepository;
import org.springframework.stereotype.Component;

@Component
public class ItemRepositoryImpl implements ItemRepository {

  @Override
  public Item save(Item item) {
    // TODO implement
    return item;
  }
}
