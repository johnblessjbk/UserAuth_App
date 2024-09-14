package com.userAuth.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userAuth.app.entities.Login;

@Repository
public interface LoginRepo extends JpaRepository<Login, Long>{

    Optional<Login> findByEmail(String email);

}
