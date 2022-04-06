package br.com.vsconsulting.barter.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

  @GetMapping("/admin")
  public ResponseEntity<String> helloAdmin() {
    return ResponseEntity.ok("Hello Admin");
  }

  @GetMapping("/user")
  public ResponseEntity<String> helloUser() {
    return ResponseEntity.ok("Hello User");
  }
}
