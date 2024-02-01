package org.hh99.tmomi.domain.user.repository;

import java.util.Optional;

import org.hh99.tmomi.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
