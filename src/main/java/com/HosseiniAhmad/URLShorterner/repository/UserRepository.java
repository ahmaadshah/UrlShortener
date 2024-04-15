package com.HosseiniAhmad.URLShorterner.repository;

import com.HosseiniAhmad.URLShorterner.model.entity.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(value = "User.detail", type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findByUsername(String username);

    @EntityGraph(value = "User.detail", type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findByEmail(String email);
}
