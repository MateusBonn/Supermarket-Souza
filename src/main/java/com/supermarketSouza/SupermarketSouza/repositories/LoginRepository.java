package com.supermarketSouza.SupermarketSouza.repositories;

import com.supermarketSouza.SupermarketSouza.model.LoginModel;
import com.supermarketSouza.SupermarketSouza.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<LoginModel, String> {

  UserDetails findByUsername(String username);

  @Query(
      "select u from LoginModel u where u.username = :username"
  )
  LoginModel findUser(@Param("username") String username);
}
