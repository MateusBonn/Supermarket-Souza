package com.supermarketSouza.SupermarketSouza.repositories;

import com.supermarketSouza.SupermarketSouza.model.RefreshToken;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

  Optional<RefreshToken> findByLogin_Cpf( String cpf);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query("UPDATE RefreshToken u SET u.expiryDate = :newExpirationDate, u.token = :token WHERE u.id = :id")
  void updateExpirationDateAndRefreshToken(@Param("newExpirationDate") Instant newExpirationDate,
                                                   @Param("token") String token,
                                                   @Param("id") UUID id);


  @Query(value = "SELECT * FROM refresh_token WHERE token = :token AND expiry_date > :currentInstant", nativeQuery = true)
  Optional<RefreshToken> findByTokenAndExpiryDate(@Param("token") String token,
                                                  @Param("currentInstant") Instant expiryDate);
}
