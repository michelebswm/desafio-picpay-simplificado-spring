package com.michelew.desafiopycpay.repositories;

import com.michelew.desafiopycpay.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
