package br.com.vsconsulting.barter.exception;

public class InvalidProposalStatusException extends RuntimeException {

  public InvalidProposalStatusException() {
  }

  public InvalidProposalStatusException(String message) {
    super(message);
  }
}
