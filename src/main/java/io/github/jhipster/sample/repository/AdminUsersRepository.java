package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.AdminUsers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AdminUsers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdminUsersRepository extends JpaRepository<AdminUsers, Long> {}
