package br.com.vsconsulting.barter.util;

import br.com.vsconsulting.barter.model.Item;
import br.com.vsconsulting.barter.model.User;
import java.util.Random;

public class TestFixtures {

  public static final User USER_CARLOS = User.builder()
      .id(new Random().nextInt())
      .username("carlos.fonseca")
      .name("Carlos Fonseca").build();

  public static final User USER_JOAO = User.builder()
      .id(new Random().nextInt())
      .username("joao.silva")
      .name("Joao Silva").build();

  public static final Item CARLOS_BROKEN_IPHONE_8 = Item.builder()
      .id(new Random().nextInt())
      .name("Broken Iphone 8")
      .description("Broken Iphone 8")
      .owner(USER_CARLOS)
      .build();

  public static final Item JOAO_APPLE_AIRPODS = Item.builder()
      .id(new Random().nextInt())
      .name("Apple Airpods")
      .description("Apple Airpods")
      .owner(USER_JOAO)
      .build();

}
