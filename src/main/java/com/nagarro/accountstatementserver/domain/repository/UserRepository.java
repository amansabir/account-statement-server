package com.nagarro.accountstatementserver.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nagarro.accountstatementserver.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    boolean existsByUsername(String username);
}
