package com.supermarketSouza.SupermarketSouza.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.supermarketSouza.SupermarketSouza.exception.TokenValidationException;
import com.supermarketSouza.SupermarketSouza.model.LoginModel;
import com.supermarketSouza.SupermarketSouza.model.RefreshToken;
import com.supermarketSouza.SupermarketSouza.repositories.LoginRepository;
import com.supermarketSouza.SupermarketSouza.repositories.RefreshTokenRepository;
import com.supermarketSouza.SupermarketSouza.response.ErrorMessage;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService {

  @Value("${api.security.token.secret}")
  private String secret;

  @Value("${api.security.token.expiration}")
  private long expirationToken;

  @Value("${api.security.token.refresh-token.expiration}")
  private long expirationRefreshToken;

  final RefreshTokenRepository refreshTokenRepository;
  final LoginRepository loginRepository;



  public String generateToken(LoginModel login){
    log.info("generateToken");
    try{
      Algorithm algorithm = Algorithm.HMAC256(secret);
     return JWT.create()
          .withIssuer("auth-api")
          .withSubject(login.getUsername())
          .withExpiresAt(generateExpirationDate(expirationToken))
          .sign(algorithm);
    }catch (JWTCreationException exception){
      throw new RuntimeException("Error while generating token " + exception);
    }
  }

  public RefreshToken createRefreshToken(LoginModel loginModel) {
    var existRefreshToken = refreshTokenRepository.findByLogin_Cpf(loginModel.getCpf());

    if (existRefreshToken.isPresent()){
     return validateDateRefreshToken(existRefreshToken.get());
    }

    var refreshToken =
        RefreshToken.builder()
            .login(loginRepository.findUser(loginModel.getUsername()))
            .token(UUID.randomUUID().toString())
            .expiryDate(generateExpirationDate(expirationRefreshToken))
            .login(loginModel)
            .build();


    return refreshTokenRepository.save(refreshToken);
  }

  public RefreshToken validateDateRefreshToken(RefreshToken refreshToken) {
      if(refreshToken.getExpiryDate().isBefore(Instant.now())) {
       changeToken(refreshToken, UUID.randomUUID().toString());
       return refreshTokenRepository.findByTokenAndExpiryDate(refreshToken.getToken(),
                                                              Instant.now()).get();
      }
      return refreshToken;
  }
  public String validateToken(String token) throws JWTVerificationException {

      log.info("validateToken");
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm)
          .withIssuer("auth-api")
          .build()
          .verify(token)
          .getSubject();
  }



  private Instant generateExpirationDate(long expiration) {
    log.info("Hora de validação do Bearer",LocalDateTime.now().plusHours(expiration).toInstant(ZoneOffset.of("-03:00")));
    log.info(String.valueOf(LocalDateTime.now().plusMinutes(expiration).toInstant(ZoneOffset.of("-03:00"))));

    return LocalDateTime.now().plusHours(expiration).toInstant(ZoneOffset.of("-03:00"));
  }

  public void changeToken(RefreshToken refreshToken, String token) {
     refreshTokenRepository.updateExpirationDateAndRefreshToken(generateExpirationDate(expirationRefreshToken),
                                                                      token,
                                                                      refreshToken.getId());
  }

}
