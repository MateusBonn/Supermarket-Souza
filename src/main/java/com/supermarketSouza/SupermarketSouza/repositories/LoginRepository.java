package com.supermarketSouza.SupermarketSouza.repositories;

import com.supermarketSouza.SupermarketSouza.model.LoginModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<LoginModel, String> {

  UserDetails findByUsername(String username);

}
