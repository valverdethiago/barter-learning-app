package br.com.vsconsulting.barter.util;

import br.com.vsconsulting.barter.model.Item;
import br.com.vsconsulting.barter.model.User;
import br.com.vsconsulting.barter.model.auth.AuthRequestModel;
import com.github.javafaker.Faker;
import java.util.Random;

public class TestFixtures {

  private static Faker FAKER = new Faker();

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

  public static final AuthRequestModel FAKE_REQUEST_MODEL = AuthRequestModel.builder()
      .username(FAKER.name().username())
      .password(FAKER.internet().password())
      .build();

  public static final User FAKE_USER = User.builder()
      .username(FAKER.name().username())
      .name(FAKER.name().fullName())
      .email(FAKER.internet().emailAddress())
      .build();

}
