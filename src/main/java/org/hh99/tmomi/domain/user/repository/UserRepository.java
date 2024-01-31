package org.hh99.tmomi.domain.user.repository;

import org.hh99.tmomi.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
