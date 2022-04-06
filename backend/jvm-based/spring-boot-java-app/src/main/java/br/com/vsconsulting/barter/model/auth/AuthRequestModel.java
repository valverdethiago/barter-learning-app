package br.com.vsconsulting.barter.model.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequestModel {

  private String username;
  private String password;

}
