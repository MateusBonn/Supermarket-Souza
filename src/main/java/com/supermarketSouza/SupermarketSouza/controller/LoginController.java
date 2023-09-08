package com.supermarketSouza.SupermarketSouza.controller;

import com.supermarketSouza.SupermarketSouza.config.security.TokenService;
import com.supermarketSouza.SupermarketSouza.model.LoginModel;
import com.supermarketSouza.SupermarketSouza.repositories.LoginRepository;
import com.supermarketSouza.SupermarketSouza.request.LoginDTO;
import com.supermarketSouza.SupermarketSouza.request.LoginSaveDTO;
import com.supermarketSouza.SupermarketSouza.response.SecurityResponse;
import com.supermarketSouza.SupermarketSouza.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

  final LoginRepository loginRepository;
  final LoginService loginService;


  @PostMapping("/login")
  public ResponseEntity<SecurityResponse> login(@RequestBody LoginDTO loginDTO) {
    return ResponseEntity.ok(loginService.login(loginDTO));
  }

  @PostMapping("/register")
  public ResponseEntity<Object> register(@RequestBody LoginSaveDTO loginSaveDTO){
    if(this.loginRepository.findByUsername(loginSaveDTO.getUsername()) != null) return ResponseEntity.badRequest().build();

    String encryptedPassword = new BCryptPasswordEncoder().encode(loginSaveDTO.getPassword());
    this.loginRepository.save(LoginModel.builder()
                                  .cpf(loginSaveDTO.getCpf())
                                  .username(loginSaveDTO.getUsername())
                                  .password(encryptedPassword)
                                  .role(loginSaveDTO.getRole())
                                  .build()
                                  );
    return ResponseEntity.status(HttpStatus.CREATED).body("Login Created");
  }


}

