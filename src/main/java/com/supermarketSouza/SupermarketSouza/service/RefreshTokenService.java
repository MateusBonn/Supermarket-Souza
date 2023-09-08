package com.supermarketSouza.SupermarketSouza.service;

import com.supermarketSouza.SupermarketSouza.model.RefreshToken;
import com.supermarketSouza.SupermarketSouza.repositories.LoginRepository;
import com.supermarketSouza.SupermarketSouza.repositories.RefreshTokenRepository;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

  final RefreshTokenRepository refreshTokenRepository;
  final LoginRepository loginRepository;

  public RefreshToken createRefreshToken(String username){
    var refreshToken =
        RefreshToken.builder()
            .login(loginRepository.findUser(username))
            .token(UUID.randomUUID().toString())
            .expiryDate(Instant.now().plusMillis(600000))
            .build();
    return refreshTokenRepository.save(refreshToken);
  }
}
